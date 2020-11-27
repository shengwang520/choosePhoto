package com.wang.photo.network;


import com.wang.photo.common.base.BaseApplication;
import com.wang.photo.common.utils.FileUtils;
import com.wang.photo.network.base.BaseUrlConfig;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口服务
 */

public class ApiService {

    private static final int DEFAULT_TIMEOUT = 20;
    private static ApiService mInstance;
    private ApiInterface mApiInterface;

    private ApiService() {

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient(null))
                .baseUrl(BaseUrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加对Gson的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加对rxJava2的支持
                .build();
        mApiInterface = mRetrofit.create(ApiInterface.class);
    }

    public static ApiService getInstance() {
        if (mInstance == null) {
            synchronized (ApiService.class) {
                mInstance = new ApiService();
            }
        }
        return mInstance;
    }

    /**
     * 获取忽略证书的OkHttpClient
     */
    private static OkHttpClient getOkHttpClient(final OnProgressListener onProgressListener) {
        //缓存容量
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        //缓存路径
        String cacheFile = FileUtils.getCacheData(BaseApplication.instance());
        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        if (onProgressListener != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response.newBuilder().body(new FileResponseBody(response.body(), onProgressListener)).build();
                }
            });
        }
        builder.addInterceptor(CachingControlInterceptor.COMMON_PARAMS);
        builder.addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR);
        builder.addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);
        builder.cache(cache);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        try {
            TrustManager[] trustManager = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;    // 返回null
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            builder.sslSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    /**
     * 获取有进度的请求
     */
    public static ApiInterface getApiInterface(OnProgressListener onProgressListener) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient(onProgressListener))
                .baseUrl(BaseUrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加对Gson的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加对rxJava2的支持
                .build();

        return mRetrofit.create(ApiInterface.class);
    }

    /**
     * 获取请求客服端
     */
    public ApiInterface getApiInterface() {
        return mApiInterface;
    }


}
