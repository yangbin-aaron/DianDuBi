package cn.com.cunw.diandubiapp.base.popup;import android.support.annotation.IntDef;import java.lang.annotation.Retention;import java.lang.annotation.RetentionPolicy;/** * @author LiuJianjun * @time 2018-09-19 11:39 * <p> * 功能： */@IntDef({        VerticalGravity.CENTER,        VerticalGravity.ABOVE,        VerticalGravity.BELOW,        VerticalGravity.ALIGN_TOP,        VerticalGravity.ALIGN_BOTTOM,})@Retention(RetentionPolicy.SOURCE)public @interface VerticalGravity {    int CENTER = 0;    int ABOVE = 1;    int BELOW = 2;    int ALIGN_TOP = 3;    int ALIGN_BOTTOM = 4;}