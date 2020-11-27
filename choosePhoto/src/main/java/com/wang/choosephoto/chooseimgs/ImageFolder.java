package com.wang.choosephoto.chooseimgs;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片路径
 */
public class ImageFolder {
    public List<ImageBean> images;
    /**
     * 图片的文件夹路径
     */
    private String dir;
    /**
     * 第一张图片的路径
     */
    private String firstImagePath;
    /**
     * 文件夹的名称
     */
    private String name;

    public ImageFolder() {
        images = new ArrayList<>();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf).replace("/", "");
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    /**
     * 设置已经选中的数据
     */
    public List<ImageBean> getImages(List<ImageBean> chooseData) {
        if (chooseData == null || chooseData.isEmpty()) return images;
        for (ImageBean choose : chooseData) {
            for (ImageBean img : images) {
                if (TextUtils.equals(choose.getImgpath(), img.getImgpath())) {
                    img.setIsChoose(true);
                }
            }
        }
        return images;
    }
}
