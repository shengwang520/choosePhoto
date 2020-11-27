package com.wang.choosephoto.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.choosephoto.R;


/**
 * 自定义提示
 */
public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, String s) {
        customShow(context, s);
    }

    public static void show(Context context, int resid) {
        customShow(context, resid);
    }

    /**
     * 自定义布局toast显示
     *
     * @param s 显示的内容
     */
    @SuppressLint("InflateParams")
    private static void customShow(Context context, String s) {
        View view = LayoutInflater.from(context).inflate(R.layout.x_view_custom_toast, null);
        TextView textView = view.findViewById(R.id.uu_toast_tv_title);
        textView.setText(s);
        if (toast != null) toast.cancel();

        toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    /**
     * 自定义布局toast显示
     *
     * @param resid 显示的内容
     */
    private static void customShow(Context context, int resid) {
        show(context, context.getResources().getString(resid));
    }
}
