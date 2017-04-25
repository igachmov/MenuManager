package com.example.ivan.menumanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;
import com.example.ivan.menumanager.fragments.ChooseHouseholdFragment;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Household;

public class MainActivity extends AppCompatActivity {

    private RippleView chooseHouseholdView;
    private RippleView magicView;
    View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DBManager db = DBManager.getInstance(this);
        chooseHouseholdView = (RippleView) findViewById(R.id.choose_household);
        magicView = (RippleView) findViewById(R.id.magic_view);

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleView view = (RippleView) v;
                view.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                    @Override
                    public void onComplete(RippleView rippleView) {
                        ChooseHouseholdFragment chooseDialog = new ChooseHouseholdFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        chooseDialog.show(fm, "chooseHousehold");
                    }
                });
            }
        };
        chooseHouseholdView.setOnClickListener(listener);
        magicView.setOnClickListener(listener);



    }
}
