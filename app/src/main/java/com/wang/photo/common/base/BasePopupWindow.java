package com.wang.photo.common.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.common.app.R;
import com.wang.photo.common.utils.MetricsUtils;
import com.wang.photo.common.utils.UiCompat;


/**
 * 基类popupwindow
 * Created by wang on 2016/6/24.
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected Context context;
    protected LayoutInflater inflater;
    protected View mview;

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mview = onCreateView(inflater);
        init();
        onViewCreate(mview);
    }

    /**
     * 创建布局
     *
     */
    protected abstract View onCreateView(LayoutInflater inflater);

    /**
     * 布局创建后
     *
     * @param view
     */
    protected abstract void onViewCreate(View view);

    private void init() {
        this.setContentView(mview);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setWidth(MetricsUtils.getWidth(context));
        this.setHeight(MetricsUtils.getHeight(context));
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable dw = new ColorDrawable(0x66000000);
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
        this.setClippingEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            this.setAttachedInDecor(true);
        }

        mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 显示在底部
     *
     * @param parent
     */
    public void show(View parent) {
        this.showAtLocation(parent, Gravity.BOTTOM, 0, UiCompat.getNavigationBarHeight(context));
    }

}
