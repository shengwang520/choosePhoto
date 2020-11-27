package com.wang.choosephoto.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * EditText 右图可点击
 */
public class DrawableUtil {


    /**
     * TextView四周drawable的序号。
     * 0 left,  1 top, 2 right, 3 bottom
     */
    private final int RIGHT = 2;

    private OnDrawableListener listener;
    private EditText mTextView;


    public DrawableUtil(EditText textView, OnDrawableListener l) {
        mTextView = textView;
        listener = l;

        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (listener != null) {
                            Drawable drawableRight = mTextView.getCompoundDrawables()[RIGHT];
                            if (drawableRight != null && event.getRawX() >= (mTextView.getRight() - drawableRight.getBounds().width())) {
                                listener.onRight(v, drawableRight);
                                return true;
                            }
                        }

                        break;
                }

                return false;
            }

        };
        mTextView.setOnTouchListener(mOnTouchListener);
    }

    public interface OnDrawableListener {

        void onRight(View v, Drawable right);
    }

}
