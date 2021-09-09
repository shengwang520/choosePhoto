package com.sheng.wang.media.impl

import com.sheng.wang.media.model.FileFolder

/**
 * 回调
 */
interface CallBack {
    /**
     * 成功
     *
     * @param results 文件数据
     */
    fun onSuccess(results: List<FileFolder>)

    /**
     * 错误
     */
    fun onError()
}