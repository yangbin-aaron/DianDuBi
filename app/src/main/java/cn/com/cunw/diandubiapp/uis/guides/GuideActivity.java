package cn.com.cunw.diandubiapp.uis.guides;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.model.Progress;

import java.util.List;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.mvp.BaseMvpActivity;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;
import cn.com.cunw.diandubiapp.interfaces.Contants;
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
public class GuideActivity extends BaseMvpActivity<MoreSourcePresenter> implements MoreSourceView, View.OnClickListener {
    @Override
    public int initLayout() {
        return R.layout.activity_guide;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    private GuideProgressView guide_pro;
    private Button mButton;

    @Override
    public void initViews() {
        super.initViews();
        mButton = findViewById(R.id.btn);
        mButton.setVisibility(View.INVISIBLE);
        mButton.setOnClickListener(this);
        guide_pro = findViewById(R.id.guide_pro);
        mPresenter.getMoreSourceList();
    }

    private void joinMainActivity() {
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
                        joinMainActivity();
//                        SourceSpHelper.getInstance().saveDownLoadedStatus();
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
        findViewById(R.id.progreBar).setVisibility(View.GONE);
        if (list == null || list.size() == 0) {
            joinMainActivity();
            return;
        }
        mButton.setVisibility(View.VISIBLE);
        mTotalSize = totalSize;
        // 下载
        for (SourceBean.ItemBean itemBean : list) {
            DownLoadHelper.getInstance().downSource(itemBean);
        }
    }

    @Override
    public void onError(String message) {
        joinMainActivity();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        joinMainActivity();
    }
}