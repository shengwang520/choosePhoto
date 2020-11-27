package com.wang.choosephoto.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * app相关状态
 * Created by wangshengqiang on 2016/10/25.
 */

public class AppUtils {

    /**
     * 需要权限:android.permission.GET_TASKS
     * app是否没在前台运行
     */
    public static boolean isAppNotLive(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        assert am != null;
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Logger.d("app没在前台运行");
                return true;
            }
        }
        Logger.d("app在前台运行");
        return false;
    }

}
