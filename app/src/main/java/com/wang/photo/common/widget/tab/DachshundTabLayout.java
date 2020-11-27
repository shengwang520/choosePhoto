package com.wang.photo.common.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.common.app.R;
import com.wang.photo.common.widget.tab.indicators.AnimatedIndicatorInterface;
import com.wang.photo.common.widget.tab.indicators.AnimatedIndicatorType;
import com.wang.photo.common.widget.tab.indicators.DachshundIndicator;
import com.wang.photo.common.widget.tab.indicators.LineFadeIndicator;
import com.wang.photo.common.widget.tab.indicators.LineMoveIndicator;
import com.wang.photo.common.widget.tab.indicators.PointFadeIndicator;
import com.wang.photo.common.widget.tab.indicators.PointMoveIndicator;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by Andy671
 */

public class DachshundTabLayout extends TabLayout implements ViewPager.OnPageChangeListener {

    private static final int DEFAULT_HEIGHT_DP = 6;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private int mCurrentPosition;
    private boolean mCenterAlign;
    private LinearLayout mTabStrip;
    private AnimatedIndicatorType mAnimatedIndicatorType;
    private AnimatedIndicatorInterface mAnimatedIndicator;
    private int mTempPosition, mTempPositionOffsetPixels;
    private float mTempPositionOffset;

    private AppCompatCheckedTextView[] appCompatCheckedTextViews;

    public DachshundTabLayout(Context context) {
        this(context, null);
    }

    public DachshundTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DachshundTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        super.setSelectedTabIndicatorHeight(0);

        mTabStrip = (LinearLayout) super.getChildAt(0);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DachshundTabLayout);

        this.mIndicatorHeight = ta.getDimensionPixelSize(R.styleable.DachshundTabLayout_ddIndicatorHeight, HelperUtils.dpToPx(DEFAULT_HEIGHT_DP));
        this.mIndicatorColor = ta.getColor(R.styleable.DachshundTabLayout_ddIndicatorColor, Color.WHITE);
        this.mCenterAlign = ta.getBoolean(R.styleable.DachshundTabLayout_ddCenterAlign, false);
        this.mAnimatedIndicatorType = AnimatedIndicatorType.values()[ta.getInt(R.styleable.DachshundTabLayout_ddAnimatedIndicator, 0)];

        ta.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mCenterAlign) {
            View firstTab = ((ViewGroup) getChildAt(0)).getChildAt(0);
            View lastTab = ((ViewGroup) getChildAt(0)).getChildAt(((ViewGroup) getChildAt(0)).getChildCount() - 1);
            ViewCompat.setPaddingRelative(getChildAt(0),
                    (getWidth() / 2) - (firstTab.getWidth() / 2),
                    0,
                    (getWidth() / 2) - (lastTab.getWidth() / 2),
                    0);
        }

        if (mAnimatedIndicator == null) {
            setupAnimatedIndicator();
        }

        onPageScrolled(mTempPosition, mTempPositionOffset, mTempPositionOffsetPixels);
    }

    private void setupAnimatedIndicator() {
        switch (mAnimatedIndicatorType) {
            case DACHSHUND:
                setAnimatedIndicator(new DachshundIndicator(this));
                break;
            case POINT_MOVE:
                setAnimatedIndicator(new PointMoveIndicator(this));
                break;
            case LINE_MOVE:
                setAnimatedIndicator(new LineMoveIndicator(this));
                break;
            case POINT_FADE:
                setAnimatedIndicator(new PointFadeIndicator(this));
                break;
            case LINE_FADE:
                setAnimatedIndicator(new LineFadeIndicator(this));
                break;
        }
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        this.mIndicatorColor = color;
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator.setSelectedTabIndicatorColor(color);

            invalidate();
        }
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator.setSelectedTabIndicatorHeight(height);

            invalidate();
        }

    }

    public void setCenterAlign(boolean centerAlign) {
        this.mCenterAlign = centerAlign;

        requestLayout();
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    @Override
    public void setupWithViewPager(@Nullable final ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
            viewPager.addOnPageChangeListener(this);
        }
    }

    /**
     * 绑定viewpage
     */
    public void addWithViewPager(@Nullable ViewPager viewPager) {
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this) {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    startScrollIndicator(position, positionOffset, positionOffsetPixels);
                    startChangeTextSize(position, positionOffset, positionOffsetPixels);
                }
            });
        }
    }

    /**
     * 绑定viewpage
     */
    public void addWithViewPager(@Nullable ViewPager viewPager, AppCompatCheckedTextView[] appCompatCheckedTextViews) {
        this.appCompatCheckedTextViews = appCompatCheckedTextViews;
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this) {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    startScrollIndicator(position, positionOffset, positionOffsetPixels);
                    startChangeTextSize(position, positionOffset, positionOffsetPixels);
                }
            });
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mAnimatedIndicator != null)
            mAnimatedIndicator.draw(canvas);

    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
                               final int positionOffsetPixels) {
        startScrollIndicator(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(final int position) {
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public float getChildXLeft(int position) {
        if (mTabStrip.getChildAt(position) != null)
            return (mTabStrip.getChildAt(position).getX());
        else
            return 0;
    }

    public float getChildXCenter(int position) {
        if (mTabStrip.getChildAt(position) != null)
            return (mTabStrip.getChildAt(position).getX() + mTabStrip.getChildAt(position).getWidth() / 2);
        else
            return 0;
    }

    public float getChildXRight(int position) {
        if (mTabStrip.getChildAt(position) != null)
            return (mTabStrip.getChildAt(position).getX() + mTabStrip.getChildAt(position).getWidth());
        else
            return 0;
    }

    public AnimatedIndicatorInterface getAnimatedIndicator() {
        return mAnimatedIndicator;
    }

    public void setAnimatedIndicator(AnimatedIndicatorInterface animatedIndicator) {
        this.mAnimatedIndicator = animatedIndicator;

        animatedIndicator.setSelectedTabIndicatorColor(mIndicatorColor);
        animatedIndicator.setSelectedTabIndicatorHeight(mIndicatorHeight);

        invalidate();
    }

    /**
     * 动态改变文字大小
     */
    private void startChangeTextSize(int position, float positionOffset, int positionOffsetPixels) {
        Logger.d("viewPager scrooll position:" + position + "-->positionOffset:" + positionOffset + "-->positionOffsetPixels:" + positionOffsetPixels + "|m:" + mCurrentPosition);
        if (appCompatCheckedTextViews == null) return;
        if (position == appCompatCheckedTextViews.length - 1) return;
        AppCompatCheckedTextView tvLeft = appCompatCheckedTextViews[position];
        AppCompatCheckedTextView tvRight = appCompatCheckedTextViews[position + 1];

        float leftSize = getLeftSize(positionOffset);
        float rightSize = getRightSize(leftSize);
        tvLeft.setTextSize(rightSize);
        tvRight.setTextSize(leftSize);
        if (leftSize >= 19) {
            tvRight.setTypeface(Typeface.DEFAULT_BOLD);
            tvLeft.setTypeface(Typeface.DEFAULT);
        } else {
            tvLeft.setTypeface(Typeface.DEFAULT_BOLD);
            tvRight.setTypeface(Typeface.DEFAULT);
        }
    }

    /**
     * 获取左边文字大小
     */
    private float getLeftSize(float positionOffset) {
        float size = (24 - 15) * positionOffset + 15;
        Logger.d("viewPager size max:" + size);
        return size;
    }

    /**
     * 获取右边文字大小
     */
    private float getRightSize(float leftSize) {
        float size = 24 + 15 - leftSize;
        Logger.d("viewPager size min:" + size);
        return size;
    }


    /**
     * 滚动下划线
     */
    private void startScrollIndicator(int position, float positionOffset, int positionOffsetPixels) {
        this.mTempPosition = position;
        this.mTempPositionOffset = positionOffset;
        this.mTempPositionOffsetPixels = positionOffsetPixels;

        if ((position > mCurrentPosition) || (position + 1 < mCurrentPosition)) {
            mCurrentPosition = position;
        }

        int mStartXLeft, mStartXCenter, mStartXRight, mEndXLeft, mEndXCenter, mEndXRight;

        if (position != mCurrentPosition) {

            mStartXLeft = (int) getChildXLeft(mCurrentPosition);
            mStartXCenter = (int) getChildXCenter(mCurrentPosition);
            mStartXRight = (int) getChildXRight(mCurrentPosition);

            mEndXLeft = (int) getChildXLeft(position);
            mEndXRight = (int) getChildXRight(position);
            mEndXCenter = (int) getChildXCenter(position);

            if (mAnimatedIndicator != null) {
                mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft,
                        mStartXCenter, mEndXCenter,
                        mStartXRight, mEndXRight);
                mAnimatedIndicator.setCurrentPlayTime((long) ((1 - positionOffset) * (int) mAnimatedIndicator.getDuration()));
            }

        } else {

            mStartXLeft = (int) getChildXLeft(mCurrentPosition);
            mStartXCenter = (int) getChildXCenter(mCurrentPosition);
            mStartXRight = (int) getChildXRight(mCurrentPosition);

            if (mTabStrip.getChildAt(position + 1) != null) {

                mEndXLeft = (int) getChildXLeft(position + 1);
                mEndXCenter = (int) getChildXCenter(position + 1);
                mEndXRight = (int) getChildXRight(position + 1);

            } else {
                mEndXLeft = (int) getChildXLeft(position);
                mEndXCenter = (int) getChildXCenter(position);
                mEndXRight = (int) getChildXRight(position);
            }

            if (mAnimatedIndicator != null) {
                mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft,
                        mStartXCenter, mEndXCenter,
                        mStartXRight, mEndXRight);
                mAnimatedIndicator.setCurrentPlayTime((long) (positionOffset * (int) mAnimatedIndicator.getDuration()));
            }

        }

        if (positionOffset == 0) {
            mCurrentPosition = position;
        }

    }

}
