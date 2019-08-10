package cn.com.cunw.diandubiapp.views;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.interfaces.MySourceDialogListener;
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

    private void init(Context context) {
        EventBus.getDefault().register(mContext);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_source, this);
        view_bg = findViewById(R.id.view_bg);
        tv_name = findViewById(R.id.tv_name);
        tv_sub = findViewById(R.id.tv_sub);
        tv_pro = findViewById(R.id.tv_pro);
        progressView = findViewById(R.id.progressView);

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
        boolean exists = exists();
        // 本地是否存在
        if (exists) {
            // 存在
        } else {
            // 不存在
        }
    }

    private boolean exists() {
        File file = new File(DownLoadHelper.getInstance().getPath() + mItemBean.fileName);
        return file.exists();
    }

    private void load() {
        if (mStatus == 0) {
            // 弹出 需要付费 的对话框
            if (mMySourceDialogListener != null) {
                mMySourceDialogListener.showDialog();
            }
            return;
        }
        if (exists()) {
            ToastUtis.show("本地已存在该文件！");
            return;
        }
        DownloadTask task = OkDownload.getInstance().getTask(mItemBean.id);
        if (task == null) {
            DownLoadHelper.getInstance().downSource(mItemBean);
        } else {
            Progress progress = task.progress;
            int proStatus = progress.status;
            if (proStatus == Progress.PAUSE || proStatus == Progress.ERROR) {
                task.start();
            } else if (proStatus == Progress.LOADING || proStatus == Progress.WAITING) {
                task.pause();
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
                try {
                    EventBus.getDefault().unregister(mContext);
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
                            initStatusView();
                        }
                        try {
                            updateRate(rate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        initStatusView();
                    }
                }
                break;
        }
    }
}