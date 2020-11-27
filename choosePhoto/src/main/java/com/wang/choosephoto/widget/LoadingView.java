package com.wang.choosephoto.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wang.choosephoto.R;


/**
 * 数据加载界面
 */
public class LoadingView extends RelativeLayout {
    private View progressView, errorView, emptyView;
    private SwipeRefreshLayout refreshLayout;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    void init() {
        setBackgroundResource(R.color.color_white);
        refreshLayout = new SwipeRefreshLayout(getContext());
        addView(refreshLayout);
        RelativeLayout contentView = new RelativeLayout(getContext());
        refreshLayout.addView(contentView);

        progressView = LayoutInflater.from(getContext()).inflate(R.layout.x_view_progress, contentView, false);
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.x_view_error, contentView, false);
        emptyView = LayoutInflater.from(getContext()).inflate(R.layout.x_view_empty, contentView, false);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        contentView.addView(errorView);
        contentView.addView(emptyView);
        contentView.addView(progressView);

        showProgress();//初始默认显示进度
    }

    /**
     * 设置下拉刷新
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置刷新状态
     */
    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
        hide();
    }

    /**
     * 显示错误页面
     */
    public void showError() {
        this.setVisibility(VISIBLE);
        errorView.setVisibility(VISIBLE);
        progressView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
    }

    /**
     * 显示无数据界面
     */
    public void showEmpty() {
        this.setVisibility(VISIBLE);
        errorView.setVisibility(GONE);
        progressView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    /**
     * 显示进度界面
     */
    public void showProgress() {
        this.setVisibility(VISIBLE);
        errorView.setVisibility(GONE);
        progressView.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
    }

    /**
     * 隐藏所有界面
     */
    public void hide() {
        this.setVisibility(GONE);
        errorView.setVisibility(GONE);
        progressView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
    }
}
