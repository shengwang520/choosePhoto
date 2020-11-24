package com.common.app.common.base;


import androidx.multidex.MultiDexApplication;

/**
 * application 基类
 */
public abstract class BaseApplication extends MultiDexApplication {
    static BaseApplication _instance;

    public static BaseApplication instance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

}
