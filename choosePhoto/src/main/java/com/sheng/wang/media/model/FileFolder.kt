package com.sheng.wang.media.model

import android.text.TextUtils
import java.util.*

/**
 * 图片路径
 */
class FileFolder {
    /**
     * 文件夹下面所有的图片
     */
    var images: MutableList<FileBean> = ArrayList()

    /**
     * 第一张图片
     */
    var firstFileBean: FileBean? = null

    /**
     * 文件夹的名称
     */
    var name: String? = null

    /**
     * 设置已经选中的数据
     */
    fun getImages(chooseData: MutableList<FileBean>?): MutableList<FileBean> {
        if (chooseData == null || chooseData.isEmpty()) return images
        for (choose in chooseData) {
            for (img in images) {
                if (TextUtils.equals(choose.filePathQ, img.filePathQ)) {
                    img.isChoose = true
                }
            }
        }
        return images
    }

}