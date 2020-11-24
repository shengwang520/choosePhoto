package com.common.app.common.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;

public abstract class BaseViewItemFinder {

    public View mView;

    public BaseViewItemFinder(View view) {
        this.mView = view;
    }

    public Context getContext() {
        return mView.getContext();
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return mView.findViewById(id);
    }
}
