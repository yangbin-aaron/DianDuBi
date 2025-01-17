package cn.com.cunw.diandubiapp.preference;

import android.content.Context;

/**
 * @author YangBin
 * @time 2019/8/9 8:50
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class SourceSpHelper {
    private static final String SOURCE_INFO = "source_info";

    private static SourceSpHelper mInstance;

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new SourceSpHelper(context);
        }
    }

    public static SourceSpHelper getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("AccountHelper instances is null, you need call init() method.");
        }
        return mInstance;
    }

    private SharedPreferencesHelper mHelper;

    private SourceSpHelper(Context context) {
        mHelper = new SharedPreferencesHelper(context, SOURCE_INFO);
    }

    public void saveToken(String token) {
        mHelper.putString("token", token);
    }

    public String getToken() {
        return mHelper.getString("token");
    }

    public void saveAccountId(String id) {
        mHelper.putString("id", id);
    }

    public String getAccountId() {
        return mHelper.getString("id");
    }

    public void saveUrl(String url) {
        mHelper.putString("url", url);
    }

    public String getUrl() {
        return mHelper.getString("url");
    }
}
