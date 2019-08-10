package cn.com.cunw.diandubiapp.http;

import android.os.Message;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.com.cunw.diandubiapp.interfaces.Contants;

/**
 * @author YangBin
 * @time 2019/8/9 15:13
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class LogDownloadListener extends DownloadListener {

    private Message mMessage;

    public LogDownloadListener() {
        super("LogDownloadListener");
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
    }

    @Override
    public void onFinish(File file, Progress progress) {
        System.out.println("onFinish: " + progress);
    }

    @Override
    public void onRemove(Progress progress) {
        System.out.println("onRemove: " + progress);
    }
}
