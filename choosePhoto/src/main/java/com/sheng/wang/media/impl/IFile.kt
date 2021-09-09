package com.sheng.wang.media.impl

/**
 * 文件接口
 */
interface IFile {
    /**
     * 文件类型
     */
    interface Type {
        companion object {
            const val FILE_TYPE_IMAGE = "image" //图片
            const val FILE_TYPE_VIDEO = "video" //视频
            const val FILE_TYPE_VOICE = "voice" //音频
            const val FILE_TYPE_ADD = "add" //加号，添加图片
            const val FILE_TYPE_ADD_VIDEO = "add_video" //加号，添加视频
        }
    }

    /**
     * 文件描述
     */
    interface Des {
        companion object {
            const val FILE_DES_PHONE_VIDEO = "user_upload" //手机自带的视频
            const val FILE_DES_APP_RECORD_VIDEO = "user_shooting" //使用app软件录制的视频
            const val FILE_DES_APP_RECORD_VOICE = "user_voice" //使用app软件录制的音频
        }
    }
}