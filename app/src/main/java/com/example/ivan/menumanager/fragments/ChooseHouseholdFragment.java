package com.example.ivan.menumanager.fragments;


import android.app.Dialog;
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


    Button okButton;
    Button cancelButton;
    Button addHouseholdButton;
    EditText newHouseholdEditText;
    View animatedViewGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Households");
        final View dialog = inflater.inflate(R.layout.dialog_choose_household, container, false);

        RecyclerView householdList = (RecyclerView) dialog.findViewById(R.id.choose_household_recyclerview);
        HouseholdRecyclerAdapter adapter = new HouseholdRecyclerAdapter(getContext());
        householdList.setAdapter(adapter);
        householdList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));

        newHouseholdEditText = (EditText) dialog.findViewById(R.id.add_household_edit);

        okButton = (Button) dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = ChooseHouseholdFragment.this.getContext();
                String newHouseholdText = newHouseholdEditText.getText().toString();
                Intent intent = new Intent(context, ViewPageActivity.class);
                intent.putExtra("household name", newHouseholdText);
                if(!newHouseholdText.isEmpty()){
                    if(MainActivity.households.containsKey(newHouseholdText)){
                        Toast.makeText(context, "Household name already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DBManager.getInstance(context).addHousehold(newHouseholdText);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Household name must not be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        animatedViewGroup = dialog.findViewById(R.id.animated_dropdown_views);
        addHouseholdButton = (Button) dialog.findViewById(R.id.add_household_button);
        addHouseholdButton.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View v) {
                Transition transition = new AutoTransition();
                transition.setDuration(200);
                TransitionManager.beginDelayedTransition((ViewGroup) animatedViewGroup, transition);
                visible = !visible;
                animatedViewGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        return dialog;
    }

}
