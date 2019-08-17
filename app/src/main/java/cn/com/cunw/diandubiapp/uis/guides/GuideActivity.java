package cn.com.cunw.diandubiapp.uis.guides;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
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
import cn.com.cunw.diandubiapp.utils.DataUtils;
import cn.com.cunw.diandubiapp.utils.ToastUtis;
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
        verifyStoragePermissions(this);
        mButton = findViewById(R.id.btn);
        mButton.setVisibility(View.INVISIBLE);
        mButton.setOnClickListener(this);
        guide_pro = findViewById(R.id.guide_pro);
    }

    private void joinMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }

    private String mTags = "";

    @Override
    public void onEventMainThread(Message message) {
        super.onEventMainThread(message);
        switch (message.what) {
            case Contants.WHAT_GUIDE_DOWN:
                Progress progress = (Progress) message.obj;
                if (guide_pro != null && progress.status != Progress.NONE && progress.status != Progress.WAITING) {

                    long currSize = progress.currentSize;
                    int rate = (int) ((currSize + mCurrSize) * 100 / mTotalSize);
                    if (progress.status == Progress.FINISH) {
                        if (TextUtils.isEmpty(mTags) || !mTags.contains(progress.tag)) {
                            mCurrSize += currSize;
                            mTags += progress.tag + ",";
                        }
                    }

                    guide_pro.updateRate(rate);
                    if (rate >= 100) {
                        joinMainActivity();
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
            DownLoadHelper.getInstance().downSource(itemBean, false);
        }
    }

    @Override
    public void onError(int code, String message) {
        // 刷新Token
        if (code == 480 || code == 401) {
            DataUtils.sendBroad("token");
        }
        joinMainActivity();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        joinMainActivity();
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    private void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_STORAGE[1]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                mPresenter.getMoreSourceList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意权限申请
                    mPresenter.getMoreSourceList();
                } else { //拒绝权限申请
                    ToastUtis.show("权限被拒绝了！");
                    finish();
                }
                break;
            default:
                break;
        }
    }
}