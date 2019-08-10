package cn.com.cunw.diandubiapp.beans;

import java.util.List;

/**
 * @author YangBin
 * @time 2019/8/5 15:04
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class SourceBean {
    public String gradeName;
    public String termName;

    public List<ItemBean> bookList;

    public static class ItemBean {
        public String id;
        public String subjectName;
        public String verisonName;
        public String downloadUrl;
        public String resTitle;
        private String fileName;
        public long fileSize;
        public boolean autoDownload;
        public boolean allowFreeDownload;
        public boolean bugflag;
        public boolean needDelete;

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return id + this.fileName;
        }
    }
}
