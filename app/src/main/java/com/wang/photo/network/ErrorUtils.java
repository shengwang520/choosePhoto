package com.wang.photo.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.common.app.R;
import com.wang.photo.common.base.BaseApplication;
import com.wang.photo.common.utils.ToastUtils;
import com.wang.photo.common.widget.CustomDialog;


/**
 * 错误码对于msg管理
 */
public class ErrorUtils {
    //通用
    public static final int API_SUCCESS = 0;//成功
    public static final int API_ERROR_404 = 404;//网络异常
    public static final int API_ERROR_405 = 405;//连接超时
    public static final int API_ERROR_12 = 12;//请先登录
    public static final int API_ERROR_13 = 13;//登录失效，请重新登录

    //功能验证
    public static final int API_ERROR_30 = 30;//VIP才可以使用{1}功能
    public static final int API_ERROR_31 = 31;//使用{1}功能需要支付{2}个金币
    public static final int API_ERROR_32 = 32;//使用{1}功能需要您开通会员或支付{2}个金币才可使用
    public static final int API_ERROR_33 = 33;//送礼物才能聊天
    public static final int API_ERROR_34 = 34;//仅对视频认证用户开放
    public static final int API_ERROR_35 = 35;//仅对视频认证用户开放 或金币购买

    @SuppressLint("StaticFieldLeak")
    private static CustomDialog customDialog;

    /**
     * 获取错误码对应的错误信息,并显示
     */
    public static void showError2msg(Context context, int err) {
        String msg = "";
        switch (err) {
            case API_ERROR_405:
                msg = BaseApplication.instance().getString(R.string.api_error_405);
                break;
            case API_ERROR_404:
                msg = BaseApplication.instance().getString(R.string.api_error_404);
                break;
            case API_ERROR_13:
                break;
        }
        if (context != null && !TextUtils.isEmpty(msg)) {
            ToastUtils.show(context, msg);
        }
    }


}
