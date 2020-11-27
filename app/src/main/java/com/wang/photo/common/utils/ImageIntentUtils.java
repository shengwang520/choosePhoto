package com.wang.photo.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.common.app.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by 圣王 on 2015/5/20 0020.
 */
public class ImageIntentUtils {

    /**
     * 图片裁切
     */
    public static void crop(AppCompatActivity context, String path) {
        File photoFile = new File(path);
        File outFile;
        try {
            outFile = new File(new FileUtils().getSaveImageFilePath() + "/" + System.currentTimeMillis() + ".jpg");
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", photoFile);
            } else {
                uri = Uri.fromFile(photoFile);
            }

            UCrop.Options options = new UCrop.Options();
            //设置裁剪图片可操作的手势
            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
            //设置隐藏底部容器，默认显示
//        options.setHideBottomControls(true);
            //设置toolbar颜色
            options.setToolbarColor(ActivityCompat.getColor(context, R.color.colorPrimary));
            //设置状态栏颜色
            options.setStatusBarColor(ActivityCompat.getColor(context, R.color.colorPrimary));
            options.setToolbarWidgetColor(ActivityCompat.getColor(context, R.color.color_222222));
//            options.setToolbarCancelDrawable(R.drawable.return_icon);

            UCrop.of(uri, Uri.fromFile(outFile))
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(800, 800)
                    .withOptions(options)
                    .start(context);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 照相
     */
    public static String takePicture(Activity context, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImagFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", photoFile);
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


    /**
     * 按时间命名文件
     */
    private static File createImagFile() throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES);
        boolean exsits = storageDir.mkdirs();
        return File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg",
                storageDir);
    }

}
