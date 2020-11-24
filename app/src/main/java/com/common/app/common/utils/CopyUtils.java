package com.common.app.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 复制
 */
public class CopyUtils {

    /**
     * 复制
     */
    public static void copy(Context context, String content) {
        copy(context, content, "订单号已复制到剪切板");
    }

    /**
     * 复制
     */
    public static void copy(Context context, String content, String msg) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        assert cm != null;
        cm.setPrimaryClip(mClipData);
        ToastUtils.show(context, msg);
    }
}
