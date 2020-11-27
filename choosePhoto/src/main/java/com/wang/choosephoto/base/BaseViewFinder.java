package com.wang.choosephoto.base;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

public abstract class BaseViewFinder {

    public Activity mActivity;

    public BaseViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return mActivity.findViewById(id);
    }
}
