package cn.com.cunw.diandubiapp.uis.localsource;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.uis.moresource.MoreSourceActivity;

/**
 * @author YangBin
 * @time 2019/8/9 8:45
 * @des 本地资源 tianlong完成 TODO
 * @copyright 湖南新云网科技有限公司
 */
public class LocalSourceActivity extends BaseActivity {
    @Override
    public int initLayout() {
        return R.layout.activity_local_source;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LocalSourceActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    public void joinMoreSourceActivity(View view) {
        MoreSourceActivity.startActivity(this);
    }
}
