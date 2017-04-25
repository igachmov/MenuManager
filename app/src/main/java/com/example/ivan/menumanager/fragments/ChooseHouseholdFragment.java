package com.example.ivan.menumanager.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivan.menumanager.MainActivity;
import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.adapters.HouseholdRecyclerAdapter;
import com.example.ivan.menumanager.model.DBManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseHouseholdFragment extends DialogFragment {

    private Button addButton;
    private Button cancelButton;
    private Button addHouseholdButton;
    private EditText newHouseholdEditText;
    private View animatedViewGroup;
    private RecyclerView chooseHousehold;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        final View dialog = inflater.inflate(R.layout.dialog_choose_household, container, false);

        RecyclerView householdList = (RecyclerView) dialog.findViewById(R.id.choose_household_recyclerview);
        HouseholdRecyclerAdapter adapter = new HouseholdRecyclerAdapter(getActivity());
        householdList.setAdapter(adapter);
        householdList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));

        newHouseholdEditText = (EditText) dialog.findViewById(R.id.add_household_edit);

        addButton = (Button) dialog.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = ChooseHouseholdFragment.this.getActivity();
                String newHouseholdText = newHouseholdEditText.getText().toString();
                Intent intent = new Intent(activity, ViewPageActivity.class);
                if (!newHouseholdText.isEmpty()) {
                    if (DBManager.households.containsKey(newHouseholdText)) {
                        Toast.makeText(activity, "Household name already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DBManager.getInstance(activity).addHousehold(newHouseholdText);
                    DBManager.currentHousehold = newHouseholdText;
                    activity.startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(activity, "Household name must not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        animatedViewGroup = dialog.findViewById(R.id.animated_dropdown_views);
        chooseHousehold = (RecyclerView) dialog.findViewById(R.id.choose_household_recyclerview);

        addHouseholdButton = (Button) dialog.findViewById(R.id.add_household_button);
        addHouseholdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatedViewGroup.setVisibility(View.VISIBLE);
                chooseHousehold.setVisibility(View.GONE);
            }
        });

        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newHouseholdEditText.setText("");
                animatedViewGroup.setVisibility(View.GONE);
                chooseHousehold.setVisibility(View.VISIBLE);
                dismiss();
            }
        });
        return dialog;
    }

}
