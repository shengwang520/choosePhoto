package com.wang.photo.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * 上层holder
 * Created by wangshengqiang on 2017/7/14.
 */

public abstract class BaseViewItemHolder<M> {
    public View mView;
    public Context mContext;
    public int mPosition;

    public BaseViewItemHolder(View mView) {
        this.mView = mView;
        this.mContext = mView.getContext();
    }

    public BaseViewItemHolder(ViewGroup parent, @LayoutRes int res) {
        this(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    public void setData(M data) {

    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return mView.findViewById(id);
    }

}
