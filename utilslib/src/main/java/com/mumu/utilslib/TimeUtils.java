package com.mumu.utilslib;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class TimeUtils {
    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getDateTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }
    //获得当天24点时间
    public static Long getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    //获得当天0点时间
    public static int getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取传入时间戳的年数
     *
     * @param time 传入的时间戳
     * @return
     */
    public static String getYearFromTime(String time) {
        if (null == time || time.length() == 0) {
            return "";
        }
        time = time.trim();
        // 如果是纯数字,则进行日期转换
        if (StringUtils.isNumberic(time)) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new java.util.Date(Long.parseLong(time)));
            if (null != dateStr && dateStr.length() > 0) {
                time = dateStr;
            }
        }

        // 默认长度为:"2018-02-22 00:00:00"总计19位
        if (time.length() < 19) {
            return time;
        }

        return time.substring(0, 4);
    }
    /**
     * 获取传入时间戳的年月日
     *
     * @param time 传入的时间戳
     * @return
     */
    public static String getYearMonthDayFromTime(String time) {
        if (null == time || time.length() == 0) {
            return "";
        }
        time = time.trim();
        // 如果是纯数字,则进行日期转换
        if (StringUtils.isNumberic(time)) {
            String dateStr = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
                    .format(new java.util.Date(Long.parseLong(time)));
            if (null != dateStr && dateStr.length() > 0) {
                time = dateStr;
            }
        }

        // 默认长度为:"2018-02-22 00:00:00"总计19位
        if (time.length() < 19) {
            return time;
        }

        return time.substring(0, 10);
    }
    /**
     * 获取传入时间戳的月数
     *
     * @param time 传入的时间戳
     * @return
     */
    public static String getMouthFromTime(String time) {
        if (null == time || time.length() == 0) {
            return "";
        }
        time = time.trim();
        // 如果是纯数字,则进行日期转换
        if (StringUtils.isNumberic(time)) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new java.util.Date(Long.parseLong(time)));
            if (null != dateStr && dateStr.length() > 0) {
                time = dateStr;
            }
        }

        // 默认长度为:"2018-02-22 00:00:00"总计19位
        if (time.length() < 19) {
            return time;
        }

        return time.substring(5, 7);
    }

    public static String getFormatTime(String calendar, String format) {
        if (TextUtils.isEmpty(calendar)) {
            return "";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return sdf.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(calendar));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取传入时间戳的天数
     *
     * @param time 传入的时间戳
     * @return
     */
    public static String getDayFromTime(String time) {
        if (null == time || time.length() == 0) {
            return "";
        }
        time = time.trim();
        // 如果是纯数字,则进行日期转换
        if (StringUtils.isNumberic(time)) {
            String dateStr = new SimpleDateFormat("yyyy MM-dd HH:mm:ss")
                    .format(new java.util.Date(Long.parseLong(time)));
            if (null != dateStr && dateStr.length() > 0) {
                time = dateStr;
            }
        }

        // 默认长度为:"2018-02-22 00:00:00"总计19位
        if (time.length() < 19) {
            return time;
        }

        return time.substring(8, 10);
    }
}
