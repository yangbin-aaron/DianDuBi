package cn.com.cunw.diandubiapp.uis;

import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.preference.SourceSpHelper;
import cn.com.cunw.diandubiapp.uis.guides.GuideActivity;
import cn.com.cunw.diandubiapp.uis.main.MainActivity;
import cn.com.cunw.diandubiapp.utils.NetworkUtil;

/**
 * @author YangBin
 * @time 2019/8/9 8:38
 * @des 欢迎界面
 * @copyright 湖南新云网科技有限公司
 */
public class LauncherActivity extends BaseActivity {
    @Override
    public int initLayout() {
        return 0;
    }

    @Override
    public void initViews() {
        super.initViews();
        boolean isDownloaded = SourceSpHelper.getInstance().isDownLoaded();

        if (isDownloaded || !NetworkUtil.isNetworkAvailable(this)) {
            MainActivity.startActivity(this);
        } else {
            GuideActivity.startActivity(this);
        }
        finish();
    }
}
