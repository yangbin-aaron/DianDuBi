package cn.com.cunw.diandubiapp.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.utils.DataUtils;

/**
 * @author YangBin
 * @time 2019/8/9 8:39
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static SourceBean.ItemBean sItemBean;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataUtils.initData(BaseActivity.this);
            String tag = intent.getStringExtra("tag");
            Log.e("aaron", "收到广播 tag = " + tag);
            switch (tag) {
                case "token":
                    // 只刷新Token，不做其他操作
                    break;
                case "api":
                    refreshApi();
                    break;
                default:// 下载
                    if (BaseActivity.sItemBean != null) {
                        DownLoadHelper.getInstance().downSource(sItemBean, false);
                    }
                    break;
            }
        }
    };

    public void refreshApi() {

    }

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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.REFRESH_TOKEN_SUCCESS);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Message message) {

    }

    public abstract int initLayout();

    public void initViews() {
    }
}
