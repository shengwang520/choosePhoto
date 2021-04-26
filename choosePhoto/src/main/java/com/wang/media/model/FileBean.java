package com.wang.media.model;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.wang.media.impl.IFile;
import com.wang.media.utils.FileUtils;

import java.io.File;

/**
 * 手机上的图片实体
 */
public class FileBean implements Parcelable, IFile {
    /**
     * 文件本地路径
     */
    public String filePath;
    /**
     * 文件Uri 路径 android 10以上使用
     */
    public String fileUri;
    /**
     * 上传到服务器的路径
     */
    public String fileUrl;
    /**
     * 视频时长
     */
    public long videoTime;
    /**
     * 文件创建时间
     */
    public long createTime;

    /**
     * 视频方向
     */
    public String rotation = "0";

    /**
     * 图片宽
     */
    public int width;
    /**
     * 图片高
     */
    public int height;

    /**
     * 文件类型{@link IFile.Type}
     */
    public String type = Type.FILE_TYPE_IMAGE;

    /**
     * 文件描述{@link IFile.Des}
     */
    public String des = Des.FILE_DES_PHONE_VIDEO;

    /**
     * 是否选中，可用于图片多选
     */
    public boolean isChoose;
    /**
     * 选中的位置
     */
    public int choosePosition;
    /**
     * 是否是相机
     */
    public boolean isCamera;

    public FileBean() {
    }

    public FileBean(boolean isCamera) {
        this.isCamera = isCamera;
    }

    /**
     * 默认为图片类型
     */
    public FileBean(String imagePath) {
        this(imagePath, Type.FILE_TYPE_IMAGE);
    }

    public FileBean(String imagePath, String type) {
        this.filePath = imagePath;
        this.type = type;
    }

    public FileBean(String imagePath, String type, String des) {
        this.filePath = imagePath;
        this.type = type;
        this.des = des;
    }

    protected FileBean(Parcel in) {
        filePath = in.readString();
        fileUri = in.readString();
        fileUrl = in.readString();
        videoTime = in.readLong();
        createTime = in.readLong();
        rotation = in.readString();
        width = in.readInt();
        height = in.readInt();
        type = in.readString();
        des = in.readString();
        isChoose = in.readByte() != 0;
        choosePosition = in.readInt();
        isCamera = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filePath);
        dest.writeString(fileUri);
        dest.writeString(fileUrl);
        dest.writeLong(videoTime);
        dest.writeLong(createTime);
        dest.writeString(rotation);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(type);
        dest.writeString(des);
        dest.writeByte((byte) (isChoose ? 1 : 0));
        dest.writeInt(choosePosition);
        dest.writeByte((byte) (isCamera ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel in) {
            return new FileBean(in);
        }

        @Override
        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };

    /**
     * 获取父类文件名称
     */
    public String getFileParentName() {
        String[] str = filePath.split("/");
        if (str.length > 1) {
            return str[str.length - 2];
        } else {
            return "";
        }
    }

    /**
     * 获取安卓Q路径
     */
    public String getFilePathQ() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() &&
                !TextUtils.isEmpty(fileUri)) {
            return fileUri;
        } else {
            return filePath;
        }
    }

    /**
     * 获取要上传的文件路径
     */
    public String getUploadFilePath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() && !TextUtils.isEmpty(fileUri)) {
            return FileUtils.getUri2CachePath(context, filePath, fileUri);
        } else {
            return filePath;
        }
    }

    /**
     * 判断文件是否存在
     */
    public boolean isExists() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() && !TextUtils.isEmpty(fileUri)) {
            return true;
        } else {
            File file = new File(filePath);
            return file.exists();
        }
    }

    /**
     * 是否是视频
     */
    public boolean isVideo() {
        return TextUtils.equals(type, IFile.Type.FILE_TYPE_VIDEO);
    }


    @Override
    public boolean equals(Object o) {//重写判断是否是同一个数据的标准
        String oPath = "";
        String oUrl = "";
        if (o instanceof String) {
            oPath = (String) o;
        } else if (o instanceof FileBean) {
            FileBean oPicture = (FileBean) o;
            oPath = oPicture.filePath;
            oUrl = oPicture.fileUrl;
        }
        if (!TextUtils.isEmpty(oUrl)) {
            return TextUtils.equals(oUrl, fileUrl);
        } else {
            return TextUtils.equals(oPath, filePath);
        }
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "imagePath='" + filePath + '\'' +
                ", fileUri='" + fileUri + '\'' +
                ", imageUrl='" + fileUrl + '\'' +
                ", videoTime=" + videoTime +
                ", createTime=" + createTime +
                ", rotation='" + rotation + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", type='" + type + '\'' +
                ", des='" + des + '\'' +
                ", isChoose=" + isChoose +
                ", choosePosition=" + choosePosition +
                ", isCamera=" + isCamera +
                '}';
    }
}
