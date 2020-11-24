package com.common.app.network;

import com.common.app.common.base.BaseApplication;
import com.common.app.common.utils.NetWorkUtils;
import com.common.app.common.utils.SystemUtil;
import com.common.app.common.utils.VersionUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存策略
 */
public class CachingControlInterceptor {
    private static final int TIMEOUT_CONNECT = 2; //2秒
    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取retrofit @headers里面的参数，参数可以自己定义，在本例我自己定义的是cache，跟@headers里面对应就可以了
            String cache = chain.request().header("cache");
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是5秒，方便观察。注意这里的cacheControl是服务器返回的
            if (cacheControl == null) {
                //如果cache没值，缓存时间为TIMEOUT_CONNECT，有的话就为cache的值
                if (cache == null || "".equals(cache)) {
                    cache = TIMEOUT_CONNECT + "";
                }
                originalResponse = originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + cache)
                        .build();
                return originalResponse;
            } else {
                return originalResponse;
            }
        }
    };
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24; //1天
    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //离线的时候为1天的缓存。
            if (!NetWorkUtils.isNetworkConnected(BaseApplication.instance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)
                        .build();
            }
            return chain.proceed(request);
        }
    };

    /**
     * 添加请求公共参数
     */
    public static final Interceptor COMMON_PARAMS = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .header("Device", "ANDROID")
                    .header("VersionCode", String.valueOf(VersionUtils.getVersionCode(BaseApplication.instance())))
                    .header("PhoneVersion", SystemUtil.getSystemVersion())
                    .header("PhoneModel", SystemUtil.getSystemModel())
                    .header("VersionName", VersionUtils.getVersionName(BaseApplication.instance()))
                    .build();
            return chain.proceed(request);
        }
    };
}
