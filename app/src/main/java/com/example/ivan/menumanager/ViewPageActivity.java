package com.example.ivan.menumanager;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ivan.menumanager.adapters.ViewPageAdapter;
import com.example.ivan.menumanager.fragments.ChooseHouseholdFragment;
import com.example.ivan.menumanager.fragments.ProductsFragment;

public class ViewPageActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    ViewPageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPageAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(adapter);
        
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String householdName = getIntent().getStringExtra("Household name");
        if(householdName != null && !householdName.isEmpty()){
            toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            toolbarTitle.setText(householdName);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_main_course:
                //TODO
                return true;
            case R.id.menu_households:
                ChooseHouseholdFragment chooseHousehold = new ChooseHouseholdFragment();
                chooseHousehold.show(getSupportFragmentManager(), "chooseHousehold");
                return true;
            case R.id.menu_explore_fridge:
                viewPager.setCurrentItem(0);
                ProductsFragment productsFragment = (ProductsFragment) adapter.getItem(tabLayout.getSelectedTabPosition());
                productsFragment.getProductLayout().setVisibility(View.VISIBLE);
                productsFragment.getFridgeLayout().setVisibility(View.GONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    }
}
