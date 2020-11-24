package com.common.app.common.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * 简单fragment适配器
 * Created by 圣王 on 2015/4/24 0024.
 */
public class SimplePageViewAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments;

    public SimplePageViewAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}
