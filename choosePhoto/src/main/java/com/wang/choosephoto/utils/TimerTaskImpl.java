package com.wang.choosephoto.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;


import com.wang.choosephoto.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 获取验证码时间倒数
 * Created by wang on 2015/10/19.
 */
public class TimerTaskImpl extends TimerTask {

    private TextView textView;
    private static final long m = 1000;
    private Timer timer;
    private int time = 60;
    private Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what > 0) {
                textView.setText(String.format("重获验证码(%ss)", String.valueOf(msg.what)));
                textView.setEnabled(false);
            } else {
                stop();
            }
            return false;
        }
    });

    public TimerTaskImpl(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(time);
        time--;
        System.out.println("TimerTask time = " + time);
    }


    public void start() {
        timer = new Timer();
        timer.schedule(this, m, m);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        textView.setText(R.string.sms_code);
        textView.setEnabled(true);
    }
}
