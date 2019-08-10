package cn.com.cunw.diandubiapp.base;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author YangBin
 * @time 2019/8/9 8:39
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = initLayout();
        if (layout != 0) {
            setContentView(layout);
        }

        try {
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Message message) {

    }

    public abstract int initLayout();

    public void initViews() {
    }
}
