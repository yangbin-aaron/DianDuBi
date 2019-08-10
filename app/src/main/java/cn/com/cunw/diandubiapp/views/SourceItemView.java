package cn.com.cunw.diandubiapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.cunw.diandubiapp.interfaces.MySourceDialogListener;
import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.beans.SourceBean;
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
    private int _rate = 0;

    private void init(Context context) {
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
        if (!mItemBean.allowFreeDownload) {
            mStatus = -1;
        } else {
            if (mItemBean.bugflag) {
                mStatus = 2;
            } else {
                mStatus = 0;
            }
        }

        // 本地是否存在
        if (1 == 1) {
            mStatus = 1;
        }

        progressView.setVisibility(GONE);
        tv_pro.setBackgroundResource(R.drawable.ic_source_status_bg);
        switch (mStatus) {
            case 0:
                tv_pro.setText("付费");
                view_bg.setBackgroundResource(R.drawable.rect_source_1);
                break;
            case 1:
                initLoaclStatus();
                break;
            case 2:
                tv_pro.setText("已付费");
                view_bg.setBackgroundResource(R.drawable.rect_source_3);
                break;
        }
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            float rate = msg.what;
            if (rate == 100) {
                initLoaclStatus();
            }
            try {
                updateRate(rate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 本地是否存在该文件
     */
    private void initLoaclStatus() {
        mStatus = 1;
        tv_pro.setText("");
        tv_pro.setBackgroundResource(android.R.color.transparent);
        view_bg.setBackgroundResource(R.drawable.rect_source_2);
    }

    private void load() {
        if (mStatus == 0) {
            // 弹出 需要付费 的对话框
            if (mMySourceDialogListener != null) {
                mMySourceDialogListener.showDialog();
            }
            return;
        }
        if (sLoading) {
            ToastUtis.show(R.string.down_loading);
            return;
        }
        sLoading = true;
        _rate = 0;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (sLoading) {
                    try {
                        sleep(100);
                        _rate++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (_rate >= 100) {
                        sLoading = false;
                    }
                    mHandler.sendEmptyMessage(_rate);
                }
            }
        }.start();
    }

    private MySourceDialogListener mMySourceDialogListener;

    public void setMySourceDialogListener(MySourceDialogListener mySourceDialogListener) {
        mMySourceDialogListener = mySourceDialogListener;
    }
}