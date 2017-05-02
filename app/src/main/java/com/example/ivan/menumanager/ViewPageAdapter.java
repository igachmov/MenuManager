package com.example.ivan.menumanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ivan.menumanager.household.ProductsFragment;
import com.example.ivan.menumanager.shopping_list.ShoppingFragment;
import com.example.ivan.menumanager.recipe.RecipesFragment;

import java.util.ArrayList;

/**
 * Created by Ivan on 4/6/2017.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ProductsFragment productsFragment;
    private ShoppingFragment shoppingFragment;
    private RecipesFragment recipesFragment;

    private String fragments[] = {"Products","Shopping","Recipes"};

    public ViewPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
        productsFragment = new ProductsFragment();
        shoppingFragment = new ShoppingFragment();
        recipesFragment = new RecipesFragment();
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return productsFragment;
            case 1:
                return shoppingFragment;
            case 2:
                return recipesFragment;
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
