package com.wang.choosephoto.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * textview显示控制
 * Created by 圣王 on 2015/6/12 0012.
 */
public class TextViewUtils {

    /**
     * 设置文字反显
     */
    public static SpannableStringBuilder setSpecialTextColor(@NonNull String text, @NonNull String specialText, int color) {

        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        if (TextUtils.isEmpty(specialText)) return spannableString;
        int start = text.indexOf(specialText);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, start, start + specialText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 获取不同颜色文字
     */
    public static SpannableString getSpannableString(String text, int color) {
        final SpannableString string = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        string.setSpan(foregroundColorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置不同的颜色
     */
    public static void setSpecialTextColor(TextView textView, String content, int color) {
        String text = textView.getText().toString();
        textView.setText(setSpecialTextColor(text, content, color));
    }

    /**
     * 设置不同的颜色
     */
    public static void setSpecialTextColor(TextView textView, String content, int color, boolean isBold) {
        String text = textView.getText().toString();
        SpannableStringBuilder stringBuilder = setSpecialTextColor(text, content, color);
        if (isBold) {
            int start = text.indexOf(content);
            StyleSpan span = new StyleSpan(Typeface.BOLD);
            stringBuilder.setSpan(span, start, start + content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(stringBuilder);
    }

}
