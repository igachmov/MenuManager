package com.example.ivan.menumanager.household;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseItemFragment extends DialogFragment {

    private Button okButton;
    private Button cancelButton;
    private Button addItemButton;
    private Button cancelHouseholds;
    private EditText itemEditText;
    private View animatedView;
    private RecyclerView chooseItem;
    private RecyclerView itemList;

    String callingObject;
    ArrayList<String> items;
    String itemType;
    ChooseItemAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View dialog = inflater.inflate(R.layout.dialog_choose, container, false);
        itemList = (RecyclerView) dialog.findViewById(R.id.choose_item_recyclerview);
        itemEditText = (EditText) dialog.findViewById(R.id.item_edit);
        okButton = (Button) dialog.findViewById(R.id.ok_button);
        animatedView = dialog.findViewById(R.id.animated_dropdown_views);
        chooseItem = (RecyclerView) dialog.findViewById(R.id.choose_item_recyclerview);
        addItemButton = (Button) dialog.findViewById(R.id.add_item);
        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        cancelHouseholds = (Button) dialog.findViewById(R.id.cancel_households);

        if (animatedView.getVisibility() == View.VISIBLE) {
            animatedView.setVisibility(View.GONE);
            chooseItem.setVisibility(View.VISIBLE);
            cancelHouseholds.setVisibility(View.VISIBLE);
        }
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
       // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorWhite_transparent)));
        fillData(getDialog());


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (callingObject) {
                    case "mainActivity":
                    case "menuHouseholds":
                        String newHouseholdName = itemEditText.getText().toString();
                        Intent intent = new Intent(getActivity(), ViewPageActivity.class);
                        if (!newHouseholdName.isEmpty()) {
                            if (DBManager.households.containsKey(newHouseholdName)) {
                                Toast.makeText(getActivity(), "Household name already exists", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            DBManager.getInstance(getActivity()).addHousehold(newHouseholdName);
                            DBManager.currentHousehold = newHouseholdName;
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Household name must not be empty", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "fridge":
                    case "floatingButton":
                    case "dairy":
                    case "bread":
                    case "dressing":
                    case "bakery":
                    case "veggies":
                    case "sauce":
                    case "fruits":
                    case "meat":
                        String newProductName = itemEditText.getText().toString();
                        if (!newProductName.isEmpty()) {
                            EditProductFragment editDialog = new EditProductFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("product", newProductName);
                            editDialog.setArguments(bundle);
                            editDialog.show(getActivity().getSupportFragmentManager(), "editItem");
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Product name must not be empty", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatedView.getVisibility() == View.GONE) {
                    animatedView.setVisibility(View.VISIBLE);
                    chooseItem.setVisibility(View.GONE);
                    cancelHouseholds.setVisibility(View.GONE);
                } else {
                    animatedView.setVisibility(View.GONE);
                    chooseItem.setVisibility(View.VISIBLE);
                    cancelHouseholds.setVisibility(View.VISIBLE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEditText.setText("");
                animatedView.setVisibility(View.GONE);
                chooseItem.setVisibility(View.VISIBLE);
                dismiss();
            }
        });

        cancelHouseholds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    private void fillData(Dialog dialog) {
        items = new ArrayList<>();
        if (getArguments() != null) {
            callingObject = getArguments().getString("callingObject");
            switch (callingObject) {
                case "mainActivity":
                case "menuHouseholds":
                    addItemButton.setText("Add household");
                    itemEditText.setHint("enter household");
                    for (String householdName : DBManager.households.keySet()) {
                        items.add(householdName);
                    }
                    itemType = "household";
                    break;

                case "food category":
                case "floatingButton":
                    addItemButton.setText("Add product");
                    itemEditText.setHint("enter product");
                    for (String productName : DBManager.predefinedProducts.keySet()) {
                        items.add(productName);
                    }
                    itemType = "product";
                    break;

                case "dairy":
                case "bakery":
                case "dressing":
                case "veggies":
                case "sauce":
                case "fruits":
                case "meat":
                case "grain":
                    Log.e("Vanya", "product - ");
                    addItemButton.setText("Add product");
                    itemEditText.setHint("enter product");
                    int categoryID = 0;
                    for(int i = 1; i < DBManager.predefinedCategories.size(); i++){
                        if(DBManager.predefinedCategories.get(i).getName().equals(callingObject)){
                            categoryID = i;
                        }
                    }
                    for (String productName : DBManager.predefinedProducts.keySet()) {
                       if(DBManager.predefinedProducts.get(productName).getFoodCategoryID() == categoryID){
                           items.add(productName);
                       }
                    }
                    itemType = "product";
                    break;
            }
            adapter = new ChooseItemAdapter(getActivity(), items, itemType);
            itemList.setAdapter(adapter);
            itemList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }





//
//    private Button okButton;
//    private Button cancelButton;
//    private Button addItemButton;
//    private Button cancelHouseholds;
//    private EditText itemEditText;
//    private View animatedView;
//    private RecyclerView chooseItem;
//    private RecyclerView itemList;
//
//    String callingObject;
//    ArrayList<String> items;
//    String itemType;
//    ChooseItemAdapter adapter;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        final View dialog = inflater.inflate(R.layout.dialog_choose, container, false);
//        itemList = (RecyclerView) dialog.findViewById(R.id.choose_item_recyclerview);
//        itemEditText = (EditText) dialog.findViewById(R.id.item_edit);
//        okButton = (Button) dialog.findViewById(R.id.ok_button);
//        animatedView = dialog.findViewById(R.id.animated_dropdown_views);
//        chooseItem = (RecyclerView) dialog.findViewById(R.id.choose_item_recyclerview);
//        addItemButton = (Button) dialog.findViewById(R.id.add_item);
//        cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
//        cancelHouseholds = (Button) dialog.findViewById(R.id.cancel_households);
//
//        if (animatedView.getVisibility() == View.VISIBLE) {
//            animatedView.setVisibility(View.GONE);
//            chooseItem.setVisibility(View.VISIBLE);
//            cancelHouseholds.setVisibility(View.VISIBLE);
//        }
//        getDialog().requestWindowFeature(STYLE_NO_TITLE);
//        // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorWhite_transparent)));
//        fillData(getDialog());
//
//
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (callingObject) {
//                    case "mainActivity":
//                    case "menuHouseholds":
//                        String newHouseholdName = itemEditText.getText().toString();
//                        Intent intent = new Intent(getActivity(), ViewPageActivity.class);
//                        if (!newHouseholdName.isEmpty()) {
//                            if (DBManager.households.containsKey(newHouseholdName)) {
//                                Toast.makeText(getActivity(), "Household name already exists", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            DBManager.getInstance(getActivity()).addHousehold(newHouseholdName);
//                            DBManager.currentHousehold = newHouseholdName;
//                            getActivity().startActivity(intent);
//                            getActivity().finish();
//                        } else {
//                            Toast.makeText(getActivity(), "Household name must not be empty", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//
//                    case "fridge":
//                    case "floatingButton":
//                    case "dairy":
//                    case "bread":
//                    case "dressing":
//                    case "bakery":
//                    case "veggies":
//                    case "sauce":
//                    case "fruits":
//                    case "meat":
//                        String newProductName = itemEditText.getText().toString();
//                        if (!newProductName.isEmpty()) {
//                            EditProductFragment editDialog = new EditProductFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("product", newProductName);
//                            editDialog.setArguments(bundle);
//                            editDialog.show(getActivity().getSupportFragmentManager(), "editItem");
//                            dismiss();
//                        } else {
//                            Toast.makeText(getActivity(), "Product name must not be empty", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                }
//            }
//        });
//
//        addItemButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (animatedView.getVisibility() == View.GONE) {
//                    animatedView.setVisibility(View.VISIBLE);
//                    chooseItem.setVisibility(View.GONE);
//                    cancelHouseholds.setVisibility(View.GONE);
//                } else {
//                    animatedView.setVisibility(View.GONE);
//                    chooseItem.setVisibility(View.VISIBLE);
//                    cancelHouseholds.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemEditText.setText("");
//                animatedView.setVisibility(View.GONE);
//                chooseItem.setVisibility(View.VISIBLE);
//                dismiss();
//            }
//        });
//
//        cancelHouseholds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        return dialog;
//    }
//
//    private void fillData(Dialog dialog) {
//        items = new ArrayList<>();
//        if (getArguments() != null) {
//            callingObject = getArguments().getString("callingObject");
//            switch (callingObject) {
//                case "mainActivity":
//                case "menuHouseholds":
//                    addItemButton.setText("Add household");
//                    itemEditText.setHint("enter household");
//                    for (String householdName : DBManager.households.keySet()) {
//                        items.add(householdName);
//                    }
//                    itemType = "household";
//                    break;
//
//                case "food category":
//                case "floatingButton":
//                    addItemButton.setText("Add product");
//                    itemEditText.setHint("enter product");
//                    for (String productName : DBManager.predefinedProducts.keySet()) {
//                        items.add(productName);
//                    }
//                    itemType = "product";
//                    break;
//
//                case "dairy":
//                case "bakery":
//                case "dressing":
//                case "veggies":
//                case "sauce":
//                case "fruits":
//                case "meat":
//                case "grain":
//                    Log.e("Vanya", "product - ");
//                    addItemButton.setText("Add product");
//                    itemEditText.setHint("enter product");
//                    int categoryID = 0;
//                    for(int i = 1; i < DBManager.predefinedCategories.size(); i++){
//                        if(DBManager.predefinedCategories.get(i).getName().equals(callingObject)){
//                            categoryID = i;
//                        }
//                    }
//                    for (String productName : DBManager.predefinedProducts.keySet()) {
//                        if(DBManager.predefinedProducts.get(productName).getFoodCategoryID() == categoryID){
//                            items.add(productName);
//                        }
//                    }
//                    itemType = "product";
//                    break;
//            }
//            adapter = new ChooseItemAdapter(getActivity(), items, itemType);
//            itemList.setAdapter(adapter);
//            itemList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
//        }
//    }
}
