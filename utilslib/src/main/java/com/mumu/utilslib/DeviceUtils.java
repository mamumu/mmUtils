package com.mumu.utilslib;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/23
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class DeviceUtils {

    private String UA = Build.MODEL;
    private String mIMEI;// 唯一的设备ID，GSM手机的 IMEI 和 CDMA手机的 MEID
    private String mSIM;// SIM卡的序列号：需要权限：READ_PHONE_STATE
    private String mMobileVersion;// 设置软件的版本号：需要权限：READ_PHONE_STATE
    private String mNetwrokIso;// 当前注册的国家环境代码
    private String mNetType;// 当前的连网类型
    private String mDeviceID;// 唯一设备号
    Context context;


    private TelephonyManager telephonyManager = null;// 很多关于手机的信息可以用此类得到

    private static DeviceUtils instance = null;// 单例模式

    /**
     * 最好用全局的context获取实例
     *
     * @param context
     * @return
     */
    public static synchronized DeviceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DeviceUtils(context);
        }

        return instance;
    }

    private DeviceUtils(Context context) {
        this.context = context;
        findData();
    }

    /**
     * 设置手机立刻震动
     *
     * @param context
     * @param milliseconds milliseconds/1000(S)
     */
    public void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 刷新基本信息
     */
    public void onRefresh() {
        findData();
    }

    /**
     * 获得android设备-唯一标识，android2.2 之前无法稳定运行
     *
     * @return
     */
    private String getDeviceId(Context context) {
        return Settings.Secure
                .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 打印基本信息串
     *
     * @return
     */
    public String getDeviceInfo() {
        StringBuffer info = new StringBuffer();
        info.append("IMEI:").append(getImei());
        info.append("\n");
        info.append("SIM:").append(getSIM());
        info.append("\n");
        info.append("UA:").append(getUA());
        info.append("\n");
        info.append("MobileVersion:").append(mMobileVersion);
        info.append("\n");
        info.append(getCallState());
        info.append("\n");
        info.append("SIM_STATE: ").append(getSimState());
        info.append("\n");
        info.append("SIM: ").append(getSIM());
        info.append("\n");
        info.append(getSimOpertorName());
        info.append("\n");
        info.append(getPhoneType());
        info.append("\n");
        info.append(getPhoneSettings());
        info.append("\n");
        return info.toString();
    }

    /**
     * 获取sim卡的状态
     *
     * @return
     */
    public String getSimState() {
        switch (telephonyManager.getSimState()) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "未知SIM状态_"
                        + TelephonyManager.SIM_STATE_UNKNOWN;
            case TelephonyManager.SIM_STATE_ABSENT:
                return "没插SIM卡_"
                        + TelephonyManager.SIM_STATE_ABSENT;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "锁定SIM状态_需要用户的PIN码解锁_"
                        + TelephonyManager.SIM_STATE_PIN_REQUIRED;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "锁定SIM状态_需要用户的PUK码解锁_"
                        + TelephonyManager.SIM_STATE_PUK_REQUIRED;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "锁定SIM状态_需要网络的PIN码解锁_"
                        + TelephonyManager.SIM_STATE_NETWORK_LOCKED;
            case TelephonyManager.SIM_STATE_READY:
                return "就绪SIM状态_"
                        + TelephonyManager.SIM_STATE_READY;
            default:
                return "未知SIM状态_"
                        + TelephonyManager.SIM_STATE_UNKNOWN;
        }
    }

    /**
     * 获取手机信号类型
     *
     * @return
     */
    public String getPhoneType() {
        switch (telephonyManager.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_NONE:
                return "PhoneType: 无信号_"
                        + TelephonyManager.PHONE_TYPE_NONE;
            case TelephonyManager.PHONE_TYPE_GSM:
                return "PhoneType: GSM信号_"
                        + TelephonyManager.PHONE_TYPE_GSM;
            case PHONE_TYPE_CDMA:
                return "PhoneType: CDMA信号_"
                        + PHONE_TYPE_CDMA;
            default:
                return "PhoneType: 无信号_"
                        + TelephonyManager.PHONE_TYPE_NONE;
        }
    }

    /**
     * 服务商名称：例如：中国移动、联通 　　 SIM卡的状态必须是 SIM_STATE_READY就绪状态(使用getSimState()判断).
     */
    public String getSimOpertorName() {
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            StringBuffer sb = new StringBuffer();
            sb.append("SimOperatorName: ").append(
                    telephonyManager.getSimOperatorName());
            sb.append("\n");
            sb.append("SimOperator: ")
                    .append(telephonyManager.getSimOperator());
            sb.append("\n");
            sb.append("Phone:").append(telephonyManager.getLine1Number());
            return sb.toString();
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("SimOperatorName: ").append("未知");
            sb.append("\n");
            sb.append("SimOperator: ").append("未知");
            return sb.toString();
        }
    }

    /**
     * 获取手机的基本设置
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getPhoneSettings() {
        StringBuffer buf = new StringBuffer();
        String str = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.BLUETOOTH_ON);
        buf.append("蓝牙:");
        if (str.equals("0")) {
            buf.append("禁用");
        } else {
            buf.append("开启");
        }

        str = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.BLUETOOTH_ON);
        buf.append("WIFI:");
        buf.append(str);

        str = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.INSTALL_NON_MARKET_APPS);
        buf.append("APP位置来源:");
        buf.append(str);

        return buf.toString();
    }

    /**
     * 电话活动的状态
     *
     * @return
     */
    public String getCallState() {
        switch (telephonyManager.getCallState()) {
            case TelephonyManager.CALL_STATE_IDLE:
                return "电话状态[CallState]: 挂断";
            case TelephonyManager.CALL_STATE_OFFHOOK:
                return "电话状态[CallState]: 接听";
            case TelephonyManager.CALL_STATE_RINGING:
                return "电话状态[CallState]: 来电";
            default:
                return "电话状态[CallState]: 未知";
        }
    }

    // 设置基本信息
    private void findData() {
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        mIMEI = telephonyManager.getDeviceId();
        mMobileVersion = telephonyManager.getDeviceSoftwareVersion();
        mNetwrokIso = telephonyManager.getNetworkCountryIso();
        mSIM = telephonyManager.getSimSerialNumber();
        mDeviceID = getDeviceId();

        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            // WIFI/MOBILE
            mNetType = info.getTypeName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 获取设备号
    private String getDeviceId() {
        return Settings.Secure
                .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getNetwrokIso() {
        return mNetwrokIso;
    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public String getNetType() {
        return mNetType;
    }

    public String getImei() {
        return mIMEI;
    }

    public String getSIM() {
        return mSIM;
    }

    public String getUA() {
        return UA;
    }



    /**
     * 根据手机号发送短信
     *
     * @param context
     * @param phone
     */
    public static void sendSmsByPhone(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        // intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    /**
     * 根据内容调用手机通讯录
     *
     * @param context
     * @param content
     */
    public static void sendSmsByContent(Context context, String content) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }


    /**
     * 方法功能：androidId > imeiId > UUID
     * 组合设备唯一标识符，防止为空
     */
    public String getSoleDeviceId() {
        String res = getDeviceId();//androidId
        if (!TextUtils.isEmpty(res)) {
            return res;
        }
        res = getImei();//imei标识符
        if (!TextUtils.isEmpty(res)) {
            return res;
        }
        //如果一个都没有，就生成一个UUID并持久化保存
        res = getUUID();
        return res;
    }

    /**
     * 创建一个UUID并保存
     */
    private String getUUID() {
        String uuid = null;
        SharedPreferences sp = context.getSharedPreferences("Cache",
                context.MODE_PRIVATE);
        if (sp != null) {
            uuid = sp.getString("uuid", "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("uuid", uuid);
                editor.commit();
            }
        }
        return uuid;
    }

    /**
     * 获取App版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 获取App版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionNo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            String macAddress = info.getMacAddress();
            if (macAddress != null) {
                return macAddress.replace(":", "");
            }
        }
        return null;
    }

    /**
     * 获取手机联系人
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
     *
     * @param context 上下文;
     * @return 联系人链表
     */
    public static List<HashMap<String, String>> getAllContactInfo(Context context) {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"},
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!StringUtils.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[]{"data1",
                                "mimetype"}, "raw_contact_id=?",
                        new String[]{contact_id}, null);
                HashMap<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }
}
