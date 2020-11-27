package com.wang.photo.common.adapter;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;


/**
 * tab和fragment切换
 * Created by wang on 2016/7/27.
 */
public class BaseFragmentTabSelectedListener implements TabLayout.OnTabSelectedListener {

    private ViewGroup container;
    private BaseTabAdapter adapter;
    private AppCompatCheckedTextView[] checkables;

    public BaseFragmentTabSelectedListener(ViewGroup container, AppCompatCheckedTextView[] checkables, BaseTabAdapter adapter) {
        this.adapter = adapter;
        this.container = container;
        this.checkables = checkables;
    }

    public BaseFragmentTabSelectedListener(ViewGroup container, BaseTabAdapter adapter) {
        this.adapter = adapter;
        this.container = container;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        Fragment fragment = (Fragment) adapter.instantiateItem(container, position);
        adapter.setPrimaryItem(container, position, fragment);
        adapter.finishUpdate(container);

        if (checkables != null) {
            checkables[tab.getPosition()].setChecked(true);
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (checkables != null) {
            checkables[tab.getPosition()].setChecked(false);
        }

        int position = tab.getPosition();
        Fragment fragment = (Fragment) adapter.instantiateItem(container, position);
        adapter.destroyItem(container, position, fragment);
        adapter.finishUpdate(container);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (checkables != null) {
            checkables[tab.getPosition()].setChecked(true);
        }

        int position = tab.getPosition();
        Fragment fragment = (Fragment) adapter.instantiateItem(container, position);
        adapter.setPrimaryItem(container, position, fragment);
        adapter.finishUpdate(container);
    }

}
