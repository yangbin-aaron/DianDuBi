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
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1NjUzOTkxNzIsInN1YiI6IntcImlkXCI6XCI2MDQ2ODYxNDMxMTU2Mjg1NDVcIixcImFjY291bnRcIjpcIk9SRzAwMDAzM1Rlc3QzM1wiLFwiYXBwQ29kZVwiOlwiQTA2XCJ9IiwiaXNzIjoiY3VudyIsImV4cCI6MTU2NTQwNjM3Mn0.7suZQ1ol4YlT-nHr0iavYYpwKMcrJrDQCj3jWjzyz4A";
        OkGo.get("http://114.116.158.57:9002/v1/class/courses")
                .headers("Authorization", token)
                .params("gradeCode", "1")
                .params("beginDate", "20190805")
                .params("endDate", "20190811")
                .params("classCode", "CLA000001")
                .execute(callback);
    }
}
