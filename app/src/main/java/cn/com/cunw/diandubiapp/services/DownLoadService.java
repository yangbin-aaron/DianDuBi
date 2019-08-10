package cn.com.cunw.diandubiapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Priority;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.cunw.diandubiapp.http.LogDownloadListener;

/**
 * @author YangBin
 * @time 2019/8/9 13:53
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class DownLoadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownLoadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 获取下载目标（List）
        List<Object> list = new ArrayList<>();
        // 下载资源（单线下载），保存到本地，改变下载状态，用EventBus发送进度
        for (Object obj : list) {
            GetRequest<File> request = OkGo.<File>get("下载url");
            OkDownload.request("唯一标识", request)
                    .fileName("") // 文件名
                    .save()
                    .register(new LogDownloadListener())
                    .start();
        }

//        // 删除
//        OkDownload.getInstance().removeTask("");
//        // 暂停
//        OkDownload.getInstance().getTask("").pause();
    }
}
