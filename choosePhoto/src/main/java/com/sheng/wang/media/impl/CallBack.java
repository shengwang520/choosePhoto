package com.sheng.wang.media.impl;


import com.sheng.wang.media.model.FileFolder;

import java.util.List;

/**
 * 回调
 */
public interface CallBack {

    /**
     * 成功
     *
     * @param results 文件数据
     */
    void onSuccess(List<FileFolder> results);

    /**
     * 错误
     */
    void onError();
}
