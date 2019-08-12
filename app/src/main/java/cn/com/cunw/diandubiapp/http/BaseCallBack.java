package cn.com.cunw.diandubiapp.http;

import android.util.Log;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.io.IOException;

import cn.com.cunw.diandubiapp.utils.ToastUtis;
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
        String message = response.message();
        JSONObject object = null;
        try {
            object = new JSONObject(mJson);
            code = object.optInt("code");
            message = object.optString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (code == 0) {
            onSuccess(object);
        } else {
            doError(code, message);
        }
    }

    private void doError(int code, String message) {
        if (code == -1) {
            ToastUtis.show("服务器连接异常！");
        } else if (code == 480) {
            Log.e("BaseCallBack", "Token过期！");
        }
        onError(code, message);
    }

    public void onError(int code, String message) {
        Log.e("BaseCallBack", "code = " + code + " ；message = " + message);
    }

    public abstract void onSuccess(JSONObject object);

    @Override
    public void onCacheSuccess(Response<T> response) {

    }

    @Override
    public void onError(Response<T> response) {
        int code = response.code();
        String message = response.message();
        try {
            ResponseBody body = response.getRawResponse().body();
            byte[] bytes = body.bytes();
            JSONObject object = new JSONObject(new String(bytes));
            code = object.optInt("code");
            message = object.optString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        doError(code, message);
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
