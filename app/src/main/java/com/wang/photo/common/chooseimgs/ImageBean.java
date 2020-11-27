package com.wang.photo.common.chooseimgs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手机上的图片实体
 * Created by wang on 2015/12/16.
 */
public class ImageBean implements Parcelable {
    public static final String FILE_TYPE_IMAGE = "image";//图片
    public static final String FILE_TYPE_VIDEO = "video";//视频
    public static final String FILE_TYPE_ADD = "add";//加号，添加图片
    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };
    private boolean isChoose;
    private boolean isCamera;//是否是相机
    private String imagePath;

    public ImageBean(boolean isCamera) {
        this.isCamera = isCamera;
    }

    private String type = FILE_TYPE_IMAGE;//文件类型

    /**
     * 默认为图片类型
     */
    public ImageBean(String imgpath) {
        this(imgpath, FILE_TYPE_IMAGE);
    }

    public ImageBean() {
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public ImageBean(String imgpath, String type) {
        this.imagePath = imgpath;
        this.type = type;
    }

    protected ImageBean(Parcel in) {
        this.imagePath = in.readString();
        this.isChoose = in.readByte() != 0;
        this.isCamera = in.readByte() != 0;
        this.type = in.readString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public String getImgpath() {
        return imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setImgpath(String imgpath) {
        this.imagePath = imgpath;
    }

    @Override
    public boolean equals(Object o) {//重写判断是否是同一个数据的标准
        String oPath = "";
        if (o instanceof String) {
            oPath = (String) o;
        } else if (o instanceof ImageBean) {
            ImageBean oPicture = (ImageBean) o;
            oPath = oPicture.imagePath;
        }
        return oPath.equals(imagePath);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCamera ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
    }
}
