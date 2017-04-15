package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ivan.menumanager.fragments.ProductsFragment;
import com.example.ivan.menumanager.fragments.ShoppingFragment;
import com.example.ivan.menumanager.fragments.RecipesFragment;

/**
 * Created by Ivan on 4/6/2017.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    private Context context;
    private ProductsFragment fragment1;
    private ShoppingFragment fragment2;
    private RecipesFragment fragment3;

    private String fragments[] = {"Products","Shopping","Recipe"};

    public ViewPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
        fragment1 = new ProductsFragment();
        fragment2 = new ShoppingFragment();

        fragment3 = new RecipesFragment();
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

}
