package cn.com.cunw.diandubiapp.http;

import android.util.Log;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * @author YangBin
 * @time 2019/8/9 16:08
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public abstract class BaseCallBack<T> implements Callback<T> {

    private String mJson;

    @Override
    public T convertResponse(okhttp3.Response response) {
        final ResponseBody body = response.body();
        byte[] bytes = new byte[0];
        try {
            bytes = body.bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mJson = new String(bytes);
        return null;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {

    }

    @Override
    public void onSuccess(Response<T> response) {
        int code = response.code();
        JSONObject object = null;
        try {
            object = new JSONObject(mJson);
            code = object.optInt("code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (code == 0 || code == 200) {
            onSuccess(object);
        } else {
            onError(response);
        }
    }

    public abstract void onSuccess(JSONObject object);

    @Override
    public void onCacheSuccess(Response<T> response) {

    }

    @Override
    public void onError(Response<T> response) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void uploadProgress(Progress progress) {

    }

    @Override
    public void downloadProgress(Progress progress) {

    }
}
