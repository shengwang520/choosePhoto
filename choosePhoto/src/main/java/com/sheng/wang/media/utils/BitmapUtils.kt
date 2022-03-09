package com.sheng.wang.media.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateUtils
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 选择单张图片处理逻辑
 * Created by wang on 2016/2/15.
 */
object BitmapUtils {
    private const val minSize = 1224 //原图最小边大于1224,等比压缩到1224
    private const val CACHE_IMG = "/CacheImg" //图片缓存目录

    /**
     * 删除图片缓存文件夹
     */
    fun deleteFile(context: Context) {
        val oPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + CACHE_IMG
        val file = File(oPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        val files = file.listFiles()
        if (files != null) {
            for (f in files) {
                f.delete()
            }
        }
    }

    /**
     * 获取图片缓存路径
     */
    private fun getCacheImageFile(context: Context): String {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + CACHE_IMG
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return path
    }

    /**
     * 开始压缩图片
     *
     * @param path 原图片路径
     */
    fun startCompressImage(context: Context, path: String, callback: Callback?) {
        Observable.fromArray(path).map { s ->
            var bitmap: Bitmap
            try {
                bitmap = BitmapFactory.decodeFile(s)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                // 提示系统，进行内存回收
                System.gc()
                val opts = BitmapFactory.Options()
                opts.inSampleSize = 4
                bitmap = BitmapFactory.decodeFile(s, opts)
                //压缩图片重新加载
            }
            bitmap
        }.map { bitmap ->
            var bitmap = bitmap
            val d = getBitmapDegree(path)
            Logger.d("-degree->$d")
            if (d > 0) {
                bitmap = rotateBitmapByDegree(bitmap, d)!!
            }
            bitmap
        }.map { bitmap ->
            var bitmap = bitmap
            if (isValid(bitmap.width.toFloat(), bitmap.height.toFloat())) { //按比例缩放
                bitmap = zoomImg(bitmap)
            }
            bitmap
        }.map { bitmap ->
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            var size = out.toByteArray().size / 1024f
            Logger.d("压缩放缩后的图片大小-》$size")
            if (size > 1000) {
                out.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 99, out)
                size = out.toByteArray().size / 1024f
                Logger.d("压缩99后的图片大小-》$size")
            }
            bitmap.recycle()
            out
        }.map { out ->
            val imgPath = getCacheImageFile(context) + "/" + System.currentTimeMillis() + ".jpg"
            val f = File(imgPath)
            val fOut = FileOutputStream(f)
            out.writeTo(fOut)
            out.flush()
            out.reset()
            out.close()
            fOut.close()
            imgPath
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(s: String) {
                callback?.onComplete(s)
            }

            override fun onError(e: Throwable) {
                //图片压缩出现异常，返回原图路径
                callback?.onComplete(path)
            }

            override fun onComplete() {}
        })
    }

    /**
     * 缩放图片
     */
    private fun zoomImg(bm: Bitmap): Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        Logger.d("image height-w-->$width-h-$height")
        val newWidth: Int
        val newHeight: Int
        if (width > height) {
            newHeight = minSize
            newWidth = (width / height.toFloat() * newHeight).toInt()
        } else if (width < height) {
            newWidth = minSize
            newHeight = (height / width.toFloat() * newWidth).toInt()
        } else {
            newHeight = minSize
            newWidth = newHeight
        }
        Logger.d("image height-w-->$newWidth-h-$newHeight")

        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        val results = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
        bm.recycle()
        return results
    }

    /**
     * 判断图片的最小边是否大于minSize;
     */
    private fun isValid(width: Float, height: Float): Boolean {
        return Math.min(width, height) > minSize
    }

    /**
     * 获取图片旋转角度
     */
    private fun getBitmapDegree(path: String): Int {
        var degree = 0
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            val exifInterface = ExifInterface(path)
            // 获取图片的旋转信息
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 还原旋转图片
     *
     * @param degree 愿旋转角度
     */
    private fun rotateBitmapByDegree(bm: Bitmap, degree: Int): Bitmap? {
        var returnBm: Bitmap? = null
        // 根据旋转角度，生成旋转矩阵
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        }
        if (returnBm == null) {
            returnBm = bm
        }
        if (bm != returnBm) {
            bm.recycle()
        }
        return returnBm
    }

    /**
     * 保存bitmap为jpg 图片
     */
    fun saveBitmap(context: Context, bm: Bitmap?): String? {
        if (bm == null) return ""
        val file = FileUtils.createFile(context, Environment.DIRECTORY_PICTURES, ".jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }
        return ""
    }

    /**
     * 保存图片到相册
     * @param imagePath 图片路径
     */
    fun saveImage2Gallery(context: Context?, imagePath: String?) {
        if (context == null) return
        if (TextUtils.isEmpty(imagePath)) return
        try {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            saveImage2Gallery(context, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 保存图片到相册
     * @param bitmap 图片
     */
    fun saveImage2Gallery(context: Context?, bitmap: Bitmap?) {
        if (context == null) return
        if (bitmap == null) return
        try {
            val contentValues = ContentValues()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH, (Environment.DIRECTORY_PICTURES + File.separator) + context.packageName
                )
                contentValues.put(
                    MediaStore.MediaColumns.DATE_EXPIRES, (System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS) / 1000
                );
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
            contentValues.put(
                MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString() + ".jpg"
            )
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
            contentValues.put(
                MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis() / 1000
            )

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bitmap.recycle()
        }
    }

    interface Callback {
        /**
         * 图片处理界面
         */
        fun onComplete(path: String?)
    }
}