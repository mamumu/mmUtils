package com.mumu.utilslib;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class SharedPreUtils {
    public final static String CACHE = "CACHE";
    public final static String CACHE_SEED = "cache_seed";//加密种子

    private SharedPreferences sp;
    static private SharedPreUtils instance;

    static public SharedPreUtils getInstance(Application application) {
        if (instance == null)
            instance = new SharedPreUtils(application.getApplicationContext());
        return instance;
    }

    private SharedPreUtils(Context context) {
        sp = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    }

    /**
     * SharedPreferences通过key取值
     * Add library 'Gradle: com.android.support:support-annotations:27.1.1@jar' to classpath
     *
     * @param key
     * @param dfValue 默认值
     * @return
     */
    public String getValue(String key, String dfValue) {
        key = EncryptUtils.md5_32(key);
        String value = sp.getString(key, dfValue);
        if (!dfValue.equals(value)) {
            value = EncryptUtils.decryptAES(CACHE_SEED, value);
        }

        return value;
    }

    /**
     * SharedPreferences通过key取值 boolean类型
     *
     * @param key
     * @param dfValue 默认值
     * @return
     */
    public boolean getValue(String key, boolean dfValue) {
        key = EncryptUtils.md5_32(key);
        return sp.getBoolean(key, dfValue);
    }

    /**
     * SharedPreferences通过key存value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        key = EncryptUtils.md5_32(key);
        value = EncryptUtils.encryptAES(CACHE_SEED, value);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * SharedPreferences通过key存value, boolean类型
     *
     * @param key
     * @param value
     */
    public void setValue(String key, boolean value) {
        key = EncryptUtils.md5_32(key);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 清空所有数据
     */
    public void clearAllData() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
