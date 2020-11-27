package com.wang.choosephoto.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    private static final String MD5_KEY = "ZMoOjQfwgrTlje4UTLdILTgxkWcFvsc";//md5密钥

    /**
     * md5 32位加密
     *
     * @param str 加密前的字符串
     * @return 加密后的结果
     */
    public static String getMD5String(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assert messageDigest != null;
        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }
        //32位加密
        return md5StrBuff.toString();
    }

    /**
     * 获取md5加密
     *
     * @param time  时间搓
     * @param phone 电话
     */
    public static String getSms2Md5(long time, String code, String phone) {
        String str = time + "+" + code + "+" + phone + "+" + MD5_KEY;
        return getMD5String(str);
    }

}
