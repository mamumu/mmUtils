package com.mumu.utilslib;

import android.util.Log;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class LogUtils {

    // 自定义log参数
    private static final String LOG_TAG = "mm";
    private static final int LOG_SIZE_LIMIT = 3500;

    /**
     * 统一自定义log，建议使用
     *
     * @param paramClass getClass()或xxx.class
     * @param param      需要打印Object
     */
    public static void LOG_D(Class<?> paramClass, Object param) {
        // 只有debug模式才打印log
        if (BuildConfig.DEBUG) {
            String paramString = param.toString();
            String str = paramClass.getName();
            if (str != null) {
                str = str.substring(1 + str.lastIndexOf("."));
            }
            int i = paramString.length();
            if (i > LOG_SIZE_LIMIT) {
                int j = 0;
                int k = 1 + i / LOG_SIZE_LIMIT;
                while (j < k + -1) {
                    Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT,
                            LOG_SIZE_LIMIT * (j + 1)));
                    j++;
                }
                Log.d(LOG_TAG, paramString.substring(j * LOG_SIZE_LIMIT, i));
            } else {
                Log.d(LOG_TAG, str + " -> " + paramString);
            }
        }
    }
}
