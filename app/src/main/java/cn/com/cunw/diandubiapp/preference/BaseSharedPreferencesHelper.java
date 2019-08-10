package cn.com.cunw.diandubiapp.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseSharedPreferencesHelper {

    protected Context context;
    protected SharedPreferences prefs;
    protected SharedPreferences.Editor editor;


    public BaseSharedPreferencesHelper(Context context, String fileName) {
        this(context, fileName, Context.MODE_PRIVATE);
    }

    public BaseSharedPreferencesHelper(Context context, String fileName, int mode) {
        this.context = context;
        prefs = context.getSharedPreferences(fileName, mode);
        editor = prefs.edit();
    }


    public boolean getBoolean(String key, boolean defaultVal) {
        return this.prefs.getBoolean(key, defaultVal);
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }


    public String getString(String key, String defaultVal) {
        return this.prefs.getString(key, defaultVal);
    }

    public String getString(String key) {
        return this.getString(key, null);
    }

    public int getInt(String key, int defaultVal) {
        return this.prefs.getInt(key, defaultVal);
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }


    public float getFloat(String key, float defaultVal) {
        return this.prefs.getFloat(key, defaultVal);
    }

    public float getFloat(String key) {
        return this.getFloat(key, 0f);
    }

    public long getLong(String key, long defaultVal) {
        return this.prefs.getLong(key, defaultVal);
    }

    public long getLong(String key) {
        return this.getLong(key, 0L);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defaultVal) {
        return this.prefs.getStringSet(key, defaultVal);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key) {
        return this.prefs.getStringSet(key, null);
    }

    public Map<String, ?> getAll() {
        return this.prefs.getAll();
    }

    public boolean exists(String key) {
        return prefs.contains(key);
    }


    public BaseSharedPreferencesHelper putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        return this;
    }

    public BaseSharedPreferencesHelper putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
        return this;
    }

    public BaseSharedPreferencesHelper putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
        return this;
    }

    public BaseSharedPreferencesHelper putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
        return this;
    }

    public BaseSharedPreferencesHelper putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
        return this;
    }

    public void putMaps(Map<String, String> maps) {
        if (null == maps || maps.size() == 0) return;
        for (Map.Entry<String, String> map : maps.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();
            editor.putString(key, value);
        }
        editor.apply();
    }


    public BaseSharedPreferencesHelper remove(String key) {
        editor.remove(key);
        editor.commit();
        return this;
    }

    public BaseSharedPreferencesHelper removeAll() {
        editor.clear();
        editor.commit();
        return this;
    }


    public void commit() {
        editor.commit();
    }

    public abstract void putObject(String key, Object object);

    public abstract <T> T getObject(String key, Class<T> clazz);

    public abstract <T> List<T> getObjectArray(String key, Class<T> clazz);

}
