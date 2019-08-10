package cn.com.cunw.diandubiapp.uis.moresource;

import com.lzy.okgo.OkGo;

import java.util.List;

import cn.com.cunw.diandubiapp.base.mvp.BaseModel;
import cn.com.cunw.diandubiapp.beans.Test;
import cn.com.cunw.diandubiapp.http.BaseCallBack;

/**
 * @author YangBin
 * @time 2019/8/9 15:45
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class MoreSourceModel extends BaseModel {
    public void getMoreSourceList(BaseCallBack callback) {
        OkGo.get("http://114.116.158.57:9002/v1/class/courses")
                .params("gradeCode", "1")
                .params("beginDate", "20190805")
                .params("endDate", "20190811")
                .params("classCode", "CLA000001")
                .execute(callback);
    }
}
