package com.wang.photo.common.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wang.photo.common.utils.MetricsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 目前只支持LinearLayout数据适配
 * Created by wangshengqiang on 2017/7/14.
 */

public abstract class BaseGroupAdapter<T> {
    private LinearLayout mViewGroup;
    private Context mContext;
    private float mOffset = 0;
    private List<T> mData;
    private int mNum = 1;//默认为每行1条数据
    private int mGravity = Gravity.START;
    private boolean isResetWidth;//是否需要重新设置宽度

    private BaseViewItemHolder mSelectorHolder;
    private OnViewSelectedListener mOnViewSelectedListener;

    protected BaseGroupAdapter(LinearLayout viewGroup) {
        this(viewGroup, 1);
    }

    protected BaseGroupAdapter(LinearLayout viewGroup, int num) {
        viewGroup.setOrientation(LinearLayout.VERTICAL);
        this.mViewGroup = viewGroup;
        this.mNum = num;
        mContext = viewGroup.getContext();
        mData = new ArrayList<>();
    }

    /**
     * 是否需要重新设置宽度,再num=1时有效,默认不需要
     */
    public void setResetWidth(boolean resetWidth) {
        isResetWidth = resetWidth;
    }

    /**
     * 设置每一行的个数
     */
    public void setNum(int num) {
        this.mNum = num;
    }

    /**
     * 设置布局内容显示位置
     */
    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    /**
     * 设置偏移量(dp)
     */
    public void setOffset(float offset) {
        this.mOffset = offset;
    }

    public void add(T t) {
        mData.add(t);
    }

    public void addAll(List<T> data) {
        mData.addAll(data);
    }

    public void clear() {
        mData.clear();
    }

    /**
     * 刷新界面
     */
    public void notifyDataSetChanged() {
        changFrameLayoutViews(mViewGroup);
    }

    private void changFrameLayoutViews(LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        if (getCount() > 0) {
            if (mNum > 1) {
                for (int i = 0, size = getCount(); i < size; i += mNum) {
                    LinearLayout itemParent = new LinearLayout(mContext);
                    itemParent.setOrientation(LinearLayout.HORIZONTAL);
                    itemParent.setGravity(mGravity);
                    int itemNum = mNum + i;
                    for (int j = i, num = itemNum > size ? size : itemNum; j < num; j++) {
                        BaseViewItemHolder holder = OnCreateViewHolder(mViewGroup);
                        OnBindViewHolder(holder, j);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.width = MetricsUtils.getWidth(mContext, mOffset, mNum);
                        itemParent.addView(holder.mView, params);
                    }
                    linearLayout.addView(itemParent);
                }

            } else {
                for (int i = 0, size = getCount(); i < size; i++) {
                    BaseViewItemHolder holder = OnCreateViewHolder(mViewGroup);
                    OnBindViewHolder(holder, i);
                    if (isResetWidth) {
                        LinearLayout itemParent = new LinearLayout(mContext);
                        itemParent.setOrientation(LinearLayout.HORIZONTAL);
                        itemParent.setGravity(mGravity);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.width = MetricsUtils.getWidth(mContext, mOffset, mNum);
                        itemParent.addView(holder.mView, params);
                        linearLayout.addView(itemParent);
                    } else {
                        linearLayout.addView(holder.mView);
                    }
                }
            }
        }
    }

    public int getCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void setOnViewSelectedListener(OnViewSelectedListener onViewSelectedListener) {
        this.mOnViewSelectedListener = onViewSelectedListener;
    }

    public abstract BaseViewItemHolder OnCreateViewHolder(ViewGroup parent);

    public void OnBindViewHolder(final BaseViewItemHolder holder, int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnViewSelectedListener == null) return;
                if (mSelectorHolder != null && mSelectorHolder == holder) {
                    mOnViewSelectedListener.onViewReselected(holder);
                } else {
                    mOnViewSelectedListener.onViewSelected(holder);
                    if (mSelectorHolder != null) {
                        mOnViewSelectedListener.onViewUnselected(mSelectorHolder);
                    }
                    mSelectorHolder = holder;
                }
            }
        });

        holder.setPosition(position);
        holder.setData(getItem(position));

    }

    public BaseViewItemHolder getSelectorHolder() {
        return mSelectorHolder;
    }

    public void setSelectorHolder(BaseViewItemHolder selectorHolder) {
        this.mSelectorHolder = selectorHolder;
    }

    public interface OnViewSelectedListener {
        void onViewSelected(BaseViewItemHolder holder);

        void onViewUnselected(BaseViewItemHolder holder);

        void onViewReselected(BaseViewItemHolder holder);
    }
}
