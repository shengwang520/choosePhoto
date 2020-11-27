package com.wang.choosephoto.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * editview相关设置
 * Created by wang on 2015/12/17.
 */
public class EditViewUtils {

    /**
     * 关闭键盘
     */
    public static void setCloseInput(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /**
     * 弹出键盘
     */
    public static void setOpenInput(Context context, EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(edit, 0);
    }

    /**
     * 移动光标
     */
    public static void onMoveGuangBiao2End(EditText editText) {
        Editable text = editText.getEditableText();
        Selection.setSelection(text, text.length());
    }

}
