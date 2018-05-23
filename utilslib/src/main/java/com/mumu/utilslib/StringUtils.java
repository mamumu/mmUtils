package com.mumu.utilslib;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/22
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class StringUtils {

    /**
     * 判断是否为数字
     *
     * @param checkStr
     * @return
     */
    public static boolean isNumberic(String checkStr) {
        if (checkStr != null && !"".equals(checkStr.trim())) {
            return checkStr.matches("^[0-9]*$");
        }

        return false;
    }

    /**
     * 判断是否为空
     *
     * @param str 传参
     * @return
     */
    public static boolean isEmpty(String str) {
        return (TextUtils.isEmpty(str) || str.equals("null") || str.equals("NULL"));
    }

    /**
     * 比较两个字符串是否相同
     *
     * @param actual   字符串1
     * @param expected 字符串2
     * @return
     */
    public static boolean isEquals(String actual, String expected) {
        return actual == expected
                || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * 保留小数点
     *
     * @param val       传参
     * @param precision 保留几位
     * @return
     */
    public static Double point(double val, int precision) {
        //小数处理，
        BigDecimal bd = new BigDecimal(val);
        return bd.setScale(precision, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * 保留小数点后两位
     *
     * @param val 传参
     * @return
     */
    public static Double point2(double val) {
        return point(val, 2);
    }
}
