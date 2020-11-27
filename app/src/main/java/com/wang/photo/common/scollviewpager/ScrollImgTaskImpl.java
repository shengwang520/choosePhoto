package com.wang.photo.common.scollviewpager;

import android.os.Handler;

import androidx.viewpager.widget.ViewPager;

/**
 * viewpager 自动滚动
 * Created by wang on 2015/10/19.
 */
public class ScrollImgTaskImpl {
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final int mScrollDuration = 2000;//动画滚动时间
    private final ViewPagerScroller scroller;
    private ViewPager viewPager;
    private int count;
    private Handler h = new Handler();
    private Runnable animateViewPager = new Runnable() {

        public void run() {
            if (viewPager.getAdapter() == null) return;
            count = viewPager.getAdapter().getCount();
            if (count > 0) {
                int position = (viewPager.getCurrentItem() + 1)
                        % count;
//                Logger.d("当前位置"+viewPager.getCurrentItem()+"滚动的位置--》" + position);
                if (position == 0) {
                    scroller.setScrollDuration(0);
                } else {
                    scroller.setScrollDuration(mScrollDuration);
                }
                viewPager.setCurrentItem(position, true);
                h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
            }
        }
    };

    public ScrollImgTaskImpl(ViewPager viewPager) {
        this.viewPager = viewPager;
        scroller = new ViewPagerScroller(viewPager.getContext());
        scroller.initViewPagerScroll(viewPager);
    }

    /**
     * 暂停
     */
    public void onPause() {
        if (h != null) {
            h.removeCallbacks(animateViewPager);
        }
    }

    /**
     * 开始
     */
    public void onResume() {
        h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
    }

}
