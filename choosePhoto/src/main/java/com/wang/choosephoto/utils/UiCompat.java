package com.wang.choosephoto.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.orhanobut.logger.Logger;
import com.wang.choosephoto.R;


/**
 * ui一体化状态栏
 * Created by wangshengqiang on 2016/12/16.
 */

public class UiCompat {

    /**
     * 图片全屏透明状态栏（图片位于状态栏下面）
     */
    public static void setImageTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.color_transparent));
        }
    }

    /**
     * 设置appbar的距上的内边距
     *
     * @param toolbar toolbar
     */
    public static void setToolbarMarginTopColl(Activity activity, Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = getStatusBarHeight(activity);
            toolbar.setLayoutParams(params);
        }
    }

    /**
     * 获取状态栏的高度
     */
    private static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Desc: 获取虚拟按键高度 放到工具类里面直接调用即可
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (isNavigationBarExist(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        Logger.d("虚拟键盘高度" + result);
        return result;
    }

    /**
     * 判断是否存在导航栏
     * 需要再activity onCreate 中添加一下代码判断才生效
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
     * window.navigationBarColor = ContextCompat.getColor(this, R.color.color_white)
     * }
     */
    private static boolean isNavigationBarExist(@NonNull Context context) {
        String NAVIGATION = "navigationBarBackground";
        if (context instanceof Activity) {
            ViewGroup vp = (ViewGroup) ((Activity) context).getWindow().getDecorView();
            if (vp != null) {
                for (int i = 0; i < vp.getChildCount(); i++) {
                    vp.getChildAt(i).getContext().getPackageName();

                    if (vp.getChildAt(i).getId() != -1 && NAVIGATION.equals(context.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
