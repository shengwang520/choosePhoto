package com.wang.photo.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.common.app.R;
import com.wang.photo.common.widget.CustomProgressDialog;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 选择单张图片处理逻辑
 * Created by wang on 2016/2/15.
 */
public class BitmapUtils {

    /**
     * 保存图片到本地
     */
    public static void saveImage2Local(final Context context, String url) {
        final CustomProgressDialog dialog = DialogUtils.create(context);
        Observable.fromArray(url)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL pictureUrl = new URL(s);
                        InputStream in = pictureUrl.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                        return bitmap;
                    }
                })
                .map(new Function<Bitmap, String>() {
                    @Override
                    public String apply(Bitmap bitmap) throws Exception {
                        FileUtils fileUtils = new FileUtils();
                        String pictureName = fileUtils.getSaveImageFilePath() + "/" + System.currentTimeMillis() + ".jpg";
                        File file = new File(pictureName);
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        return pictureName;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        dialog.dismiss();
                        Logger.d("image save success path:" + s);
                        //刷新系统相册
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + s)));
                        ToastUtils.show(context, context.getString(R.string.image_save_success));
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        ToastUtils.show(context, context.getString(R.string.image_save_fail));
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

}
