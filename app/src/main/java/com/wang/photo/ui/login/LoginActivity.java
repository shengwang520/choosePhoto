package com.wang.photo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wang.media.base.BaseActivity;
import com.wang.photo.R;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_activity_login);
    }
}
