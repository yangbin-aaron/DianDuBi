package cn.com.cunw.diandubiapp.uis.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.uis.guides.GuideActivity;
import cn.com.cunw.diandubiapp.uis.localsource.LocalSourceActivity;

/**
 * @author YangBin
 * @time 2019/8/9 8:43
 * @des 主页，tianlong完成 TODO
 * @copyright 湖南新云网科技有限公司
 */
public class MainActivity extends BaseActivity {
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    private static MainActivity sActivity;

    public static void startActivity(Context context) {
        if (sActivity != null)
            return;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        super.initViews();
        sActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sActivity = null;
        // 停止下载服务
        GuideActivity.mI = -1;
    }

    public void joinLoaclActivity(View view) {
        LocalSourceActivity.startActivity(this);
    }
}
