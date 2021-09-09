package com.sheng.wang.media

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.orhanobut.logger.Logger
import com.sheng.wang.media.impl.CallBack
import com.sheng.wang.media.impl.IFile
import com.sheng.wang.media.impl.ILoadFile
import com.sheng.wang.media.model.FileBean
import com.sheng.wang.media.model.FileFolder
import com.wang.media.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 本地文件扫描{Manifest.permission.READ_EXTERNAL_STORAGE}
 */
class FileCompat(private val context: Context, private val callBack: CallBack?) : ILoadFile {
    private val listImages: MutableList<FileFolder> = ArrayList()
    private val tmpDir = HashMap<String, Int>() //临时的辅助类，用于防止同一个文件夹的多次扫描
    override fun loadImages() {
        val all = FileFolder()
        all.name = context.getString(R.string.all_images)
        listImages.add(all)
        Observable
            .create<FileBean> { e ->
                val mCursor =
                    context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.DATE_ADDED), "", null,
                        MediaStore.MediaColumns.DATE_ADDED + " DESC")
                if (mCursor != null && mCursor.moveToFirst()) {
                    do {
                        val path =
                            mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                        val id =
                            mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        val uri =
                            Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                                .toString()
                        val width =
                            mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH))
                        val height =
                            mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT))
                        val time =
                            mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                        val fileBean = FileBean(false, path)
                        fileBean.fileUri = uri
                        fileBean.width = width
                        fileBean.height = height
                        fileBean.createTime = time
                        Logger.d("file start query:$fileBean")
                        e.onNext(fileBean)
                    } while (mCursor.moveToNext())
                }
                mCursor?.close()
                e.onComplete()
            }
            .filter { fileBean ->
                (fileBean.width >= FileOptions.width
                        || fileBean.height >= FileOptions.height)
            }
            .filter { fileBean -> fileBean.isExists }
            .map { fileBean ->
                all.images.add(fileBean)
                all.firstFileBean = all.images[0]

                // 获取该图片的父路径名
                val fileParentName = fileBean.fileParentName
                val imageFolder: FileFolder?
                if (!tmpDir.containsKey(fileParentName)) {
                    // 初始化imageFolder
                    imageFolder = FileFolder()
                    imageFolder.name = fileParentName
                    imageFolder.firstFileBean = fileBean
                    listImages.add(imageFolder)
                    tmpDir[fileParentName] = listImages.indexOf(imageFolder)
                } else {
                    imageFolder = listImages[tmpDir[fileParentName]!!]
                }
                imageFolder.images.add(fileBean)
                imageFolder
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<FileFolder> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(imageFolder: FileFolder) {}
                override fun onError(e: Throwable) {
                    callBack?.onError()
                }

                override fun onComplete() {
                    callBack?.onSuccess(listImages)
                }
            })
    }

    /**
     * 获取适配筛选条件
     */
    private val videoProjection: Array<String>
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.ORIENTATION)
        } else {
            arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_ADDED)
        }

    override fun loadVideos() {
        val all = FileFolder()
        all.name = context.getString(R.string.all_videos)
        listImages.add(all)
        Observable
            .create<FileBean> { e ->
                val mCursor =
                    context.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        videoProjection, "", null,
                        MediaStore.MediaColumns.DATE_ADDED + " DESC")
                if (mCursor != null && mCursor.moveToFirst()) {
                    do {
                        val path =
                            mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)) // 路径
                        val duration =
                            mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                                .toLong() // 时长 （毫秒）
                        val id =
                            mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                        val uri =
                            Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                                .toString()
                        val time =
                            mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))
                        val imageBean = FileBean(false, path, IFile.Type.FILE_TYPE_VIDEO)
                        imageBean.fileUri = uri
                        imageBean.videoTime = duration
                        imageBean.createTime = time
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            imageBean.rotation =
                                mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.ORIENTATION))
                        }
                        Logger.d("file query video:$imageBean")
                        e.onNext(imageBean)
                    } while (mCursor.moveToNext())
                }
                mCursor?.close()
                e.onComplete()
            }
            .filter { imageBean -> //视频时长限制在10-30秒
                imageBean.videoTime >= FileOptions.videoMinTime && imageBean.videoTime <= FileOptions.videoMaxTime
            }
            .filter { fileBean -> fileBean.isExists }
            .map { imageBean ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imageBean.rotation = getRotation(imageBean.filePath)
                }
                imageBean
            }
            .map { imageBean ->
                all.images.add(imageBean)
                all.firstFileBean = all.images[0]

                // 获取该图片的父路径名
                val fileParentName = imageBean.fileParentName
                val imageFolder: FileFolder?
                if (!tmpDir.containsKey(fileParentName)) {
                    // 初始化imageFolder
                    imageFolder = FileFolder()
                    imageFolder.name = fileParentName
                    imageFolder.firstFileBean = imageBean
                    listImages.add(imageFolder)
                    tmpDir[fileParentName] = listImages.indexOf(imageFolder)
                } else {
                    imageFolder = listImages[tmpDir[fileParentName]!!]
                }
                imageFolder.images.add(imageBean)
                imageFolder
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<FileFolder> {
                override fun onSubscribe(d: Disposable) {
                    Logger.d("find video onSubscribe")
                }

                override fun onNext(imageFolder: FileFolder) {
                    Logger.d("find video onNext")
                }

                override fun onError(e: Throwable) {
                    Logger.d("find video onError:$e")
                    callBack?.onError()
                }

                override fun onComplete() {
                    Logger.d("find video complete")
                    callBack?.onSuccess(listImages)
                }
            })
    }

    companion object {
        /**
         * 获取视频方向
         *
         * @param videoUrl 视频地址
         */
        private fun getRotation(videoUrl: String?): String? {
            try {
                val mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(videoUrl)
                val rotation =
                    mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
                Logger.d("video rotation:$rotation")
                return rotation
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "0"
        }
    }

}