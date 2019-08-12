package cn.com.cunw.diandubiapp.uis.moresource;

import com.lzy.okgo.OkGo;

import cn.com.cunw.diandubiapp.App;
import cn.com.cunw.diandubiapp.base.mvp.BaseModel;
import cn.com.cunw.diandubiapp.http.BaseCallBack;
import cn.com.cunw.diandubiapp.preference.SourceSpHelper;

/**
 * @author YangBin
 * @time 2019/8/9 15:45
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class MoreSourceModel extends BaseModel {

    public void getMoreSourceList(BaseCallBack callback) {
        String url = App.getBaseUrl() + "takingpen/resources";
        String token = SourceSpHelper.getInstance().getToken();
        OkGo.get(url)
                .headers("Authorization", token)
                .params("accountId", "604650534976229377")
                .execute(callback);
    }
}