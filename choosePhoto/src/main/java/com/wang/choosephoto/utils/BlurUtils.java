package com.wang.choosephoto.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.orhanobut.logger.Logger;

/**
 * 图片模糊处理
 */
public class BlurUtils {

    /**
     * 模糊图片
     *
     * @param context context
     * @param source  需要模糊的图片资源
     * @param radius  模糊半径
     * @return 模糊的图片
     */
    public static Bitmap getBlurBitmap(Context context, Bitmap source, int radius) {
        float scale = 1 / 8f;//制定模糊前缩小的倍数,缩小的系数应该为2的整数次幂 ，即上面代码中的scale应该为1/2、1/4、1/8。 一般scale = 1/8 为佳。
        Logger.d("blur origin size:" + source.getWidth() + "*" + source.getHeight());
        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);
        Bitmap inputBmp = Bitmap.createScaledBitmap(source, width, height, false);
        RenderScript renderScript = RenderScript.create(context);
        Logger.d("blur scale size:" + inputBmp.getWidth() + "*" + inputBmp.getHeight());
        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);
        renderScript.destroy();
        return inputBmp;
    }

    /**
     * 获取本地视频的第一帧
     */
    public static Bitmap getLocalVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(filePath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

}
