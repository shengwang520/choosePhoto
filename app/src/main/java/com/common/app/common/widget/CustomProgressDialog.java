package com.common.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.common.app.R;


/**
 * 自定义进度
 * Created by wang on 2016/1/27.
 */
public class CustomProgressDialog extends Dialog {
    private String message;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_view_progress_dialog_custom);
        TextView tv_message = findViewById(R.id.x_progress_tv_message);
        tv_message.setText(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
