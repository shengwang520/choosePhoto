package com.sheng.wang.media.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import java.io.*

/**
 * 本地文件管理
 */
object FileUtils {
    private const val CACHE_DATA = "/CacheData" //缓存目录
    private const val CACHE_GIFT_ANIM = "/GiftAnim" //礼物动画缓存目录
    private const val CACHE_CHAT_GIFT_IMG = "ChatCacheGImg" //聊天礼物图片缓存目录

    /**
     * 获取数据缓存路径
     */
    fun getCacheData(context: Context): String {
        return context.filesDir.absolutePath + CACHE_DATA
    }

    /**
     * 获取数据缓存路径
     */
    fun getCacheGiftAnim(context: Context): String {
        return context.filesDir.absolutePath + CACHE_GIFT_ANIM
    }

    /**
     * 获取聊天礼物缓存路径
     */
    fun getChatCacheImage(context: Context): String {
        val path = context.filesDir.absolutePath + CACHE_CHAT_GIFT_IMG
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return path
    }

    //普通安装
    fun installNormal(context: Context, apkPath: String) {
        Logger.d("apk path:$apkPath")
        val intent = Intent(Intent.ACTION_VIEW)
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val file = File(apkPath)
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            val apkUri =
                FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(File(apkPath)),
                "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }

    /**
     * 创建文件
     *
     * @param type   文件缓存类型[Environment]
     * @param suffix 文件后缀
     */
    @Throws(IOException::class)
    fun createFile(context: Context, type: String?, suffix: String?): File {
        val storageDir = context.getExternalFilesDir(type)
        return File.createTempFile(System.currentTimeMillis().toString(), suffix, storageDir)
    }

    /**
     * 将下载的数据写入本地
     *
     * @param body 下载的数据
     * @param path 需要写入的本地文件路径
     */
    fun writeResponseBodyToDisk(body: ResponseBody, path: String?): Boolean {
        if (path.isNullOrEmpty()) return false
        return try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val futureStudioIconFile = File(path)
                val fileReader = ByteArray(1024)
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                var read: Int
                while (inputStream.read(fileReader).also { read = it } != -1) {
                    outputStream.write(fileReader, 0, read)
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }

    /**
     * 删除指定文件
     * 1.安卓10一下所有文件可删除
     * 2.安卓10以上，只能删除app内部文件
     * @param path 被删除的文件路径
     */
    fun deleteFile(path: String?) {
        if (path.isNullOrEmpty()) return
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    fun readTxtFile(path: String?): String {
        if (path.isNullOrEmpty()) return ""
        val content = StringBuilder() //文件内容字符串
        //打开文件
        val file = File(path)
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory) {
            Logger.d("TestFile", "The File doesn't not exist.")
        } else {
            try {
                val instream: InputStream = FileInputStream(file)
                val inputreader = InputStreamReader(instream)
                val buffreader = BufferedReader(inputreader)
                var line: String?
                //分行读取
                while (buffreader.readLine().also { line = it } != null) {
                    content.append(line).append("\n")
                }
                instream.close()
            } catch (e: FileNotFoundException) {
                Logger.d("TestFile", "The File doesn't not exist.")
            } catch (e: IOException) {
                Logger.d("TestFile", e.message)
            }
        }
        return content.toString()
    }

    /**
     * android 10+ 适配 uri转化为app内部文件
     */
    fun getUri2CachePath(context: Context?, path: String?, uri: String?): String {
        val imgFile = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!imgFile!!.exists()) {
            val a = imgFile.mkdir()
        }
        try {
            val file = File(imgFile.absolutePath + File.separator + getFileNewName(path))
            // 使用openInputStream(uri)方法获取字节输入流
            val fileInputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            val fileOutputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var byteRead: Int
            while (-1 != fileInputStream!!.read(buffer).also { byteRead = it }) {
                fileOutputStream.write(buffer, 0, byteRead)
            }
            fileInputStream.close()
            fileOutputStream.flush()
            fileOutputStream.close()
            val newPath = file.absolutePath
            // 文件可用新路径 file.getAbsolutePath()
            Logger.d("file new path:$newPath")
            return newPath
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取文件新名称
     *
     * @param path 原文件路径
     */
    private fun getFileNewName(path: String?): String {
        try {
            return System.currentTimeMillis().toString() + path!!.substring(path.lastIndexOf("."))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return System.currentTimeMillis().toString()
    }
}