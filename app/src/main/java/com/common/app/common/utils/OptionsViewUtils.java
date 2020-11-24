package com.common.app.common.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.common.app.R;

/**
 * 选择菜单通用设置
 */
public class OptionsViewUtils {

    /**
     * 获取通用配置选择器
     */
    public static OptionsPickerBuilder getOptionsPickerBuilder(Context context, String title, OnOptionsSelectListener onOptionsSelectListener) {
        return new OptionsPickerBuilder(context, onOptionsSelectListener)
                .setTitleText(title)
                .setTitleColor(ContextCompat.getColor(context, R.color.color_white))
                .setTitleBgColor(ContextCompat.getColor(context, R.color.color_040717))
                .setCancelColor(ContextCompat.getColor(context, R.color.color_white))
                .setSubmitColor(ContextCompat.getColor(context, R.color.color_white))
                .setTextColorCenter(ContextCompat.getColor(context, R.color.color_222222))
                .setOutSideCancelable(true)
                .setContentTextSize(17)
                .setLineSpacingMultiplier(2)
                .setTitleSize(17);
    }

    /**
     * 获取时间通用配置器
     */
    public static TimePickerBuilder getTimePickerBuilder(Context context, String title, OnTimeSelectListener onTimeSelectListener) {
        return new TimePickerBuilder(context, onTimeSelectListener)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setContentTextSize(17)//滚轮文字大小
                .setTitleSize(17)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ContextCompat.getColor(context, R.color.color_white))
                .setTitleBgColor(ContextCompat.getColor(context, R.color.color_040717))
                .setCancelColor(ContextCompat.getColor(context, R.color.color_white))
                .setSubmitColor(ContextCompat.getColor(context, R.color.color_white))
                .setTextColorCenter(ContextCompat.getColor(context, R.color.color_222222))
                .setLabel(context.getString(R.string.unit_year), context.getString(R.string.unit_month), context.getString(R.string.unit_day), context.getString(R.string.unit_hours), context.getString(R.string.unit_mins), context.getString(R.string.unit_seconds))//默认设置为年月日时分秒
                .isCenterLabel(false); //是否只显示中间选中项的label文字，false则每项item全部都带有label。;//是否显示为对话框样式
    }
}
