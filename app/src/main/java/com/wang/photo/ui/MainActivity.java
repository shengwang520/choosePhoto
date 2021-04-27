package com.wang.photo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sheng.wang.media.FileCompat;
import com.sheng.wang.media.impl.CallBack;
import com.sheng.wang.media.model.FileFolder;

import java.util.List;

/**
 * 使用实例
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 获取图片
     */
    private void loadImages() {
        FileCompat fileCompat = new FileCompat(this, new CallBack() {
            @Override
            public void onSuccess(List<FileFolder> results) {

            }

            @Override
            public void onError() {

            }
        });
        fileCompat.loadImages();
    }

    /**
     * 获取视频
     */
    private void loadVideo() {
        FileCompat fileCompat = new FileCompat(this, new CallBack() {
            @Override
            public void onSuccess(List<FileFolder> results) {

            }

            @Override
            public void onError() {

            }
        });
        fileCompat.loadVideos();
    }
}
