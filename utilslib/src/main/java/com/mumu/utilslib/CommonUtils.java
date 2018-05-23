package com.mumu.utilslib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class CommonUtils {

    /**
     * 当前按钮点击的时间戳
     */
    public static long mBtnClickTimestamp = 0;

    /**
     * 是否按钮点击非法,判断是否重复点击
     *
     * @return true无效|false有效
     */
    public static boolean IsBtnClickInvalid(long deltaTime) {
        if (deltaTime < 0) {
            deltaTime = 100;
        }

        long curBtnClickTimestamp = System.currentTimeMillis();
        if (curBtnClickTimestamp - mBtnClickTimestamp > deltaTime) {
            mBtnClickTimestamp = curBtnClickTimestamp;
            return false;
        }

        return true;
    }

    /**
     * 是否按钮点击非法,判断是否重复点击
     *
     * @return true无效|false有效
     */
    public static boolean IsBtnClickInvalid() {
        return IsBtnClickInvalid(100);
    }

    /**
     * 通过包名打开应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 是否打开成功
     */
    public static boolean startAppByPackageName(Context context, String packageName) {
        boolean bIsStartAppOk = true;
        PackageInfo pi;
        try {
            pi = context.getApplicationContext().getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getApplicationContext().getPackageManager();
            List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//重点是加这个
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            bIsStartAppOk = false;
        }

        return bIsStartAppOk;
    }
}
