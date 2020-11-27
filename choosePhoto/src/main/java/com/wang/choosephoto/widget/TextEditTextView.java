package com.wang.choosephoto.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 可监听软件盘隐藏的EditText
 */
public class TextEditTextView extends AppCompatEditText {
    /**
     * 键盘监听接口
     */
    OnKeyBoardHideListener onKeyBoardHideListener;

    public TextEditTextView(Context context) {
        super(context);
    }

    public TextEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public TextEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            if (onKeyBoardHideListener != null) {
                onKeyBoardHideListener.onKeyHide(keyCode, event);
            }
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public interface OnKeyBoardHideListener {
        void onKeyHide(int keyCode, KeyEvent event);
    }
}
