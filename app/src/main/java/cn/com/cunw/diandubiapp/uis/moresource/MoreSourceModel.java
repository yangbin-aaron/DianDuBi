package cn.com.cunw.diandubiapp.uis.moresource;

import com.lzy.okgo.OkGo;

import java.util.List;

import cn.com.cunw.diandubiapp.base.mvp.BaseModel;
import cn.com.cunw.diandubiapp.beans.Test;
import cn.com.cunw.diandubiapp.http.BaseCallBack;
import cn.com.cunw.diandubiapp.interfaces.Contants;

/**
 * @author YangBin
 * @time 2019/8/9 15:45
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class MoreSourceModel extends BaseModel {

    public void getMoreSourceList(BaseCallBack callback) {
        String url = Contants.BASE_URL + "v1/takingpen/resources";
        OkGo.get(url)
                .params("accountId", "604650534976229377")
                .execute(callback);
    }
}
