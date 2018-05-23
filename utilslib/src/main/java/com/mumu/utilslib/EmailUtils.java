package com.mumu.utilslib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/22
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class EmailUtils {

    /**
     * 判断邮箱格式是否正确
     *
     * @param email 邮箱
     * @return
     */
    public static boolean isValidEmail(String email) {
        //"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        if (!email.contains("@")) {
            return false;
        }

        String str = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 转换email格式
     *
     * @param email 待转换的email
     * @return 输出xxx***xxx@xxx.xxxxx
     */
    public static String formatEmailStyle(String email) {
        if (StringUtils.isEmpty(email)) {
            return "";
        }

        int lastIndexOfAt = email.lastIndexOf("@");
        if (lastIndexOfAt > 6) {
            //email转化为xxx***xxx@xxx.xxxxx
            email = email.replaceAll("(\\S{3})(\\S+)(\\S{3})(@\\S+\\.\\S+)", "$1***$3$4");
        }

        return email;
    }
}
