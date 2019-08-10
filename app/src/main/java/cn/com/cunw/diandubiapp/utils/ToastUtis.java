package cn.com.cunw.diandubiapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author YangBin
 * @time 2019/8/9 14:11
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class ToastUtis {
    private static Toast sToast;

    public ToastUtis(Context context) {
        sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void init(Context context) {
        if (sToast == null) {
            new ToastUtis(context);
        }
    }

    public static void show(String text) {
        sToast.setText(text);
        sToast.show();
    }

    public static void show(int text) {
        sToast.setText(text);
        sToast.show();
    }
}
