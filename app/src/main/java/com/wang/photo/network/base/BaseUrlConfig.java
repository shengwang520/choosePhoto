package com.wang.photo.network.base;


import com.common.app.BuildConfig;

/**
 * 动态配置主机地址
 */
public class BaseUrlConfig {
    public static String BASE_URL;//使用主机地址

    static {
        BASE_URL = BuildConfig.DEBUG ? BaseUrl.BASE_URL_DEBUG : BaseUrl.BASE_URL_RELEASE;
    }
}
