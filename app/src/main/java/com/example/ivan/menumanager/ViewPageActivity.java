package com.example.ivan.menumanager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ivan.menumanager.household.ProductsAdapter;
import com.example.ivan.menumanager.household.ChooseItemFragment;
import com.example.ivan.menumanager.household.ProductsFragment;
import com.example.ivan.menumanager.model.DBManager;

public class ViewPageActivity extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ViewPageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPageAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                ProductsFragment productsFragment = (ProductsFragment) adapter.getItem(tabLayout.getSelectedTabPosition());
                productsFragment.getCatagoryLayout().setVisibility(View.VISIBLE);
                productsFragment.getFridgeLayout().setVisibility(View.GONE);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(DBManager.currentHousehold);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_viewpager_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_course:
                //TODO
                return true;
            case R.id.menu_households:
                ChooseItemFragment chooseItem = new ChooseItemFragment();
                Bundle bundle = new Bundle();
                bundle.putString("callingObject", "menuHouseholds");
                chooseItem.setArguments(bundle);
                chooseItem.show(getSupportFragmentManager(), "chooseItem");
                return true;
            case R.id.menu_favourite_recipes:
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }








//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_main_course:
//                //TODO
//                return true;
//            case R.id.menu_households:
//                ChooseItemFragment chooseItem = new ChooseItemFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("callingObject", "menuHouseholds");
//                chooseItem.setArguments(bundle);
//                chooseItem.show(getSupportFragmentManager(), "chooseItem");
//                return true;
//            case R.id.menu_favourite_recipes:
//                //TODO
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
