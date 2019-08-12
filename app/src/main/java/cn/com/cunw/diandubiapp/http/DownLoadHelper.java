package cn.com.cunw.diandubiapp.http;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import java.io.File;

import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.preference.SourceSpHelper;
import cn.com.cunw.diandubiapp.utils.FileUtils;

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
    public static void init(String path) {
        sPath = path;
        // 设置下载，保存文件的目录
        OkDownload.getInstance().setFolder(sPath);
        // 最大下载数
        OkDownload.getInstance().getThreadPool().setCorePoolSize(2);
    }

    public String getPath() {
        return sPath;
    }

    /**
     * 下载
     *
     * @param itemBean
     */
    public void downSource(SourceBean.ItemBean itemBean, boolean h) {
        BaseActivity.sItemBean = itemBean;
        long availableSize = FileUtils.getAvailableSize();
        if (availableSize - itemBean.fileSize > Contants.MAX_SD_SIZE) {
            String token = SourceSpHelper.getInstance().getToken();
            GetRequest<File> request = OkGo.get(itemBean.downloadUrl);
            request.headers("Authorization", token);
            OkDownload.request(itemBean.id, request)
                    .fileName(itemBean.getFileName()) // 文件名
                    .save()
                    .register(new LogDownloadListener(h))
                    .start();
        } else {
            Log.e("DownLoadHelper", "空间不足，剩余空间为：" + (int) (availableSize / 1024 / 1024));
        }
    }
}
