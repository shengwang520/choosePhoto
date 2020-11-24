package com.common.app.common.base;

import android.app.Activity;
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
 * activity 引入toolbar
 */
public abstract class ToolbarFinder extends BaseViewFinder {
    public Toolbar toolbar;
    protected AppCompatActivity compatActivity;

    protected ToolbarFinder(Activity activity) {
        super(activity);
        if (activity instanceof AppCompatActivity)
            this.compatActivity = (AppCompatActivity) activity;
        else
            throw new IllegalArgumentException(activity.getLocalClassName() + " must be instanceof AppCompatActivity");
        this.toolbar = activity.findViewById(R.id.toolbar);
        if (this.toolbar == null)
            throw new IllegalArgumentException(activity.getLocalClassName() + " must have a Toolbar with id=toolbar");

        initToolbar((AppCompatActivity) activity);

    }


    private void initToolbar(final AppCompatActivity activity) {
        initToolbar(activity, true);
    }

    public void initToolbar(final AppCompatActivity activity, int resId) {
        initToolbar(activity, resId, true);
    }

    public void initToolbar(AppCompatActivity activity, boolean isShow) {
        initToolbar(activity, R.drawable.x_close_btn, isShow);
    }

    public void initToolbar(AppCompatActivity activity, int resId, boolean isShow) {
        activity.setSupportActionBar(toolbar);//toolbar与Activity绑定。
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(isShow);//显示toolbar左上角的返回按钮。
            bar.setHomeAsUpIndicator(resId);//给左上角返回按钮设置图片。
            bar.setElevation(0);//设置阴影，高版本才能看见。
        }
    }

    /**
     * toobar 控件居中并隐藏title
     */
    protected void initTab(View view) {
        initTab(view, false, 0);
    }

    /**
     * toobar 控件居中并隐藏title
     *
     * @param rightmargin 距离右边的距离
     */
    protected void initTab(View view, int rightmargin) {
        initTab(view, false, rightmargin);
    }


    /**
     * toobar 控件居中并隐藏title
     *
     * @param isMatch     是否居中
     * @param rightmargin 距离右边距离
     */
    protected void initTab(View view, boolean isMatch, int rightmargin) {
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
    public void initTabRight(View view, View.OnClickListener onClickListener) {
        ActionBar actionBar = compatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER | Gravity.END);
        view.setOnClickListener(onClickListener);
        toolbar.addView(view, params);
    }

    /**
     * 初始化右边布局
     */
    public void initTabLeft(View view, View.OnClickListener onClickListener) {
        ActionBar actionBar = compatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);//显示toolbar左上角的返回按钮。
        }
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START | Gravity.CENTER | Gravity.LEFT);
        view.setOnClickListener(onClickListener);
        toolbar.addView(view, params);
    }


    /**
     * 初始化自定义布局
     */
    public void initCustomView(View view) {
        ActionBar actionBar = compatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);//显示toolbar左上角的返回按钮。
        }
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL | Gravity.END);
        toolbar.addView(view, params);
    }

    /**
     * 初始化标题居中显示
     *
     * @param text        文字
     * @param textsize    文字大小
     * @param textColorId 文字颜色
     */
    protected TextView getTextView(CharSequence text, int textsize, int textColorId) {
        TextView textView = new TextView(mActivity);
        textView.setText(text);
        textView.setTextSize(textsize);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(ContextCompat.getColor(mActivity, textColorId));
        return textView;
    }

    /**
     * 默认文字大小16sp,颜色红色
     */
    protected TextView getTextView(CharSequence text) {
        return getTextView(text, 17, R.color.color_222222);
    }

    /**
     * 创建按钮
     */
    protected TextView getTextView(CharSequence text, int colorId) {
        return getTextView(text, 15, colorId, 0, 40);
    }

    /**
     * 创建右按钮
     */
    protected TextView getTextView(int resId) {
        return getTextView(null, 15, R.color.color_222222, resId, 40);
    }


    /**
     * 初始化标题居中显示
     *
     * @param text        文字
     * @param textsize    文字大小
     * @param textColorId 文字颜色
     */
    private TextView getTextView(CharSequence text, int textsize, int textColorId, int resId, int padding) {
        TextView textView = new TextView(mActivity);
        textView.setText(text);
        if (resId > 0) {
            textView.setText(null);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
        textView.setTextSize(textsize);
        textView.setPadding(0, 0, padding, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(mActivity, textColorId));
        return textView;
    }

}
