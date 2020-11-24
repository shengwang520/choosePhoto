package com.common.app.network.user;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 用户信息类，本地保存
 */
public class User {
    private static final String DATA = "data_user";

    private volatile static User instance;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (null == instance) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    /**
     * 获取数据存储
     */
    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    /**
     * 退出登录
     */
    public void loginOut(Context context) {

    }

}
