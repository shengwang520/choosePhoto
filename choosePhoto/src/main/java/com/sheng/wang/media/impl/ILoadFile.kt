package com.sheng.wang.media.impl

interface ILoadFile {
    /**
     * 1.加载图片
     * @param onLoadFileFolderListener 获取数据成功回调，包含父文件信息
     * @param onLoadErrorListener 获取数据失败
     */
    fun loadImages(
        onLoadFileFolderListener: CallBack.OnLoadFileFolderListener?,
        onLoadErrorListener: CallBack.OnLoadErrorListener?
    )

    /**
     * 2.加载视频
     * @param onLoadFileFolderListener 获取数据成功回调，包含父文件信息
     * @param onLoadErrorListener 获取数据失败
     */
    fun loadVideos(
        onLoadFileFolderListener: CallBack.OnLoadFileFolderListener?,
        onLoadErrorListener: CallBack.OnLoadErrorListener?
    )

    /**
     * 3.加载图片和视频数据（按照创建时间倒叙排序）
     * @param onLoadFileListener 获取数据成功回调，不包含父文件信息
     * @param onLoadFileListener 获取数据失败
     */
    fun loadImageAndVideos(
        onLoadFileListener: CallBack.OnLoadFileListener?,
        onLoadErrorListener: CallBack.OnLoadErrorListener?
    )
}