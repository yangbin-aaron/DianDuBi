package cn.com.cunw.diandubiapp.utils;

import android.os.Environment;
import android.os.StatFs;

import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;

import java.io.File;

import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.http.DownLoadHelper;

/**
 * @author YangBin
 * @time 2019/8/10 14:54
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class FileUtils {
    public static File getFile(SourceBean.ItemBean itemBean) {
        return new File(DownLoadHelper.getInstance().getPath() + itemBean.getFileName());
    }

    public static boolean exists(SourceBean.ItemBean itemBean) {
        File file = getFile(itemBean);
        Progress progress = DownloadManager.getInstance().get(itemBean.id);
        if (progress != null) {
            if (progress.status == Progress.NONE && file.exists()) {
                // 没有下载完成的
                file.delete();
                return false;
            }
        }
        return file.exists();
    }

    public static boolean delete(SourceBean.ItemBean itemBean) {
        File file = getFile(itemBean);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * SDCard 总容量大小
     *
     * @return B
     */
    public static long getTotalSize() {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        String sdcard = Environment.getExternalStorageState();
        String state = Environment.MEDIA_MOUNTED;
        if (sdcard.equals(state)) {
            //获得sdcard上 block的总数
            long blockCount = statFs.getBlockCount();
            //获得sdcard上每个block 的大小
            long blockSize = statFs.getBlockSize();
            //计算标准大小使用：1024，当然使用1000也可以
            long bookTotalSize = blockCount * blockSize;
            return bookTotalSize;

        } else {
            return -1;
        }

    }

    /**
     * 计算Sdcard的剩余大小
     *
     * @return B
     */
    public static long getAvailableSize() {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        String sdcard = Environment.getExternalStorageState();
        String state = Environment.MEDIA_MOUNTED;
        if (sdcard.equals(state)) {
            //获得Sdcard上每个block的size
            long blockSize = statFs.getBlockSize();
            //获取可供程序使用的Block数量
            long blockavailable = statFs.getAvailableBlocks();
            //计算标准大小使用：1024，当然使用1000也可以
            long blockavailableTotal = blockSize * blockavailable;
            return blockavailableTotal;
        } else {
            return -1;
        }
    }
}
