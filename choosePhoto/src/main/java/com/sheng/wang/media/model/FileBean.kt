package com.sheng.wang.media.model

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.sheng.wang.media.impl.IFile
import com.sheng.wang.media.utils.FileUtils
import java.io.File

/**
 * 手机上的图片实体
 */
class FileBean @JvmOverloads constructor(
    isCamera: Boolean = false,
    filePath: String? = "",
    type: String? = IFile.Type.FILE_TYPE_IMAGE,
    des: String? = "") :
    IFile, Parcelable {
    /**
     * 文件本地路径
     */
    var filePath: String? = null

    /**
     * 文件Uri 路径 android 10以上使用
     */
    var fileUri: String? = null

    /**
     * 上传到服务器的路径
     */
    var fileUrl: String? = null

    /**
     * 视频时长
     */
    var videoTime: Long = 0

    /**
     * 文件创建时间
     */
    var createTime: Long = 0

    /**
     * 视频方向
     */
    var rotation: String? = "0"

    /**
     * 图片宽
     */
    var width = 0

    /**
     * 图片高
     */
    var height = 0

    /**
     * 文件类型[IFile.Type]
     */
    var type: String? = IFile.Type.FILE_TYPE_IMAGE

    /**
     * 文件描述[IFile.Des]
     */
    var des: String? = IFile.Des.FILE_DES_PHONE_VIDEO

    /**
     * 是否选中，可用于图片多选
     */
    var isChoose = false

    /**
     * 选中的位置
     */
    var choosePosition = 0

    /**
     * 是否是相机
     */
    var isCamera = false

    /**
     * 获取父类文件名称
     */
    val fileParentName: String
        get() {
            val str = filePath!!.split("/").toTypedArray()
            return if (str.size > 1) {
                str[str.size - 2]
            } else {
                ""
            }
        }

    /**
     * 获取安卓Q路径
     */
    val filePathQ: String?
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() &&
            !TextUtils.isEmpty(fileUri)) {
            fileUri
        } else {
            filePath
        }

    /**
     * 获取要上传的文件路径
     */
    fun getUploadFilePath(context: Context?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() && !TextUtils.isEmpty(fileUri)) {
            FileUtils.getUri2CachePath(context, filePath, fileUri)
        } else {
            filePath
        }
    }

    /**
     * 判断文件是否存在
     */
    val isExists: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy() && !TextUtils.isEmpty(fileUri)) {
            true
        } else {
            val file = File(filePath!!)
            file.exists()
        }

    /**
     * 是否是视频
     */
    val isVideo: Boolean
        get() = TextUtils.equals(type, IFile.Type.FILE_TYPE_VIDEO)

    constructor(parcel: Parcel) : this() {
        filePath = parcel.readString()
        fileUri = parcel.readString()
        fileUrl = parcel.readString()
        videoTime = parcel.readLong()
        createTime = parcel.readLong()
        rotation = parcel.readString()
        width = parcel.readInt()
        height = parcel.readInt()
        type = parcel.readString()
        des = parcel.readString()
        isChoose = parcel.readByte() != 0.toByte()
        choosePosition = parcel.readInt()
        isCamera = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean { //重写判断是否是同一个数据的标准
        var oPath: String? = ""
        var oUrl = ""
        if (other is String) {
            oPath = other
        } else if (other is FileBean) {
            oPath = other.filePath
            oUrl = other.fileUrl!!
        }
        return if (!TextUtils.isEmpty(oUrl)) {
            TextUtils.equals(oUrl, fileUrl)
        } else {
            TextUtils.equals(oPath, filePath)
        }
    }

    override fun toString(): String {
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
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(filePath)
        parcel.writeString(fileUri)
        parcel.writeString(fileUrl)
        parcel.writeLong(videoTime)
        parcel.writeLong(createTime)
        parcel.writeString(rotation)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(type)
        parcel.writeString(des)
        parcel.writeByte(if (isChoose) 1 else 0)
        parcel.writeInt(choosePosition)
        parcel.writeByte(if (isCamera) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun hashCode(): Int {
        var result = filePath?.hashCode() ?: 0
        result = 31 * result + (fileUri?.hashCode() ?: 0)
        result = 31 * result + (fileUrl?.hashCode() ?: 0)
        result = 31 * result + videoTime.hashCode()
        result = 31 * result + createTime.hashCode()
        result = 31 * result + (rotation?.hashCode() ?: 0)
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (des?.hashCode() ?: 0)
        result = 31 * result + isChoose.hashCode()
        result = 31 * result + choosePosition
        result = 31 * result + isCamera.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<FileBean> {
        override fun createFromParcel(parcel: Parcel): FileBean {
            return FileBean(parcel)
        }

        override fun newArray(size: Int): Array<FileBean?> {
            return arrayOfNulls(size)
        }
    }

}