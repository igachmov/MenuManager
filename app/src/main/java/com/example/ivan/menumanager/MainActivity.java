package com.example.ivan.menumanager;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ivan.menumanager.fragments.ChooseHouseholdFragment;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Household;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView chooseHouseholdView;
    private TextView magicView;
    public static HashMap<String, Household> households = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager.getInstance(this);
        chooseHouseholdView = (TextView) findViewById(R.id.choose_household);
        magicView = (TextView) findViewById(R.id.magic_view);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseHouseholdFragment chooseDialog = new ChooseHouseholdFragment();
                FragmentManager fm = getSupportFragmentManager();
                chooseDialog.show(fm, "chooseHousehold");
            }
        };
        chooseHouseholdView.setOnClickListener(listener);
        magicView.setOnClickListener(listener);

    }
}
