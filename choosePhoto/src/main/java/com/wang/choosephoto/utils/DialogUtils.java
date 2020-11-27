package com.wang.choosephoto.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.wang.choosephoto.R;
import com.wang.choosephoto.widget.CustomProgressDialog;
import com.wang.choosephoto.widget.DownProgressDialog;


/**
 * 进度提示弹出
 * Created by 圣王 on 2015/5/16 0016.
 */
public class DialogUtils {

    public static ProgressDialog create(Context context, String message) {
        return create(context, ProgressDialog.STYLE_SPINNER, message, null, -1, true, null);
    }

    public static ProgressDialog create(Context context, String message, DialogInterface.OnCancelListener onCancelListener) {
        return create(context, ProgressDialog.STYLE_SPINNER, message, null, -1, true, onCancelListener);
    }

    /**
     * 创建自定义进度
     */
    public static CustomProgressDialog create(Context context, boolean isOutCancel, String message) {
        CustomProgressDialog mypDialog = new CustomProgressDialog(context, R.style.Dialog);//实例化
        mypDialog.setMessage(message);//设置ProgressDialog 提示信息
        mypDialog.setCancelable(true);//设置ProgressDialog 是否可以按退回按键取消
        mypDialog.setCanceledOnTouchOutside(isOutCancel);
        mypDialog.show();//让ProgressDialog显示
        return mypDialog;
    }

    /**
     * 创建自定义进度
     */
    public static CustomProgressDialog create(Context context) {
        CustomProgressDialog mypDialog = new CustomProgressDialog(context, R.style.Dialog);//实例化
        mypDialog.setMessage(context.getString(R.string.waiting_loading));//设置ProgressDialog 提示信息
        mypDialog.setCancelable(true);//设置ProgressDialog 是否可以按退回按键取消
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();//让ProgressDialog显示
        return mypDialog;
    }

    /**
     * 创建下载弹窗
     */
    public static DownProgressDialog createDown(Context context) {
        DownProgressDialog mypDialog = new DownProgressDialog(context, R.style.Dialog);//实例化
        mypDialog.setCancelable(true);//设置ProgressDialog 是否可以按退回按键取消
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();//让ProgressDialog显示
        return mypDialog;
    }

//    /**
//     * 创建绑定手机号弹窗
//     */
//    public static BindingPhoneDialog createBinding(Context context) {
//        BindingPhoneDialog mypDialog = new BindingPhoneDialog(context, R.style.Dialog);//实例化
//        mypDialog.setCancelable(true);//设置ProgressDialog 是否可以按退回按键取消
//        mypDialog.setCanceledOnTouchOutside(false);
//        if (User.getInstance().isLogin(context) && !User.getInstance().isBindingPhone(context)) {//没有绑定手机号才显示绑定手机号弹窗
//            mypDialog.show();//让ProgressDialog显示
//        }
//        return mypDialog;
//    }

    public static ProgressDialog create(Context context, int style, String message, String title, int iconid, boolean isCancel, DialogInterface.OnCancelListener onCancelListener) {
        ProgressDialog mypDialog = new ProgressDialog(context);//实例化
        mypDialog.setProgressStyle(style);//设置进度条风格，风格为圆形，旋转的
        mypDialog.setMessage(message);//设置ProgressDialog 提示信息
        mypDialog.setIndeterminate(false);//设置ProgressDialog 的进度条是否不明确
        mypDialog.setCancelable(isCancel);//设置ProgressDialog 是否可以按退回按键取消

        if (TextUtils.isEmpty(title)) {
            mypDialog.setTitle(title);//设置ProgressDialog 标题
        }
        if (iconid > 0) {
            mypDialog.setIcon(iconid);
        }

        if (onCancelListener != null) {
            mypDialog.setOnCancelListener(onCancelListener);
        }
        mypDialog.show();//让ProgressDialog显示
        return mypDialog;
    }

}
