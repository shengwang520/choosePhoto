package com.common.app.common.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * tab fragment
 * Created by 圣王 on 2015/4/24 0024.
 */
public class SimpleTabViewAdapter extends BaseTabAdapter {

    private Fragment[] fragments;

    public SimpleTabViewAdapter(FragmentManager fm, Fragment[] fragments) {
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
