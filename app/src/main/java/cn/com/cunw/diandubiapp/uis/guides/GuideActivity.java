package cn.com.cunw.diandubiapp.uis.guides;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.base.mvp.BaseMvpActivity;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;
import cn.com.cunw.diandubiapp.http.LogDownloadListener;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.preference.SourceSpHelper;
import cn.com.cunw.diandubiapp.uis.main.MainActivity;
import cn.com.cunw.diandubiapp.uis.moresource.MoreSourcePresenter;
import cn.com.cunw.diandubiapp.uis.moresource.MoreSourceView;
import cn.com.cunw.diandubiapp.views.GuideProgressView;

/**
 * @author YangBin
 * @time 2019/8/9 8:44
 * @des 引导页，自动下载资源
 * @copyright 湖南新云网科技有限公司
 */
public class GuideActivity extends BaseMvpActivity<MoreSourcePresenter> implements MoreSourceView {
    @Override
    public int initLayout() {
        return R.layout.activity_guide;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    private GuideProgressView guide_pro;

    @Override
    public void initViews() {
        super.initViews();
        guide_pro = findViewById(R.id.guide_pro);
        mPresenter.getMoreSourceList();
    }

    public void joinMainActivity(View view) {
        MainActivity.startActivity(this);
        finish();
    }

    @Override
    public void onEventMainThread(Message message) {
        super.onEventMainThread(message);
        switch (message.what) {
            case Contants.WHAT_GUIDE_DOWN:
                Progress progress = (Progress) message.obj;
                if (guide_pro != null && progress.status != Progress.NONE && progress.status != Progress.WAITING) {

                    long currSize = progress.currentSize;
                    long totalSize = progress.totalSize;
                    if (currSize == totalSize) {
                        mCurrSize += currSize;
                    }

                    int rate = (int) ((currSize + mCurrSize) * 100 / mTotalSize);
                    guide_pro.updateRate(rate);
                    if (rate == 100) {
                        joinMainActivity(null);
                        SourceSpHelper.getInstance().saveDownLoadedStatus();
                    }
                }
                break;
        }
    }

    @Override
    protected MoreSourcePresenter createPresenter() {
        return new MoreSourcePresenter(this);
    }

    @Override
    public void initList(List<SourceBean> list) {

    }

    private long mCurrSize;
    private long mTotalSize;

    @Override
    public void initAutoList(List<SourceBean.ItemBean> list, long totalSize) {
        mTotalSize = totalSize;
        // 下载
        for (SourceBean.ItemBean itemBean : list) {
            DownLoadHelper.getInstance().downSource(itemBean);
        }
    }
}