package cn.com.cunw.diandubiapp.uis.moresource;

import android.content.Context;

import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.cunw.diandubiapp.base.mvp.BasePresenter;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.BaseCallBack;
import cn.com.cunw.diandubiapp.utils.ToastUtis;

/**
 * @author YangBin
 * @time 2019/8/9 15:43
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class MoreSourcePresenter extends BasePresenter<MoreSourceModel, MoreSourceView> {

    public MoreSourcePresenter(Context context) {
        super(context);
    }

    @Override
    public MoreSourceModel bindModel() {
        return new MoreSourceModel();
    }

    public void getMoreSourceList() {
        mModel.getMoreSourceList(new BaseCallBack() {
            @Override
            public void onSuccess(JSONObject object) {
                List<SourceBean.ItemBean> list2 = new ArrayList<>();
                long totalSize = 0;
                JSONArray jsonArray = object.optJSONArray("data");
                List<SourceBean> list = new ArrayList<>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        SourceBean sourceBean = new SourceBean();
                        sourceBean.gradeName = obj.optString("gradeName");
                        sourceBean.termName = obj.optString("termName");
                        List<SourceBean.ItemBean> bookList = new ArrayList<>();
                        JSONArray array = obj.optJSONArray("bookList");
                        if (array != null) {
                            for (int j = 0; j < array.length(); j++) {
                                JSONObject bookJson = array.optJSONObject(j);
                                SourceBean.ItemBean itemBean = new SourceBean.ItemBean();
                                itemBean.id = bookJson.optString("id");
                                itemBean.subjectName = bookJson.optString("subjectName");
                                itemBean.verisonName = bookJson.optString("verisonName");
                                itemBean.downloadUrl = bookJson.optString("downloadUrl");
                                itemBean.resTitle = bookJson.optString("resTitle");
                                itemBean.fileName = bookJson.optString("fileName");
                                itemBean.fileSize = bookJson.optLong("fileSize");
                                itemBean.autoDownload = bookJson.optBoolean("autoDownload");
                                itemBean.allowFreeDownload = bookJson.optBoolean("allowFreeDownload");
                                itemBean.bugflag = bookJson.optBoolean("bugflag");
                                bookList.add(itemBean);

                                if (itemBean.autoDownload && itemBean.bugflag) {
                                    totalSize += itemBean.fileSize;
                                    list2.add(itemBean);
                                }
                            }
                        }
                        sourceBean.bookList = bookList;
                        list.add(sourceBean);
                    }
                }

                mView.initList(list);
                mView.initAutoList(list2, totalSize);
            }

            @Override
            public void onError(int code, String message) {
                super.onError(code, message);
                mView.onError(message);
            }
        });
    }
}
