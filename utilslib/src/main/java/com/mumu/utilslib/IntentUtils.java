package com.mumu.utilslib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class IntentUtils {
    /**
     * 启动当前界面快捷方式
     *
     * @param context
     * @param target
     */
    public static void launch(Context context, Class<?> target) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        context.startActivity(intent);
    }

    /**
     * 启动当前界面清除其他界面
     *
     * @param context
     */
    public static void launchWithCleanTop(Context context, Class<?> target) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }

    /**
     * 启动当前界面,携带参数
     **/
    public static void launchWithData(Activity self, String key, Object value, Class<?> target) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) value);
        intent.putExtras(bundle);
        intent.setClass(self, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        self.startActivity(intent);
    }

    /**
     * 启动当前界面，关闭时需返回结果
     **/
    public static void launchWithResult(Activity self, String key, Object value, Class<?> target, int requestCode) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) value);
        intent.putExtras(bundle);
        intent.setClass(self, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        self.startActivityForResult(intent, requestCode);
    }

    /**
     * 关闭当前界面，回传结果
     **/
    public static void finishWithResult(Activity self, String key, Object value, Class<?> target, int resultCode) {
        Intent intent = new Intent(self, target);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) value);
        intent.putExtras(bundle);
        self.setResult(resultCode, intent);
        self.finish();
    }

}
