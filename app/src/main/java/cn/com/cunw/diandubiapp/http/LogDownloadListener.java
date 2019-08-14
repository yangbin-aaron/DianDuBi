package cn.com.cunw.diandubiapp.http;

import android.os.Message;
import android.util.Log;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.utils.DataUtils;
import cn.com.cunw.diandubiapp.utils.ToastUtis;

/**
 * @author YangBin
 * @time 2019/8/9 15:13
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class LogDownloadListener extends DownloadListener {

    private Message mMessage;

    // 第一次进来的时候，自动下载不刷新token，如果下载失败，用户手动去下载即可
    private boolean mHandlerToken;

    public LogDownloadListener(boolean handlerToken) {
        super("LogDownloadListener");
        mHandlerToken = handlerToken;
        mMessage = new Message();
        mMessage.what = Contants.WHAT_GUIDE_DOWN;
    }

    @Override
    public void onStart(Progress progress) {
        System.out.println("onStart: " + progress);
    }

    @Override
    public void onProgress(Progress progress) {
        System.out.println("onProgress: " + progress);
        mMessage.obj = progress;
        EventBus.getDefault().post(mMessage);
    }

    @Override
    public void onError(Progress progress) {
        System.out.println("onError: " + progress);
        progress.exception.printStackTrace();
        mMessage.obj = progress;
        EventBus.getDefault().post(mMessage);
        String message = progress.exception.getMessage();
        if ("480".equals(message)) {
            Log.e("down", "Token过期!");
            if (mHandlerToken) {
                // 刷新Token
                DataUtils.sendBroad("down_" + progress.tag);
            }
        } else {
            ToastUtis.show("访问异常！");
            Log.e("down", "code = " + message);
        }
    }

    @Override
    public void onFinish(File file, Progress progress) {
        System.out.println("onFinish: " + progress);
        mMessage.obj = progress;
        EventBus.getDefault().post(mMessage);
    }

    @Override
    public void onRemove(Progress progress) {
        System.out.println("onRemove: " + progress);
    }
}
