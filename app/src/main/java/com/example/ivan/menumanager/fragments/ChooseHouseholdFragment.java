package com.example.ivan.menumanager.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseHouseholdFragment extends DialogFragment {


    public ChooseHouseholdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.fragment_choose_household, container, false);

        TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = ChooseHouseholdFragment.this.getContext();
                Intent intent = new Intent(context, ViewPageActivity.class);
                context.startActivity(intent);
            }
        });
        return dialog;
    }

}
