package cn.com.cunw.diandubiapp.base.mvp;

import android.support.annotation.NonNull;

/**
 * @author YangBin
 * @time 2019/8/9 15:48
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public interface IPresenter<V extends BaseView> {

    /**
     * 连接view
     *
     * @param view
     */
    void attachView(@NonNull V view);

    /**
     * 分离view
     */
    void detachView();
}
