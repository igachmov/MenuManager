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
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private RecycleViewFragment recycleViewFragment;
    private String fragments[] = {"Fridge","Shopping List","Recipe"};

    public ViewPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        recycleViewFragment =  new RecycleViewFragment();
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragment1;
            case 1:
                return fragment2;
            case 2:
                return fragment3;
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

    public RecycleViewFragment getRecycleViewFragment() {
        return recycleViewFragment;
    }
}
