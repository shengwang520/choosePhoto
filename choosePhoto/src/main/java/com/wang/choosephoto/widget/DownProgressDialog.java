package com.wang.choosephoto.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wang.choosephoto.R;
import com.wang.choosephoto.utils.GlideUtil;


/**
 * 自定义进度
 * Created by wang on 2016/1/27.
 */
public class DownProgressDialog extends Dialog {
    private TextView tv_message;
    private ProgressBar progressBar;

    public DownProgressDialog(Context context) {
        super(context);
    }

    public DownProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_view_down_progress_dialog);
        tv_message = findViewById(R.id.x_progress_tv_message);
        progressBar = findViewById(R.id.x_progress_bar);
    }

    public void setMax(int max) {
        progressBar.setMax(max);
    }

    public void setProgress(final int progress) {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress);
            }
        });
    }

    /**
     * 设置当前下载进度
     *
     * @param title    总大小
     * @param progress 进度
     */
    public void setLoading(final long title, final long progress) {
        tv_message.post(new Runnable() {
            @Override
            public void run() {
                String titleSize = GlideUtil.getFormatSize(title);
                String progressSize = GlideUtil.getFormatSize(progress);
                tv_message.setText(String.format("%s/%s", progressSize, titleSize));
            }
        });

    }

}
