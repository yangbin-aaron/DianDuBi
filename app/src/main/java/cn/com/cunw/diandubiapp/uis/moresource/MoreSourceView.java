package cn.com.cunw.diandubiapp.uis.moresource;

import java.util.List;

import cn.com.cunw.diandubiapp.base.mvp.BaseView;
import cn.com.cunw.diandubiapp.beans.SourceBean;

/**
 * @author YangBin
 * @time 2019/8/9 15:45
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public interface MoreSourceView extends BaseView {
    void initList(List<SourceBean> list);

    void initAutoList(List<SourceBean.ItemBean> list, long totalSize);

    void onError(String message);
}
