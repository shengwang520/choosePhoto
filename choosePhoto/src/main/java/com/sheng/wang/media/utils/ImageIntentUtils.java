package com.sheng.wang.media.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * 拍照
 */
public class ImageIntentUtils {

    /**
     * 照相
     */
    public static String takePicture(Activity context, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = FileUtils.createFile(context, Environment.DIRECTORY_PICTURES, ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", photoFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(photoFile);
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                context.startActivityForResult(intent, requestCode);

                //刷新系统相册
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + photoFile.getAbsolutePath())));

            }
            return (photoFile != null ? photoFile.getAbsolutePath() : null);
        }
        return null;
    }

}
