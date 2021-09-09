package com.wang.photo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sheng.wang.media.FileCompat
import com.sheng.wang.media.impl.CallBack
import com.sheng.wang.media.model.FileFolder

/**
 * 使用实例
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 获取图片
     */
    private fun loadImages() {
        val fileCompat = FileCompat(this, object : CallBack {
            override fun onSuccess(results: List<FileFolder>) {}
            override fun onError() {}
        })
        fileCompat.loadImages()
    }

    /**
     * 获取视频
     */
    private fun loadVideo() {
        val fileCompat = FileCompat(this, object : CallBack {
            override fun onSuccess(results: List<FileFolder>) {}
            override fun onError() {}
        })
        fileCompat.loadVideos()
    }
}