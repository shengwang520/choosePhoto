package com.common.app.common.base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


/**
 * activity上层基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String RESULT_DATA = "result_intent_data";///数据返回key

    public static final String DATA_KEY = "intent_data_key";//基本请求参数进入key
    public static final String DATA_KEY_TWO = "intent_data_key_two";//基本请求参数进入key2
    public static final String DATA_KEY_THREE = "intent_data_key_three";//基本请求参数进入key3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public BaseActivity getActivity() {
        return this;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomeBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * toolbar,返回键
     */
    protected void onHomeBack() {
        finish();
    }


}
