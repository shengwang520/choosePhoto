package com.wang.choosephoto.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.wang.choosephoto.R;


/**
 * app 页面过度动画
 */
public class AppCompat {

    /**
     * 跳转界面
     */
    public static void startActivity(Context context, Intent intent) {
        ActivityCompat.startActivity(context, intent, null);
    }

    /**
     * 跳转界面
     */
    public static void startActivity(Activity activity, Intent intent, int requestCode) {
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    /**
     * 跳转界面-从下往上动画
     */
    public static void startActivityAnim(Context context, Intent intent) {
        ActivityCompat.startActivity(context, intent, ActivityOptions.makeCustomAnimation(context, R.anim.x_slide_in_from_bottom, R.anim.x_slide_out_to_top).toBundle());
    }

    /**
     * 淡入淡出动画
     */
    public static void startActivityAnimFade(Activity context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.startActivity(context, intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
        } else {
            startActivity(context, intent);
        }
    }

    /**
     * 淡入淡出动画
     */
    public static void startActivityAnimFade(Activity activity, Intent intent, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.startActivityForResult(activity, intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            startActivity(activity, intent, requestCode);
        }
    }

    /**
     * 淡入淡出动画
     */
    public static void startActivityAnimFade(Context context, Intent intent) {
        if (context instanceof Activity) {
            startActivityAnimFade((Activity) context, intent);
        } else {
            startActivity(context, intent);
        }
    }

    /**
     * 启动共享元素activity
     *
     * @param str 共享元素名称
     */
    public static void startActivityAnim(Activity context, Intent intent, View view, String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.startActivity(context, intent, ActivityOptions.makeSceneTransitionAnimation
                    (context, view, str)
                    .toBundle());
        } else {
            startActivity(context, intent);
        }
    }

    /**
     * 启动共享元素activity
     *
     * @param str 共享元素名称
     */
    public static void startActivityAnim(Context context, Intent intent, View view, String str) {
        if (context instanceof Activity) {
            startActivityAnim((Activity) context, intent, view, str);
        } else {
            startActivity(context, intent);
        }
    }
//
//    /**
//     * 跳转界面 需要检查是否登录
//     */
//    public static void startActivityLogin(Context context, Intent intent) {
//        if (User.getInstance().isLogin(context)) {
//            startActivity(context, intent);
//        } else {
//            startActivity(context, LoginPhoneActivity.newIntent(context));
//        }
//    }
//
//    /**
//     * 跳转至登录界面
//     */
//    public static void startActivityLogin(Context context) {
//        startActivity(context, LoginPhoneActivity.newIntent(context));
//    }
}
