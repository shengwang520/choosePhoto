package com.wang.photo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wang.media.base.BaseActivity;
import com.wang.photo.R;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_activity_main);
    }

}
