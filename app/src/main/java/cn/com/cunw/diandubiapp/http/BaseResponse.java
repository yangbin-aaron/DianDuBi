package cn.com.cunw.diandubiapp.http;

import java.io.Serializable;

/**
 * @author YangBin
 * @time 2019/8/9 16:03
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class BaseResponse<T> implements Serializable {
    public int code;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
