package com.common.app.ui;

import android.os.Bundle;

import com.common.app.common.base.BaseActivity;
import com.common.app.common.utils.AppCompat;


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
