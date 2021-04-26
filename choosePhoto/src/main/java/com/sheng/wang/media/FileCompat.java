package com.sheng.wang.media;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.orhanobut.logger.Logger;
import com.sheng.wang.media.impl.CallBack;
import com.sheng.wang.media.impl.IFile;
import com.sheng.wang.media.impl.ILoadFile;
import com.sheng.wang.media.model.FileBean;
import com.sheng.wang.media.model.FileFolder;
import com.wang.media.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 本地文件扫描
 */
public class FileCompat implements ILoadFile {
    private Context context;
    private List<FileFolder> listImages;
    private CallBack callBack;
    private HashMap<String, Integer> tmpDir = new HashMap<>();//临时的辅助类，用于防止同一个文件夹的多次扫描

    public FileCompat(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        listImages = new ArrayList<>();
    }

    @Override
    public void loadImages() {
        final FileFolder all = new FileFolder();
        all.name = context.getString(R.string.all_images);
        listImages.add(all);
        Observable
                .create(new ObservableOnSubscribe<FileBean>() {

                    @Override
                    public void subscribe(@NotNull ObservableEmitter<FileBean> e) {
                        Cursor mCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.DATE_ADDED}, "", null,
                                MediaStore.MediaColumns.DATE_ADDED + " DESC");
                        if (mCursor != null && mCursor.moveToFirst()) {
                            do {
                                String path = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                                String id = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                                String uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString();
                                int width = mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
                                int height = mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
                                long time = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                                FileBean fileBean = new FileBean(path);
                                fileBean.fileUri = uri;
                                fileBean.width = width;
                                fileBean.height = height;
                                fileBean.createTime = time;
                                Logger.d("file start query:" + fileBean.toString());
                                e.onNext(fileBean);
                            } while (mCursor.moveToNext());
                        }
                        if (mCursor != null)
                            mCursor.close();

                        e.onComplete();
                    }
                })
                .filter(new Predicate<FileBean>() {
                    @Override
                    public boolean test(@NotNull FileBean fileBean) {
                        return fileBean.width >= 800
                                || fileBean.height >= 800;
                    }
                })
                .filter(new Predicate<FileBean>() {
                    @Override
                    public boolean test(@NonNull FileBean fileBean) throws Exception {
                        return fileBean.isExists();
                    }
                })
                .map(new Function<FileBean, FileFolder>() {
                    @Override
                    public FileFolder apply(@NotNull FileBean fileBean) {
                        all.images.add(fileBean);
                        all.firstFileBean = all.images.get(0);

                        // 获取该图片的父路径名
                        String fileParentName = fileBean.getFileParentName();

                        FileFolder imageFolder;
                        if (!tmpDir.containsKey(fileParentName)) {
                            // 初始化imageFolder
                            imageFolder = new FileFolder();
                            imageFolder.name = fileParentName;
                            imageFolder.firstFileBean = fileBean;
                            listImages.add(imageFolder);
                            tmpDir.put(fileParentName, listImages.indexOf(imageFolder));
                        } else {
                            imageFolder = listImages.get(tmpDir.get(fileParentName));
                        }
                        imageFolder.images.add(fileBean);
                        return imageFolder;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FileFolder>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull FileFolder imageFolder) {
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (callBack != null) {
                            callBack.onSuccess(listImages);
                        }
                    }
                });
    }


    /**
     * 获取适配筛选条件
     */
    private String[] getVideoProjection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new String[]{MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.ORIENTATION};
        } else {
            return new String[]{MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DURATION, MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_ADDED};
        }
    }

    @Override
    public void loadVideos() {
        final FileFolder all = new FileFolder();
        all.name = context.getString(R.string.all_videos);
        listImages.add(all);
        Observable
                .create(new ObservableOnSubscribe<FileBean>() {

                    @Override
                    public void subscribe(@NotNull ObservableEmitter<FileBean> e) {
                        Cursor mCursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                getVideoProjection(), "", null,
                                MediaStore.MediaColumns.DATE_ADDED + " DESC");
                        if (mCursor != null && mCursor.moveToFirst()) {
                            do {
                                String path = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                                long duration = mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长 （毫秒）
                                String id = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                                String uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id).toString();
                                long time = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));
                                FileBean imageBean = new FileBean(path, IFile.Type.FILE_TYPE_VIDEO);
                                imageBean.fileUri = uri;
                                imageBean.videoTime = duration;
                                imageBean.createTime = time;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    String orientation = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.ORIENTATION));
                                    imageBean.rotation = orientation;
                                }
                                Logger.d("file query video:" + imageBean.toString());
                                e.onNext(imageBean);
                            } while (mCursor.moveToNext());
                        }
                        if (mCursor != null)
                            mCursor.close();

                        e.onComplete();
                    }
                })
                .filter(new Predicate<FileBean>() {
                    @Override
                    public boolean test(@NotNull FileBean imageBean) throws Exception {
                        //视频时长限制在10-30秒
                        return imageBean.videoTime >= 3 * 1000 && imageBean.videoTime <= 30 * 1000;
                    }
                })
                .filter(new Predicate<FileBean>() {
                    @Override
                    public boolean test(@NonNull FileBean fileBean) throws Exception {
                        return fileBean.isExists();
                    }
                })
                .map(new Function<FileBean, FileBean>() {
                    @Override
                    public FileBean apply(@NonNull FileBean imageBean) throws Exception {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            imageBean.rotation = getRotation(imageBean.filePath);
                        }
                        return imageBean;
                    }
                })
                .map(new Function<FileBean, FileFolder>() {
                    @Override
                    public FileFolder apply(@NotNull FileBean imageBean) throws Exception {
                        all.images.add(imageBean);
                        all.firstFileBean = all.images.get(0);

                        // 获取该图片的父路径名
                        String fileParentName = imageBean.getFileParentName();

                        FileFolder imageFolder;
                        if (!tmpDir.containsKey(fileParentName)) {
                            // 初始化imageFolder
                            imageFolder = new FileFolder();
                            imageFolder.name = fileParentName;
                            imageFolder.firstFileBean = imageBean;
                            listImages.add(imageFolder);
                            tmpDir.put(fileParentName, listImages.indexOf(imageFolder));
                        } else {
                            imageFolder = listImages.get(tmpDir.get(fileParentName));
                        }
                        imageFolder.images.add(imageBean);
                        return imageFolder;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FileFolder>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Logger.d("find video onSubscribe");

                    }

                    @Override
                    public void onNext(@NotNull FileFolder imageFolder) {
                        Logger.d("find video onNext");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Logger.d("find video onError:" + e.toString());
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("find video complete");
                        if (callBack != null) {
                            callBack.onSuccess(listImages);
                        }
                    }
                });
    }

    /**
     * 获取视频方向
     *
     * @param videoUrl 视频地址
     */
    private static String getRotation(String videoUrl) {
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoUrl);
            String rotation = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            Logger.d("video rotation:" + rotation);
            return rotation;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

}
