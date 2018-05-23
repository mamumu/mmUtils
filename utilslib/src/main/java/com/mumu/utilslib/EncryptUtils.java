package com.mumu.utilslib;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/22
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：
 */
public class EncryptUtils {
    /**
     * 32位MD5加密
     *
     * @param paramString 传参
     * @return
     */
    public static String md5_32(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            return toHexString(localMessageDigest.digest());
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return paramString;
    }

    /**
     * 16位MD5加密
     *
     * @param paramString 传参
     * @return
     */
    public static String md5_16(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            return toHexString(localMessageDigest.digest()).substring(8, 24);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return paramString;
    }

    /**
     * base64加密
     *
     * @param paramString 传参
     * @return
     */
    public static String base64(String paramString) {
        return base64(paramString, Base64.DEFAULT);
    }

    private static String base64(String paramString, int base64Type) {
        String enToStr = base64(paramString.getBytes(), base64Type);
        return enToStr;
    }

    private static String base64(byte[] paramBytes, int base64Type) {
        String enToStr = Base64.encodeToString(paramBytes, base64Type);
        return enToStr;
    }

    /**
     * base64解密
     *
     * @param paramString 传参
     * @return
     */
    public static String base64Decode(String paramString) {
        return base64Decode(paramString, Base64.DEFAULT);
    }

    /**
     * base64解密
     *
     * @param paramString 传参
     * @param base64Type  解密类型
     * @return
     */
    public static String base64Decode(String paramString, int base64Type) {
        String deToStr = new String(Base64.decode(paramString.getBytes(), base64Type));
        return deToStr;
    }

    private static final char[] HEX_DIGITS = {48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 97, 98, 99, 100, 101, 102};

    private static String toHexString(byte[] paramArrayOfByte) {
        int length = paramArrayOfByte.length;
        StringBuilder localStringBuilder = new StringBuilder(2 * length);
        for (int i = 0; ; ++i) {
            if (i >= length)
                return localStringBuilder.toString().toUpperCase();
            localStringBuilder
                    .append(HEX_DIGITS[((0xF0 & paramArrayOfByte[i]) >>> 4)]);
            localStringBuilder.append(HEX_DIGITS[(0xF & paramArrayOfByte[i])]);
        }
    }

    /**
     * SHA1加密
     *
     * @param val
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-1");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return toHexString(m);
    }


    private final static String HEX = "0123456789ABCDEF";
    //密钥是跟服务端约定好写死的256位的密钥；或者服务端定期会改变，然后告诉我们
    private static String VCREDIT_AES_KEY = "AsDfGUYUAsDfGUYU";

    /**
     * AES加密
     *
     * @param key 密钥
     * @param src 加密文本
     * @return
     * @throws Exception
     */
    public static String encryptAES(String key, String src) {
        byte[] result = new byte[0];
        try {
            byte[] rawKey = VCREDIT_AES_KEY.getBytes();
            result = encrypt(rawKey, src.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(result);
    }

    /**
     * AES解密
     *
     * @param key       密钥
     * @param encrypted 待揭秘文本
     * @return
     * @throws Exception
     */
    public static String decryptAES(String key, String encrypted) {
        byte[] result = new byte[0];
        try {
            byte[] rawKey = VCREDIT_AES_KEY.getBytes();
            byte[] enc = toByte(encrypted);
            result = decrypt(rawKey, enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(result);
    }

    /**
     * 真正的加密过程
     *
     * @param key
     * @param src
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }

    /**
     * 真正的解密过程
     *
     * @param key
     * @param encrypted
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] key, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
