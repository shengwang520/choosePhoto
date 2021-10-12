package com.sheng.wang.media.impl

import com.sheng.wang.media.model.FileBean
import com.sheng.wang.media.model.FileFolder

/**
 * 回调
 */
interface CallBack {

    /**
     * 加载文件成功回调，包含父文件数据
     */
    interface OnLoadFileFolderListener {
        /**
         * 成功
         *
         * @param results 文件数据
         */
        fun onSuccess(results: List<FileFolder>)
    }

    /**
     * 加载成功回调，没有父文件数据
     */
    interface OnLoadFileListener {
        /**
         * 成功
         * @param results 文件数据
         */
        fun onSuccess(results: List<FileBean>)
    }


    /**
     * 加载失败回调
     */
    interface OnLoadErrorListener {
        /**
         * 错误
         */
        fun onError()
    }

}