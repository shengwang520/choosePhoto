package com.common.app.common.dexterpermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.karumi.dexter.Dexter;

/**
 * Created by JamesP949 on 2016/10/13.
 * android 6.0+ 权限管理工具
 */

public class DexterPermissionsUtil {

    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;//相机权限
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;//录音权限

    //录制视频权限 ，聊天权限
    public static final String[] RECORD_VIDEO_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    //拨打视频电话权限
    public static final String[] CALL_VIDEO_PERMISSION = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    //录制声音
    public static final String[] RECORD_VOICE_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    //文件读写权限
    public static final String[] RECORD_WRITE_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,};


    /**
     * 请求单个权限
     */
    public static void requestPermission(Context context, String permission, final CallBack callBack) {
        if (context instanceof Activity) {
            Dexter.withActivity((Activity) context)
                    .withPermission(permission)
                    .withListener(new SamplePermissionListener(callBack))
                    .check();
        }

    }

    /**
     * 请求某个功能需要的多个权限
     *
     * @param function 功能名称
     */
    public static void requestPermissions(Context context,
                                          String function,
                                          CallBack callBack,
                                          String... permissions) {
        if (context instanceof Activity) {
            Dexter.withActivity((Activity) context)
                    .withPermissions(permissions)
                    .withListener(new SampleMultiplePermissionListener(callBack))
                    .check();
        }
    }

    /**
     * 请求某个功能需要的多个权限
     */
    public static void requestPermissions(Context context,
                                          CallBack callBack,
                                          String... permissions) {
        if (context instanceof Activity) {
            Dexter.withActivity((Activity) context)
                    .withPermissions(permissions)
                    .withListener(new SampleMultiplePermissionListener(callBack))
                    .check();
        }
    }


    /**
     * 检查是否有权限
     */
    public static boolean checkCameraPermission(Context activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 取设置开启权限
     */
    public static void go2OpenPermission(Context context) {
        Intent myAppSettings = new Intent(Settings
                .ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myAppSettings);
    }


    public interface CallBack {
        /**
         * 同意用户申请的权限
         */
        void showPermissionGranted(String permission);

        /**
         * 拒绝用户申请的权限
         */
        void showPermissionDenied(String permission, boolean permanentlyDenied);
    }
}
