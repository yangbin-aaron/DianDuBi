package cn.com.cunw.diandubiapp.interfaces;

/**
 * @author YangBin
 * @time 2019/8/9 13:37
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public interface Contants {
    int WHAT_GUIDE_DOWN = 10002;
    int WHAT_GUIDE_ERROR = 10004;
    int WHAT_GUIDE_FINISH = 10005;
    int WHAT_REGISTER_EVENTBUG = 10001;

    long MAX_SD_SIZE = 500 * 1024 * 1024;

    //刷新token广播
    String REFRESH_TOKEN = "com.cunw.broadcast.REFRESH_TOKEN";
    //token 刷新成功广播
    String REFRESH_TOKEN_SUCCESS = "com.cunw.broadcast.REFRESH_TOKEN_SUCCESS";

}
