package com.wang.photo.common.widget.tab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.core.content.ContextCompat;

import com.common.app.R;
import com.google.android.material.tabs.TabLayout;


/**
 * tab按钮管理
 */
public class TabMenuManger {
    private Context context;
    private TabLayout tabLayout;

    public TabMenuManger(Context context, TabLayout tabLayout) {
        this.context = context;
        this.tabLayout = tabLayout;
    }

    /**
     * 创建自定义tabItem
     */
    public TabMenuHolder createView(String text, int resId) {
        return createView(text, resId, 0);
    }

    /**
     * 创建自定义tabItem
     */
    public TabMenuHolder createView(String text, int resId, int colorId) {
        View view = LayoutInflater.from(context).inflate(R.layout.x_view_main_tab_text, tabLayout, false);
        TabMenuHolder tabMenuHolder = new TabMenuHolder(view);
        tabMenuHolder.setText(text);
        tabMenuHolder.setTopIcon(resId);
        tabMenuHolder.setTextColor(colorId);
        return tabMenuHolder;
    }

    /**
     * 创建选择按钮
     *
     * @param ctx  上下文
     * @param text 按钮显示的文字
     * @return 可选中的按钮
     */
    public static AppCompatCheckedTextView createButton(Context ctx, CharSequence text) {
        return createButton(ctx, text, 0);
    }

    /**
     * 创建选择按钮
     *
     * @param ctx  上下文
     * @param text 按钮显示的文字
     * @return 可选中的按钮
     */
    public static AppCompatCheckedTextView createButton(Context ctx, CharSequence text, int colorResId) {
        AppCompatCheckedTextView button = new AppCompatCheckedTextView(ctx);
        button.setCheckMarkDrawable(new ColorDrawable(Color.TRANSPARENT));
        button.setText(text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        button.setGravity(Gravity.CENTER);
        button.setPadding(10, 4, 10, 4);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setTextColor(ContextCompat.getColorStateList(ctx, colorResId));
        return button;
    }

}
