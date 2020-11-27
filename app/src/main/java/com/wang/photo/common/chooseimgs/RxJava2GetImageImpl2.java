package com.wang.photo.common.chooseimgs;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取手机上的所有图片任务，并进行分组
 * Created by wang on 2015/12/16.
 */
public class RxJava2GetImageImpl2 {
    private Context context;
    private List<ImageFolder> listImages;
    private CallBack callBack;
    private HashMap<String, Integer> tmpDir = new HashMap<>();//临时的辅助类，用于防止同一个文件夹的多次扫描

    public RxJava2GetImageImpl2(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.listImages = new ArrayList<>();
    }

    /**
     * 使用rxjava2获取图片
     */
    public void getPhoneImages() {
        final ImageFolder all = new ImageFolder();
        all.setDir("/所有图片");
        listImages.add(all);
        Observable
                .create(new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(ObservableEmitter<String> e) {
                        Cursor mCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                                MediaStore.MediaColumns.DATE_ADDED + " DESC");
                        if (mCursor != null && mCursor.moveToFirst()) {
                            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                            do {
                                e.onNext(mCursor.getString(_date));
                            } while (mCursor.moveToNext());
                        }
                        if (mCursor != null)
                            mCursor.close();

                        e.onComplete();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        //判断图片是否存在
                        File file = new File(s);
                        return file.exists();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        //检查图片是否损坏
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(s, options); //filePath代表图片路径//限制图片最小为800*800
                        return !options.mCancel && options.outWidth >= 800
                                && options.outHeight >= 800;
                    }
                })
                .map(new Function<String, ImageFolder>() {
                    @Override
                    public ImageFolder apply(String s) {
                        all.images.add(new ImageBean(s));
                        all.setFirstImagePath(all.images.get(0).getImgpath());

                        // 获取该图片的父路径名
                        File parentFile = new File(s).getParentFile();
                        String dirPath = parentFile.getAbsolutePath();

                        ImageFolder imageFloder;
                        if (!tmpDir.containsKey(dirPath)) {
                            // 初始化imageFloder
                            imageFloder = new ImageFolder();
                            imageFloder.setDir(dirPath);
                            imageFloder.setFirstImagePath(s);
                            listImages.add(imageFloder);
                            tmpDir.put(dirPath, listImages.indexOf(imageFloder));
                        } else {
                            imageFloder = listImages.get(tmpDir.get(dirPath));
                        }
                        imageFloder.images.add(new ImageBean(s));
                        return imageFloder;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageFolder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageFolder imageFolder) {
                    }

                    @Override
                    public void onError(Throwable e) {
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

    public interface CallBack {
        void onSuccess(List<ImageFolder> results);

        void onError();
    }
}
