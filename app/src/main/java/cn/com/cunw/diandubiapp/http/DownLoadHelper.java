package cn.com.cunw.diandubiapp.http;

import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import java.io.File;

import cn.com.cunw.diandubiapp.beans.SourceBean;

/**
 * @author YangBin
 * @time 2019/8/10 9:24
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class DownLoadHelper {

    private static DownLoadHelper sHelper;

    public static DownLoadHelper getInstance() {
        if (sHelper == null) {
            sHelper = new DownLoadHelper();
        }
        return sHelper;
    }

    private static String sPath;

    // 相关设置
    public static void init() {
        sPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa/";
        // 设置下载，保存文件的目录
        OkDownload.getInstance().setFolder(sPath);
        // 最大下载数
        OkDownload.getInstance().getThreadPool().setCorePoolSize(1);
    }

    public String getPath() {
        return sPath;
    }

    /**
     * 下载
     *
     * @param itemBean
     */
    public void downSource(SourceBean.ItemBean itemBean) {
        GetRequest<File> request = OkGo.get(itemBean.downloadUrl);
        OkDownload.request(itemBean.id, request)
                .fileName(itemBean.fileName) // 文件名
                .save()
                .register(new LogDownloadListener())
                .start();
    }
}
