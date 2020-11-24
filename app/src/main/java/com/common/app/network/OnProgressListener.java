package com.common.app.network;

public interface OnProgressListener {
    /**
     * 下载进度
     *
     * @param total    总大小
     * @param progress 当前进度
     */
    void onLoading(long total, long progress);
}
