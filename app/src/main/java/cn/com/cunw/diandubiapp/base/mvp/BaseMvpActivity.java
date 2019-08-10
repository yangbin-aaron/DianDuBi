package cn.com.cunw.diandubiapp.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.com.cunw.diandubiapp.base.BaseActivity;

/**
 * @author YangBin
 * @time 2019/8/9 16:16
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    public P mPresenter;

    protected abstract P createPresenter();

    private void bindPresenter() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        bindPresenter();
        super.onCreate(savedInstanceState);
    }
}
