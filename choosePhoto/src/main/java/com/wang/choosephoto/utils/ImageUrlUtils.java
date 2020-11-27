package com.wang.choosephoto.utils;

/**
 * 图片处理
 */
public class ImageUrlUtils {

    private static String IMAGE_SMALL = "?x-oss-process=style/thumb_small";
    private static String IMAGE_MIDDLE = "?x-oss-process=style/thumb_middle";
    private static String IMAGE_LARGE = "?x-oss-process=style/thumb_large";

    /**
     * 获取小图
     */
    public static String getImageSmallUrl(String imageUrl) {
        return imageUrl + IMAGE_MIDDLE;
    }

    /**
     * 获取中图
     */
    public static String getImageMiddleUrl(String imageUrl) {
        return imageUrl + IMAGE_LARGE;
    }

    /**
     * 获取大图
     */
    public static String getImageLargeUrl(String imageUrl) {
        return imageUrl + IMAGE_LARGE;
    }

}
