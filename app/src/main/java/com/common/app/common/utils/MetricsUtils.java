package com.common.app.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import static android.view.View.MeasureSpec.UNSPECIFIED;

/**
 * 屏幕测量工具
 */
public class MetricsUtils {

    /**
     * 获取控件的宽度
     *
     * @param offset 偏移量[dp]
     * @param count  每行个数
     */
    public static int getWidth(Context context, float offset, int count) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return (int) ((displayMetrics.widthPixels - dp2px(displayMetrics, offset)) / count);
    }

    public static DisplayMetrics getDisplayMetrics(Context ctx) {
        return ctx.getResources().getDisplayMetrics();
    }

    public static float dp2px(Context context, float dp) {
        return dp2px(context.getResources().getDisplayMetrics(), dp);
    }

    public static float dp2px(DisplayMetrics displayMetrics, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    /**
     * 获取控件的高度
     *
     * @param width 宽度
     * @param scale 宽高比
     */
    public static int getHeight(int width, float scale) {
        return (int) (width / scale);
    }

    public static void measureChildren(View paraent) {
        paraent.measure(UNSPECIFIED, UNSPECIFIED);
    }


    /**
     * 获取屏幕宽
     */
    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels;
    }


    /**
     * 获取屏幕的高
     */
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (context instanceof Activity) {
            //适配刘海屏高度获取
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }
}
