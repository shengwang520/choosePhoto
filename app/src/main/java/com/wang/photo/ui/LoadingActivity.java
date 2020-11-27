package com.wang.photo.ui;

import android.os.Bundle;

import com.wang.photo.common.base.BaseActivity;
import com.wang.photo.common.utils.AppCompat;


/**
 * 启动页
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompat.startActivity(this, MainActivity.newIntent(this));
        finish();
    }
}
