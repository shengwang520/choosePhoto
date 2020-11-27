package com.wang.photo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.common.app.R;


/**
 * 自定义弹出提示框
 */
public class CustomDialog extends Dialog {
    //构建参数
    private OnClickListener onCancelListener;
    private OnClickListener onConfirmListener;
    private String title;
    private int titleColor;
    private String message;
    private int messageColor;
    private int messageTopIcon;//消息顶部图片
    private boolean isMessageLeft;
    private String confrimtext;
    private int confrimColor;
    private int confrimIcon;//确认的图片
    private String canceltext;
    private int cancelColor;
    private int cancelIcon;//取消的图片
    private View customview;

    private int bgColor;
    private boolean isClose = true;//是否关闭

    private Context mContext;

    private CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_view_custom_dialog);
        TextView tv_title = findViewById(R.id.common_dialog_tv_title);
        TextView tv_message = findViewById(R.id.common_dialog_tv_message);
        TextView bt_cancel = findViewById(R.id.common_dialog_bt_Cancel);
        TextView bt_confirm = findViewById(R.id.common_dialog_bt_Confirm);
        LinearLayout mView = findViewById(R.id.common_dialog_ll_view);
        LinearLayout bgView = findViewById(R.id.common_dialog_bgview);
        //

        if (bgColor > 0) {
            bgView.setBackgroundResource(bgColor);
        }

        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
            if (titleColor > 0) tv_title.setTextColor(ContextCompat.getColor(mContext, titleColor));
        }

        //
        if (TextUtils.isEmpty(message)) {
            tv_message.setVisibility(View.GONE);
        } else {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText(message);
            if (messageColor > 0)
                tv_message.setTextColor(ContextCompat.getColor(mContext, messageColor));

            if (isMessageLeft) {
                tv_message.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            }

            if (messageTopIcon > 0) {
                tv_message.setCompoundDrawablesWithIntrinsicBounds(0, messageTopIcon, 0, 0);
            }
        }

        //
        if (TextUtils.isEmpty(canceltext)) {
            bt_cancel.setVisibility(View.GONE);
        } else {
            bt_cancel.setVisibility(View.VISIBLE);
            bt_cancel.setText(canceltext);
            if (cancelColor > 0)
                bt_cancel.setTextColor(ContextCompat.getColor(mContext, cancelColor));

        }

        if (cancelIcon > 0) {
            bt_cancel.setText("");
            bt_cancel.setPadding(0, 20, 0, 0);
            bt_cancel.setCompoundDrawablesWithIntrinsicBounds(0, cancelIcon, 0, 0);
        }
        //
        if (!TextUtils.isEmpty(confrimtext)) {
            bt_confirm.setText(confrimtext);
            if (confrimColor > 0)
                bt_confirm.setTextColor(ContextCompat.getColor(mContext, confrimColor));
        }

        if (confrimIcon > 0) {
            bt_confirm.setText("");
            bt_confirm.setPadding(0, 20, 0, 0);
            bt_confirm.setCompoundDrawablesWithIntrinsicBounds(0, confrimIcon, 0, 0);
        }
        //
        if (customview != null) {
            mView.setVisibility(View.VISIBLE);
            mView.addView(customview);
        }

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                if (onCancelListener != null) {
                    onCancelListener.onClick(CustomDialog.this, v);
                }
            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClose)
                    dismiss();
                if (onConfirmListener != null) {
                    onConfirmListener.onClick(CustomDialog.this, v);
                }
            }
        });
    }


    public void setOnCancelListener(OnClickListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public void setOnConfirmListener(OnClickListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public void setConfrimIcon(int confrimIcon) {
        this.confrimIcon = confrimIcon;
    }

    public void setCancelIcon(int cancelIcon) {
        this.cancelIcon = cancelIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageColor(int messageColor) {
        this.messageColor = messageColor;
    }

    public void setConfrimtext(String confrimtext) {
        this.confrimtext = confrimtext;
    }

    public void setConfrimColor(int confrimColor) {
        this.confrimColor = confrimColor;
    }

    public void setCanceltext(String canceltext) {
        this.canceltext = canceltext;
    }

    public void setCancelColor(int cancelColor) {
        this.cancelColor = cancelColor;
    }

    public void setCustomview(View customview) {
        this.customview = customview;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public void setMessageLeft(boolean messageLeft) {
        isMessageLeft = messageLeft;
    }

    public void setMessageTopIcon(int messageTopIcon) {
        this.messageTopIcon = messageTopIcon;
    }

    public interface OnClickListener {
        void onClick(CustomDialog dialogCustom, View v);
    }

    public static class Builder {
        private Context context;
        private OnClickListener onCancelListener;
        private OnClickListener onConfirmListener;
        private String title;
        private int titleColor;
        private String message;
        private int messageColor;
        private int messageTopIcon;
        private boolean isMessageLeft;
        private String confrimtext;
        private int confrimColor;
        private int confrimIcon;//确认的图片
        private String canceltext;
        private int cancelIcon;//取消的图片
        private int cancelColor;
        private View customview;

        private int bgColor;
        private boolean isClose = true;

        public Builder(Context context) {
            this.context = context;
            canceltext = context.getString(R.string.cancel);
        }

        public Builder setCancelListener(OnClickListener onClickListener) {
            this.onCancelListener = onClickListener;
            return this;
        }

        public Builder setConfirmListener(OnClickListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageColor(int messageColor) {
            this.messageColor = messageColor;
            return this;
        }

        public Builder setMessageTopIcon(int messageTopIcon) {
            this.messageTopIcon = messageTopIcon;
            return this;
        }

        public Builder setConfrimtext(String confrimtext) {
            this.confrimtext = confrimtext;
            return this;
        }

        public Builder setConfrimColor(int confrimColor) {
            this.confrimColor = confrimColor;
            return this;
        }

        public Builder setCanceltext(String canceltext) {
            this.canceltext = canceltext;
            return this;
        }

        public Builder setCloseCancel(boolean isCloseCancel) {
            if (isCloseCancel) canceltext = null;
            return this;
        }

        public Builder setCancelColor(int cancelColor) {
            this.cancelColor = cancelColor;
            return this;
        }

        public Builder setCustomview(View customview) {
            this.customview = customview;
            return this;
        }

        public Builder setBgColorResource(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setMessageLeft(boolean messageLeft) {
            isMessageLeft = messageLeft;
            return this;
        }

        public Builder setConfrimIcon(int confrimIcon) {
            this.confrimIcon = confrimIcon;
            return this;
        }

        public Builder setCancelIcon(int cancelIcon) {
            this.cancelIcon = cancelIcon;
            return this;
        }

        /**
         * 是否点击确认关闭dialog,默认关闭
         */
        public Builder setConfrimClose(boolean close) {
            this.isClose = close;
            return this;
        }

        public CustomDialog create() {
            CustomDialog dialogCustom = new CustomDialog(context, R.style.Dialog);
            dialogCustom.setCancelColor(cancelColor);
            dialogCustom.setCanceltext(canceltext);
            dialogCustom.setOnCancelListener(onCancelListener);
            dialogCustom.setConfrimColor(confrimColor);
            dialogCustom.setConfrimtext(confrimtext);
            dialogCustom.setOnConfirmListener(onConfirmListener);
            dialogCustom.setCustomview(customview);
            dialogCustom.setMessage(message);
            dialogCustom.setMessageColor(messageColor);
            dialogCustom.setTitle(title);
            dialogCustom.setTitleColor(titleColor);
            dialogCustom.setBgColor(bgColor);
            dialogCustom.setClose(isClose);
            dialogCustom.setConfrimIcon(confrimIcon);
            dialogCustom.setCancelIcon(cancelIcon);
            dialogCustom.setMessageLeft(isMessageLeft);
            dialogCustom.setMessageTopIcon(messageTopIcon);
            return dialogCustom;
        }
    }
}
