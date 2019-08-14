package cn.com.cunw.diandubiapp.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import cn.com.cunw.diandubiapp.App;
import cn.com.cunw.diandubiapp.interfaces.Contants;
import cn.com.cunw.diandubiapp.preference.SourceSpHelper;

/**
 * @author YangBin
 * @time 2019/8/11 16:51
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class DataUtils {

    private static final String TAG = DataUtils.class.getSimpleName();

    public static void initData(Context context) {
        Log.e(TAG, "initData");
        String AUTHORITY = "com.xywschoolcard.provider";
        Uri uri = Uri.parse("content://" + AUTHORITY + "/User");
        String token = "", url = "", version = "";
        String studentId = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            Log.e(TAG, "initData   size = " + cursor.getCount());
            String _catch = "";
            while (cursor.moveToNext()) {
                try {
                    Log.e("xyw", "count====" + cursor.getCount());
                    _catch = "try_ ";
                    token = cursor.getString(cursor.getColumnIndex("accessToken"));
                    url = cursor.getString(cursor.getColumnIndex("url"));
                    version = cursor.getString(cursor.getColumnIndex("version"));
                    studentId = cursor.getString(cursor.getColumnIndex("studentId"));
                } catch (Exception e) {
                    Log.e("xyw", cursor.getColumnIndex("ACCESS_TOKEN") + "=count====" + cursor.getCount());
                    _catch = "catch_ ";
                    token = cursor.getString(cursor.getColumnIndex("ACCESS_TOKEN"));
                    url = cursor.getString(cursor.getColumnIndex("URL"));
                    version = cursor.getString(cursor.getColumnIndex("VERSION"));
                    studentId = cursor.getString(cursor.getColumnIndex("STUDENT_ID"));
                }
            }
            Log.e(TAG, _catch + "initData   token = " + token);
            Log.e(TAG, _catch + "initData   url = " + url);
            Log.e(TAG, _catch + "initData   version = " + version);
            Log.e(TAG, _catch + "initData   studentId = " + studentId);
            SourceSpHelper.getInstance().saveToken("Bearer " + token);
            SourceSpHelper.getInstance().saveAccountId(studentId);
            SourceSpHelper.getInstance().saveUrl(url + "/" + version + "/");
            cursor.close();
        }
    }

    private static Intent sIntent;

    public static void sendBroad(String tag) {
        Log.e(TAG, "发广播  --------");
        if (sIntent == null) {
            sIntent = new Intent(Contants.REFRESH_TOKEN);
        }
        sIntent.putExtra("tag", tag);
        App.getInstance().sendBroadcast(sIntent);
    }
}
