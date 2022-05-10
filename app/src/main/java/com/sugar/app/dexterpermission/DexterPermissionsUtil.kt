package com.sugar.app.dexterpermission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter

/**
 * Created by JamesP949 on 2016/10/13.
 * android 6.0+ 权限管理工具
 */
object DexterPermissionsUtil {
    const val PERMISSION_CAMERA = Manifest.permission.CAMERA //相机权限
    const val PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO //录音权限

    //录制视频权限 ，聊天权限
    val RECORD_VIDEO_PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    //拨打视频电话权限
    val CALL_VIDEO_PERMISSION = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    //录制声音
    val RECORD_VOICE_PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    //文件读写权限
    val RECORD_WRITE_PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    //文件读写权限
    val LOCATION_PERMISSION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    /**
     * 请求单个权限
     */
    @JvmStatic
    fun requestPermission(context: Context?, permission: String?, callBack: CallBack?) {
        if (context is Activity) {
            Dexter.withActivity(context as Activity?)
                .withPermission(permission)
                .withListener(SamplePermissionListener(callBack!!))
                .check()
        }
    }

    /**
     * 请求某个功能需要的多个权限
     */
    @JvmStatic
    fun requestPermissions(
        context: Context?,
        callBack: CallBack?,
        vararg permissions: String?
    ) {
        if (context is Activity) {
            Dexter.withActivity(context as Activity?)
                .withPermissions(*permissions)
                .withListener(SampleMultiplePermissionListener(callBack!!))
                .check()
        }
    }

    /**
     * 检查是否有权限
     */
    @JvmStatic
    fun checkCameraPermission(activity: Context?, permission: String?): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 取设置开启权限
     */
    fun go2OpenPermission(context: Context) {
        val myAppSettings = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + context.packageName)
        )
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(myAppSettings)
    }

    interface CallBack {
        /**
         * 同意用户申请的权限
         */
        fun showPermissionGranted(permission: String?)

        /**
         * 拒绝用户申请的权限
         */
        fun showPermissionDenied(permission: String?, permanentlyDenied: Boolean)
    }
}