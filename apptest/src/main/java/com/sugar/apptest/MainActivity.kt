package com.sugar.apptest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.sheng.wang.media.FileCompat
import com.sheng.wang.media.impl.CallBack
import com.sheng.wang.media.model.FileBean
import com.sheng.wang.media.model.FileFolder
import com.sugar.apptest.dexterpermission.DexterPermissionsUtil
import com.sugar.apptest.dexterpermission.DexterPermissionsUtil.RECORD_WRITE_PERMISSION

/**
 * 使用实例
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImages()
    }

    /**
     * 获取图片
     */
    private fun loadImages() {
        DexterPermissionsUtil.requestPermissions(
            this,
            object : DexterPermissionsUtil.CallBack {
                override fun showPermissionGranted(permission: String?) {
                    loadImageAndVideo()
                }

                override fun showPermissionDenied(permission: String?, permanentlyDenied: Boolean) {
                }

            }, *RECORD_WRITE_PERMISSION
        )

    }

    private fun loadImage() {
        val fileCompat = FileCompat(this)
        fileCompat.loadImages(object : CallBack.OnLoadFileFolderListener {
            override fun onSuccess(results: List<FileFolder>) {
                Logger.d("获取到的图片数量：" + results.size)
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }

    /**
     * 获取视频
     */
    private fun loadVideo() {
        val fileCompat = FileCompat(this)
        fileCompat.loadVideos(object : CallBack.OnLoadFileFolderListener {
            override fun onSuccess(results: List<FileFolder>) {
                Logger.d("获取到的视频数量：" + results.size)
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }

    /**
     * 获取视频
     */
    private fun loadImageAndVideo() {
        val fileCompat = FileCompat(this)
        fileCompat.loadImageAndVideos(object : CallBack.OnLoadFileListener {
            override fun onSuccess(results: List<FileBean>) {
                Logger.d("获取到的图片视频数量：" + results.size)
                for (item in results) {
                    Logger.d("load data path:" + item.filePathQ)
                }
            }
        }, object : CallBack.OnLoadErrorListener {
            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }
}