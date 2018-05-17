package com.mumu.utilslib;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/17
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：吐司相关的工具类
 */
public class ToastUtils {

    private static Toast currentToast = null;
    private static boolean mbIsNeedCancelWhenDestory = true;

    public interface MessageFilter {
        String filter(String msg);
    }

    public static MessageFilter msgFilter;

    /**
     * Toast消息
     *
     * @param activity
     * @param message  消息内容
     * @param center   是否居中
     * @param time     显示时间
     */
    public static void showToast(final Activity activity, final String message, final boolean center, final int time) {
        final String msg = msgFilter != null ? msgFilter.filter(message) : message;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                mbIsNeedCancelWhenDestory = true;
                if (currentToast == null) {
                    currentToast = Toast.makeText(activity.getApplication(), msg, time);
                }
                currentToast.setText(message);
                currentToast.setDuration(time);
                if (center) currentToast.setGravity(Gravity.CENTER, 0, 0);
                currentToast.show();
            }
        });
    }

    /**
     * 短时间显示Toast消息(1.5s)，并保证运行在UI线程中
     *
     * @param activity Activity
     * @param message  消息内容
     */
    public static void showToastS(final Activity activity, final String message) {
        showToast(activity, message, false, 1500);
    }

    /**
     * 长时间显示Toast消息(3s)，并保证运行在UI线程中
     *
     * @param activity Activity
     * @param message  消息内容
     */
    public static void showToastL(final Activity activity, final String message) {
        showToast(activity, message, false, 3000);
    }

    /**
     * 持久化显示-和界面关闭没有关联-短时间显示Toast消息，并保证运行在UI线程中
     *
     * @param activity Activity
     * @param message  消息内容
     */
    public static void showToastS_Persistent(final Activity activity, final String message) {
        showToastS(activity, message);
        mbIsNeedCancelWhenDestory = false;
    }

    /**
     * 持久化显示-和界面关闭没有关联-长时间显示Toast消息，并保证运行在UI线程中
     *
     * @param activity Activity
     * @param message  消息内容
     */
    public static void showToastL_Persistent(final Activity activity, final String message) {
        showToastL(activity, message);
        mbIsNeedCancelWhenDestory = false;
    }

    /**
     * 关闭Toast
     */
    public static void closeAllToast() {
        try {
            if (currentToast != null && mbIsNeedCancelWhenDestory) {
                currentToast.cancel();
            }
            mbIsNeedCancelWhenDestory = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
