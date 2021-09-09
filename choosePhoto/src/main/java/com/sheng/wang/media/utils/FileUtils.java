package com.sheng.wang.media.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * 本地文件管理
 */
public class FileUtils {
    private static final String CACHE_DATA = "/CacheData";//缓存目录
    private static final String CACHE_GIFT_ANIM = "/GiftAnim";//礼物动画缓存目录
    private static final String CACHE_CHAT_GIFT_IMG = "ChatCacheGImg";//聊天礼物图片缓存目录

    private FileUtils() {
    }

    /**
     * 获取数据缓存路径
     */
    public static String getCacheData(Context context) {
        return context.getFilesDir().getAbsolutePath() + CACHE_DATA;
    }

    /**
     * 获取数据缓存路径
     */
    public static String getCacheGiftAnim(Context context) {
        return context.getFilesDir().getAbsolutePath() + CACHE_GIFT_ANIM;
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
     * 创建文件
     *
     * @param type   文件缓存类型{@link Environment}
     * @param suffix 文件后缀
     */
    public static File createFile(Context context, String type, String suffix) throws IOException {
        File storageDir = context.getExternalFilesDir(type);
        return File.createTempFile(String.valueOf(System.currentTimeMillis()), suffix, storageDir);
    }

    /**
     * 将下载的数据写入本地
     *
     * @param body 下载的数据
     * @param path 需要写入的本地文件路径
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String path) {
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
     * <p>1.安卓10一下所有文件可删除<p/>
     * <p>2.安卓10以上，只能删除app内部文件<p/>
     *
     * @param path 被删除的文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }


    public static String ReadTxtFile(String path) {
        StringBuilder content = new StringBuilder(); //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Logger.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                instream.close();
            } catch (java.io.FileNotFoundException e) {
                Logger.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Logger.d("TestFile", e.getMessage());
            }
        }
        return content.toString();
    }

    /**
     * android 10+ 适配 uri转化为app内部文件
     */
    public static String getUri2CachePath(Context context, String path, String uri) {
        File imgFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!imgFile.exists()) {
            boolean a = imgFile.mkdir();
        }
        try {
            File file = new File(imgFile.getAbsolutePath() + File.separator + getFileNewName(path));
            // 使用openInputStream(uri)方法获取字节输入流
            InputStream fileInputStream = context.getContentResolver().openInputStream(Uri.parse(uri));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            String newPath = file.getAbsolutePath();
            // 文件可用新路径 file.getAbsolutePath()
            Logger.d("file new path:" + newPath);
            return newPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件新名称
     *
     * @param path 原文件路径
     */
    private static String getFileNewName(String path) {
        try {
            return System.currentTimeMillis() + path.substring(path.lastIndexOf("."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }
}
