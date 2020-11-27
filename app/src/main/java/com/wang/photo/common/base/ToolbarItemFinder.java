package com.wang.photo.common.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.common.app.R;


/**
 * Created by 圣王 on 2015/3/26 0026.
 */
public abstract class ToolbarItemFinder extends BaseViewItemFinder {

    public Toolbar toolbar;

    protected ToolbarItemFinder(View view) {
        super(view);
        this.toolbar = view.findViewById(R.id.toolbar);
    }


    protected void initToolbar(Activity activity, boolean isShow) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            appCompatActivity.setSupportActionBar(toolbar);
            if (appCompatActivity.getSupportActionBar() == null) return;
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
            appCompatActivity.getSupportActionBar().setElevation(0);
            appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(0);
        }

    }

    /**
     * toobar 控件居中并隐藏title
     *
     * @param rightmargin 距离右边的距离
     */
    protected void initTab(AppCompatActivity appCompatActivity, View view, int rightmargin) {
        initTab(appCompatActivity, view, false, rightmargin);
    }


    /**
     * toobar 控件居中并隐藏title
     */
    protected void initTab(AppCompatActivity appCompatActivity, View view) {
        initTab(appCompatActivity, view, false, 0);
    }


    protected void initTab(AppCompatActivity compatActivity, View view, boolean isMatch, int rightmargin) {
        ActionBar actionBar = compatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Toolbar.LayoutParams params;
        if (isMatch)
            params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        else
            params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        params.rightMargin = rightmargin;
        toolbar.addView(view, params);
    }

    /**
     * 初始化右边布局
     */
    public void initTabRight(AppCompatActivity compatActivity, View view, View.OnClickListener onClickListener) {
        ActionBar actionBar = compatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER | Gravity.END);
        view.setOnClickListener(onClickListener);
        toolbar.addView(view, params);
    }

    /**
     * 初始化标题居中显示
     *
     * @param text        文字
     * @param textsize    文字大小
     * @param textColorId 文字颜色
     */
    protected TextView getTextView(Context context, CharSequence text, int textsize, int textColorId) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(textsize);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(ContextCompat.getColor(context, textColorId));
        return textView;
    }

    /**
     * 默认文字大小16sp,颜色红色
     */
    protected TextView getTextView(Context context, CharSequence text) {
        return getTextView(context, text, 17, R.color.color_222222);
    }

    /**
     * 创建按钮
     */
    protected TextView getTextView(Context context, CharSequence text, int size) {
        return getTextView(text, size, R.color.color_white, 0, 30);
    }


    /**
     * 创建右按钮
     */
    protected TextView getTextView(int resId) {
        return getTextView(null, 15, R.color.color_222222, resId, 40);
    }


    /**
     * 创建右按钮
     */
    protected TextView getTextView(int resId, int endPadding) {
        return getTextView(null, 15, R.color.color_222222, resId, endPadding);
    }


    /**
     * 初始化标题居中显示
     *
     * @param text        文字
     * @param textsize    文字大小
     * @param textColorId 文字颜色
     */
    private TextView getTextView(CharSequence text, int textsize, int textColorId, int resId, int padding) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        if (resId > 0) {
            textView.setText(null);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
        textView.setTextSize(textsize);
        textView.setPadding(0, 0, padding, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(getContext(), textColorId));
        return textView;
    }
}
