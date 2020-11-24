package com.common.app.common.widget.tab;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.core.content.ContextCompat;

import com.common.app.R;
import com.common.app.common.base.BaseViewItemFinder;

/**
 * 底部有红点的导航
 */
public class TabMenuHolder extends BaseViewItemFinder {
    public AppCompatCheckedTextView tvName;
    public TextView tvRed;

    TabMenuHolder(View view) {
        super(view);
        tvName = findViewById(R.id.tv_name);
        tvRed = findViewById(R.id.tv_red);
    }

    void setText(String text) {
        tvName.setText(text);
    }

    void setTopIcon(int resId) {
        tvName.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
    }

    void setTextColor(int color) {
        tvName.setTextColor(ContextCompat.getColorStateList(getContext(), color));
    }
}
