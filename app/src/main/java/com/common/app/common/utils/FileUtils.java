package com.common.app.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * 本地文件管理
 */
public class FileUtils {
    private static final String FILE_NAME = "Travel.apk";//app名称
    private static final String CACHE_DATA = "/CacheData";//缓存目录
    private static final String CACHE_CHAT_IMG = "ChatCacheImg";//聊天图片缓存目录
    private static final String CACHE_CHAT_GIFT_IMG = "ChatCacheGImg";//聊天礼物图片缓存目录
    private static final String IMAGE_SAVE = "Images";//图片本地保存目录
    private String SDPATH;

    public FileUtils() {
        //得到当前外部存储设备的目录
        // /SDCARD
        SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * 获取数据缓存路径
     */
    public static String getCacheData(Context context) {
        return context.getFilesDir().getAbsolutePath() + CACHE_DATA;
    }

    /**
     * 获取聊天礼物缓存路径
     */
    public static String getChatCacheImag(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + CACHE_CHAT_GIFT_IMG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    //普通安装
    public static void installNormal(Context context, String apkPath) {
        Logger.d("apk path:" + apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 在SD卡上创建文件
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        if (!dir.exists())
            dir.mkdir();
        return dir;
    }

    /**
     * 获取apk文件路径
     */
    public String getFileName() {
        return SDPATH + FILE_NAME;
    }

    /**
     * 获取图片保存目录
     */
    public String getSaveImageFilePath() {
        String path = SDPATH + IMAGE_SAVE;
        creatSDDir(IMAGE_SAVE);
        return path;
    }

    /**
     * 将下载的数据写入本地
     *
     * @param body 下载的数据
     * @param path 需要写入的本地文件路径
     */
    public boolean writeResponseBodyToDisk(ResponseBody body, String path) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File futureStudioIconFile = new File(path);
                byte[] fileReader = new byte[1024];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                int read;
                while ((read = inputStream.read(fileReader)) != -1) {
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 删除指定文件
     *
     * @param path 被删除的文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
