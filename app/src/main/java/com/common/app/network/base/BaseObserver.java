package com.common.app.network.base;

import android.content.Context;
import android.text.TextUtils;

import com.common.app.common.base.BaseApplication;
import com.common.app.common.utils.NetWorkUtils;
import com.common.app.common.utils.ToastUtils;
import com.common.app.common.widget.CustomProgressDialog;
import com.common.app.network.ErrorUtils;
import com.common.app.network.user.User;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.common.app.network.ErrorUtils.API_SUCCESS;


/**
 * retrofit2+rxJava2接口请求通用返回
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private Context mContext;
    private CustomProgressDialog dialog;

    public BaseObserver() {
    }

    public BaseObserver(Context context) {
        this.mContext = context;
    }

    public BaseObserver(Context mContext, CustomProgressDialog dialog) {
        this.mContext = mContext;
        this.dialog = dialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Logger.d("api onSubscribe");
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        Logger.d("api onNext");
        onFinish();
        if (response.status == API_SUCCESS) {
            onSuccess(response);
            onSuccess(response.data);
        } else {
            switch (response.status) {
                case ErrorUtils.API_ERROR_12:
                case ErrorUtils.API_ERROR_13:
                    onLoginLose();
                    onError(response.status, response.msg);
                    break;
                case ErrorUtils.API_ERROR_30:
                case ErrorUtils.API_ERROR_31:
                case ErrorUtils.API_ERROR_32:
                case ErrorUtils.API_ERROR_33:
                case ErrorUtils.API_ERROR_34:
                case ErrorUtils.API_ERROR_35:
                    onError(response.status, response.msg, response.data);
                    break;
                default:
                    onError(response.status, response.msg);
                    break;
            }

        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("api onError");
        onFinish();
        e.printStackTrace();
        if (NetWorkUtils.isNetworkConnected(BaseApplication.instance())) {
            onError(ErrorUtils.API_ERROR_405, null);
        } else {
            onError(ErrorUtils.API_ERROR_404, null);
        }
    }

    @Override
    public void onComplete() {
        Logger.d("api onComplete");
    }

    public abstract void onSuccess(T t);

    public void onSuccess(BaseResponse t) {

    }

    public void onError(int error, String msg) {
        if (mContext != null && !TextUtils.isEmpty(msg)) {
            ToastUtils.show(mContext, msg);
        }
        onError(error);
    }

    public void onError(int error) {
        ErrorUtils.showError2msg(mContext, error);
    }

    public void onError(int error, String msg, T t) {

    }

    /**
     * token失效
     */
    public void onLoginLose() {
        User.getInstance().loginOut(BaseApplication.instance());
    }

    public void onFinish() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
