package com.wang.media.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片路径
 */
public class FileFolder {
    /**
     * 文件夹下面所有的图片
     */
    public List<FileBean> images;
    /**
     * 第一张图片
     */
    public FileBean firstFileBean;
    /**
     * 文件夹的名称
     */
    public String name;

    public FileFolder() {
        images = new ArrayList<>();
    }

    /**
     * 设置已经选中的数据
     */
    public List<FileBean> getImages(List<FileBean> chooseData) {
        if (chooseData == null || chooseData.isEmpty()) return images;
        for (FileBean choose : chooseData) {
            for (FileBean img : images) {
                if (TextUtils.equals(choose.getFilePathQ(), img.getFilePathQ())) {
                    img.isChoose = true;
                }
            }
        }
        return images;
    }
}
