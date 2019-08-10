package cn.com.cunw.diandubiapp.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author YangBin
 * @time 2019/8/9 15:50
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public abstract class BasePresenter<M extends BaseModel, V extends BaseView> implements IPresenter<V> {
    protected M mModel;
    protected V mView;
    protected Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
        mModel = bindModel();
    }

    public abstract M bindModel();

    @Override
    public void attachView(@NonNull V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}