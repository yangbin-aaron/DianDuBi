package cn.com.cunw.diandubiapp.views;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.interfaces.MySourceDialogListener;
import cn.com.cunw.diandubiapp.utils.FileUtils;
import cn.com.cunw.diandubiapp.utils.ToastUtis;

/**
 * @author YangBin
 * @time 2019/8/5 15:19
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class SourceItemView extends RelativeLayout {

    private static boolean sLoading = false;

    public SourceItemView(Context context) {
        super(context);
        init(context);
    }

    public SourceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Context mContext;
    private View view_bg;
    private TextView tv_name;
    private TextView tv_sub;
    private TextView tv_pro;
    private SourceProgressView progressView;
    private ImageView iv_down_status;

    private void init(Context context) {
        EventBus.getDefault().register(this);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_source, this);
        view_bg = findViewById(R.id.view_bg);
        tv_name = findViewById(R.id.tv_name);
        tv_sub = findViewById(R.id.tv_sub);
        tv_pro = findViewById(R.id.tv_pro);
        progressView = findViewById(R.id.progressView);
        iv_down_status = findViewById(R.id.iv_down_status);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始下载，判断本地是否有数据，判断能否下载
                load();
            }
        });
    }

    private SourceBean.ItemBean mItemBean;

    public void setItemBean(SourceBean.ItemBean itemBean) {
        mItemBean = itemBean;
        tv_name.setText(mItemBean.subjectName);
        tv_sub.setText(mItemBean.verisonName);
        initStatusView();
    }

    private int mStatus = -1;

    private void initStatusView() {
        mStatus = -1;
        if (mItemBean.allowFreeDownload) {
            mStatus = 1;
        } else {
            if (mItemBean.bugflag) {
                mStatus = 2;
            } else {
                mStatus = 0;
            }
        }

        progressView.setVisibility(GONE);
        tv_pro.setBackgroundResource(R.drawable.ic_source_status_bg);
        switch (mStatus) {
            case 0:
                tv_pro.setText("付费");
                view_bg.setBackgroundResource(R.drawable.rect_source_1);
                break;
            case 1:// 免费
                tv_pro.setText("");
                tv_pro.setBackgroundResource(android.R.color.transparent);
                view_bg.setBackgroundResource(R.drawable.rect_source_2);
                break;
            case 2:
                tv_pro.setText("已付费");
                view_bg.setBackgroundResource(R.drawable.rect_source_3);
                break;
        }

        int status = 0;
        try {
            DownloadTask task = OkDownload.getInstance().getTask(mItemBean.id);
            if (task != null) {
                status = task.progress.status;
                if (status == Progress.PAUSE || status == Progress.WAITING || status == Progress.LOADING) {
                    int rate = (int) (task.progress.currentSize * 100 / task.progress.totalSize);
                    if (rate < 100) {
                        updateRate(rate);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateDownStatus(status);

        initLoaclStatus();
    }

    private void updateRate(float rate) {
        progressView.setVisibility(VISIBLE);
        tv_pro.setBackgroundResource(android.R.color.transparent);
        progressView.update(rate / 100);
        tv_pro.setText((int) rate + "%");
        if (rate >= 100) {
            initStatusView();
        }
    }

    /**
     * 本地是否存在该文件
     */
    private void initLoaclStatus() {
        boolean exists = FileUtils.exists(mItemBean);
        setTag(exists);
        // 本地是否存在
        if (exists) {
            tv_pro.setText("已下载");
        }
    }

    private void updateDownStatus(int status) {
        switch (status) {
            case Progress.ERROR:
            case Progress.FINISH:
            case Progress.NONE:
                iv_down_status.setImageResource(R.color.transparent);
                break;
            case Progress.WAITING:
                iv_down_status.setImageResource(R.drawable.ic_down_waiting);
                break;
            case Progress.LOADING:
                iv_down_status.setImageResource(R.drawable.ic_down_pause);
                break;
            case Progress.PAUSE:
                iv_down_status.setImageResource(R.drawable.ic_down_start);
                break;
        }
    }

    private void load() {
        if (mStatus == 0) {
            // 弹出 需要付费 的对话框
            if (mMySourceDialogListener != null) {
                mMySourceDialogListener.showDialog();
            }
            return;
        }
        boolean exists = (boolean) getTag();
        DownloadTask task = OkDownload.getInstance().getTask(mItemBean.id);
        if (task == null) {
            if (exists) {
                ToastUtis.show("本地已存在该文件！");
            } else {
                long availableSize = FileUtils.getAvailableSize();
                if (availableSize - mItemBean.fileSize > Contants.MAX_SD_SIZE) {
                    DownLoadHelper.getInstance().downSource(mItemBean);
                } else {
                    ToastUtis.show("空间不足！");
                }
            }
        } else {
            Progress progress = task.progress;
            int proStatus = progress.status;
            if (proStatus == Progress.PAUSE || proStatus == Progress.ERROR) {
                task.start();
            } else if (proStatus == Progress.LOADING || proStatus == Progress.WAITING) {
                task.pause();
            } else {
                if (exists) {
                    ToastUtis.show("本地已存在该文件！");
                }
            }
        }
    }

    private MySourceDialogListener mMySourceDialogListener;

    public void setMySourceDialogListener(MySourceDialogListener mySourceDialogListener) {
        mMySourceDialogListener = mySourceDialogListener;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Message message) {
        switch (message.what) {
            case Contants.WHAT_REGISTER_EVENTBUG:
                DownloadTask task = OkDownload.getInstance().getTask(mItemBean.id);
                if (task != null) {
                    task.pause();
                }
                try {
                    EventBus.getDefault().unregister(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Contants.WHAT_GUIDE_DOWN:
                Progress progress = (Progress) message.obj;
                long currSize = progress.currentSize;
                long totalSize = progress.totalSize;
                String tag = progress.tag;
                if (tag.equals(mItemBean.id)) {
                    if (progress.status == Progress.LOADING || progress.status == Progress.PAUSE || progress.status == Progress.FINISH) {
                        int rate = (int) (currSize * 100 / totalSize);
                        if (rate == 100) {
                            ToastUtis.show(mItemBean.getFileName() + "下载完成");
                            initStatusView();
                        }
                        try {
                            updateRate(rate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (progress.status == Progress.ERROR) {
                        FileUtils.delete(mItemBean);
                        initStatusView();
                    } else {

                    }
                    updateDownStatus(progress.status);
                }
                break;
        }
    }
}