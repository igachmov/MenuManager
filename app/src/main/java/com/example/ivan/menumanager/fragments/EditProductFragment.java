package com.example.ivan.menumanager.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ivan.menumanager.MainActivity;
import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.adapters.PredefinedProductsAdapter;
import com.example.ivan.menumanager.adapters.ProductsFridgeAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProductFragment extends DialogFragment {

    private TextView okButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View dialog = inflater.inflate(R.layout.edit_product, container, false);
        okButton = (TextView) dialog.findViewById(R.id.new_product_text);
        TextView text = ProductsFridgeAdapter.chooseDialog.getItemEditText();
        okButton.setText(text.getText().toString());

        return dialog;
    }

}
