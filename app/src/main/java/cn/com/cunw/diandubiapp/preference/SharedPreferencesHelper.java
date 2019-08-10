package cn.com.cunw.diandubiapp.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class SharedPreferencesHelper extends BaseSharedPreferencesHelper {

    public SharedPreferencesHelper(Context context, String fileName) {
        super(context, fileName);
    }

    public SharedPreferencesHelper(Context context, String fileName, int mode) {
        super(context, fileName, mode);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SharedPreferencesHelper putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.commit();
        return this;
    }

    public void putObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if (prefs.contains(key)) {
            String objectVal = prefs.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public <T> void putObjectForJson(String key, T data) {
        String json = null;
        try {
            json = toJson(data);
        } catch (Exception e) {

        }
        editor.putString(key, json);
        editor.commit();
    }

    public <T> T getObjectForJson(String key, Type type) {
        String json = prefs.getString(key, null);
        if (json != null) {
            Gson gson = new Gson();
//            Type type = new TypeToken<Object>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    private static <T> String toJson(T data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return json;
    }

    @Override
    public <T> List<T> getObjectArray(String key, Class<T> clazz) {
        return null;
    }
}
