package com.wang.photo.common.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

/**
 * 图片加载
 */
public class GlideLoadUtils {
    private String TAG = "GlideImageLoader";

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    private GlideLoadUtils() {
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param url       加载图片的url地址  String
     * @param imageView 加载图片的ImageView 控件
     */
    public void glideLoad(Context context, String url, ImageView imageView, RequestOptions options) {
        try {
            if (context != null) {
                Glide.with(context).load(url).thumbnail(0.1f).apply(options).into(imageView);
            } else {
                Logger.d(TAG, "Picture x_loading failed,context is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void glideLoad(Activity activity, String url, ImageView imageView, RequestOptions options) {
        try {
            if (!activity.isDestroyed()) {
                Glide.with(activity).load(url).thumbnail(0.1f).apply(options).into(imageView);
            } else {
                Logger.d(TAG, "Picture x_loading failed,activity is Destroyed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void glideLoad(Fragment fragment, String url, ImageView imageView, RequestOptions options) {
        try {
            if (fragment != null && fragment.getActivity() != null) {
                Glide.with(fragment).load(url).thumbnail(0.1f).apply(options).into(imageView);
            } else {
                Logger.d(TAG, "Picture x_loading failed,fragment is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

}
