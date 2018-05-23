package com.mumu.utilslib;

import android.text.TextUtils;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class BankCardUtils {
    /**
     * 屏蔽银行卡号中间段
     *
     * @param replace
     * @return
     */
    public static String hideMiddleForBankCard(String replace) {
        if (TextUtils.isEmpty(replace)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(replace.substring(0, 4));
        for (int i = 4; i < replace.length() - 4; i++) {
            if (i % 4 == 0) {
                //每4位添加一个空格
                sb.append(" ");
            }
            sb.append("*");
        }
        //最后倒数4位前,添加空格
        sb.append(" ");
        sb.append(replace.substring(replace.length() - 4));
        return sb.toString();
    }

    /**
     * 银行卡号每隔四个数加密,并且加短横线
     *
     * @param replace
     * @return
     */
    public static String repalceWithLineForBankCard(String replace) {
        if (TextUtils.isEmpty(replace)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(replace.substring(0, 4));
        for (int i = 4; i < replace.length() - 4; i++) {
            if (i % 4 == 0) {
                //每4位添加一个空格
                sb.append("-");
            }
            sb.append("*");
        }
        //最后倒数4位前,添加空格
        sb.append("-");
        sb.append(replace.substring(replace.length() - 4));
        return sb.toString();
    }

    /**
     * 银行卡每隔四个数加短横线
     *
     * @param str 银行卡号
     * @return 格式化后的银行卡号
     */
    public static String repalceWithOnlyLineForBankCard(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.substring(0, str.length() - 4));

        for (int i = 0; i < sb.length(); i++) {
            if (i % 5 == 0) {
                sb.insert(i, "-");
            }
        }
        sb.append("-");
        sb.append(str.substring(str.length() - 4));
        sb.deleteCharAt(0);
        return sb.toString();
    }
}
