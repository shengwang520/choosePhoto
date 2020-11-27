package com.wang.choosephoto.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 信息验证
 */
public class ValidateUtil {
    private static final String PHONE_PATTERN = "^(1)\\d{10}$";//手机号

    /**
     * 验证手机号
     */
    public static boolean checkIsPhone(String phonenum) {
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phonenum);
        return m.matches();
    }
} 