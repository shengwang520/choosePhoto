package com.wang.photo.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * fragment 上层基类
 */
public abstract class BaseFragment extends Fragment {

    protected Activity activity;

    private final Handler mHandler = new Handler();
    private Thread mUiThread;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUiThread = Thread.currentThread();
        View view = inflater.inflate(getLayoutId(), container, false);
        initHolder(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * 返回界面布局id
     */
    public abstract int getLayoutId();

    /**
     * 继承BaseFragment的碎片必须要实现的方法，在填充界面时，初始化控件
     */
    public abstract void initHolder(View view);

    /**
     * 继承BaseFragment的碎片必须要实现的方法, 实现业务逻辑
     */
    public abstract void initView();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 主线程，可更新ui
     */
    public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }

}
