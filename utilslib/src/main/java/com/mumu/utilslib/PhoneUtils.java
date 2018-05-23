package com.mumu.utilslib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/22
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class PhoneUtils {

    /**
     * 手机号位数
     */
    private static final int PHONE_LENGTH = 11;

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147、198
     * 联通号码段:130、131、132、136、185、186、145、166
     * 电信号码段:133、153、180、189、199、149
     *
     * @param cellphone 手机号码
     * @return false，格式错误；true，格式正确
     * @author caoyoulin
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\\\d{8}$";
        if (cellphone.length() != PHONE_LENGTH) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(cellphone);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 屏蔽手机号中间段
     *
     * @param replace
     * @return
     */
    public static String hideMiddleForPhone(String replace) {
        if (StringUtils.isEmpty(replace)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(replace.substring(0, 3));
        for (int i = 3; i < replace.length() - 4; i++) {
            sb.append("*");
        }
        sb.append(replace.substring(replace.length() - 4));
        return sb.toString();
    }

    /**
     * 打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void startIntentForCalling(Context context, String phoneNum) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可
        context.startActivity(intent);
    }
}
