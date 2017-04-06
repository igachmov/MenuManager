package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ivan.menumanager.fragments.Fragment1;
import com.example.ivan.menumanager.fragments.Fragment2;
import com.example.ivan.menumanager.fragments.Fragment3;

/**
 * Created by Ivan on 4/6/2017.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    private Context context;
    private String fragments[] = {"Fragment1","Fragment2","Fragment3"};

    public ViewPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
