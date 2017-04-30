package com.example.ivan.menumanager.household;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.household.EditProductFragment;
import com.example.ivan.menumanager.household.HouseholdAdapter;
import com.example.ivan.menumanager.household.PredefinedProductsAdapter;
import com.example.ivan.menumanager.household.ProductsFridgeAdapter;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseFragment extends DialogFragment {

    private Button okButton;
    private Button cancelButton;
    private Button addItemButton;
    private EditText itemEditText;
    private View animatedView;
    private RecyclerView chooseItem;
    private RecyclerView itemList;


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

        if (animatedView.getVisibility() == View.VISIBLE) {
            animatedView.setVisibility(View.GONE);
            chooseItem.setVisibility(View.VISIBLE);
        }
        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        //choosing adapter to set upon calling subjects to show
        //in case another fragment is calling we set arguments
        //in case Main activity or menu item arguments == null

        if(getActivity().getLocalClassName().equals("MainActivity")) {
            addItemButton.setText("Add household");
            itemEditText.setHint("enter household");
            HouseholdAdapter adapterHh = new HouseholdAdapter(getActivity());
            itemList.setAdapter(adapterHh);
            itemList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
        }
        else {
            addItemButton.setText("Add product");
            itemEditText.setHint("enter product");

            ArrayList<Product> predefinedProd = new ArrayList<>();
            for(Product product:DBManager.predefinedProducts.values()){
                predefinedProd.add(product);
            }
            PredefinedProductsAdapter adapterPp = new PredefinedProductsAdapter(getActivity(), (ProductsFridgeAdapter.ICommunicator) getActivity(), predefinedProd);
            itemList.setAdapter(adapterPp);
            itemList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
        }

        //setting different actions
        //in case another fragment is calling we set arguments
        //in case Main activity or menu item - arguments == null
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getLocalClassName().equals("MainActivity")) {
                    //insert new household in database
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
                } else {
                    //take me to edit fields
                    String newProductName = itemEditText.getText().toString();
                    if(!newProductName.isEmpty()){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        EditProductFragment editDialog = new EditProductFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("product", newProductName);
                        editDialog.setArguments(bundle);
                        editDialog.show(fm, "editItem");
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Product name must not be empty", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatedView.getVisibility() == View.GONE) {
                    animatedView.setVisibility(View.VISIBLE);
                    chooseItem.setVisibility(View.GONE);
                } else {
                    animatedView.setVisibility(View.GONE);
                    chooseItem.setVisibility(View.VISIBLE);
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

        return dialog;
    }

   // protected abstract void fillData();


    public TextView getItemEditText(){
        return this.itemEditText;
    }

}
