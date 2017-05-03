package com.example.ivan.menumanager.household;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProductFragment extends DialogFragment {

    private TextView newProduct;
    private EditText quantityEditText;
    private Spinner measureSpinner;
    private Spinner categorySpinner;
    private Spinner expirySpinner;
    private Button remove;
    private Button add;
    private Button cancel;
    private String name;
    private double quantityArgs;
    private int measureArgs;
    private int categoryArgs;
    private int expiryTermArgs;
    private double quantity;
    private int measureID;
    private int categoryID;
    private int expiryTermID;

    ViewPageActivity activity;
    ProductsFragment productsFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            quantityArgs = getArguments().getDouble("quantity");
            measureArgs = getArguments().getInt("measureID");
            categoryArgs = getArguments().getInt("categoryID");
            expiryTermArgs = getArguments().getInt("expiryTermID");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View dialog = inflater.inflate(R.layout.edit_product, container, false);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        newProduct = (TextView) dialog.findViewById(R.id.new_product_name);
        measureSpinner = (Spinner) dialog.findViewById(R.id.measure_spin);
        categorySpinner = (Spinner) dialog.findViewById(R.id.category_spin);
        expirySpinner = (Spinner) dialog.findViewById(R.id.expiry_spin);
        quantityEditText = (EditText) dialog.findViewById(R.id.qunatity_edit);
        remove = (Button) dialog.findViewById(R.id.remove_product);
        add = (Button) dialog.findViewById(R.id.update_product);
        cancel = (Button) dialog.findViewById(R.id.cancel_product);
        activity = (ViewPageActivity) getActivity();
        productsFragment = (ProductsFragment) activity.getAdapter().getItem(0);


        //spinners
        ArrayAdapter<String> measureAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_predefined_value, DBManager.predefinedMeasures);
        measureAdapter.setDropDownViewResource(R.layout.spinner_predefined_value);
        measureSpinner.setAdapter(measureAdapter);
        measureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category category : DBManager.predefinedCategories) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_predefined_value, categoryNames);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_predefined_value);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> expiryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_predefined_value, DBManager.predefinedExpiryTerms);
        expiryAdapter.setDropDownViewResource(R.layout.spinner_predefined_value);
        expirySpinner.setAdapter(expiryAdapter);
        expirySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newProduct.setText(name);
        measureSpinner.setSelection(measureArgs);
        categorySpinner.setSelection(categoryArgs);
        expirySpinner.setSelection(expiryTermArgs);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(quantityEditText.getText().toString().equals(""))) {
                    quantity = Double.parseDouble(quantityEditText.getText().toString());
                    if (quantity == 0) {
                        quantityEditText.setError("invalid quantity");
                        return;
                    }
                } else {
                    quantityEditText.setError("invalid quantity");
                    return;
                }
                TextView selectedItem;
                measureID = measureSpinner.getSelectedItemPosition();
                if (measureID == 0) {
                    selectedItem = (TextView) measureSpinner.getSelectedView();
                    selectedItem.setError("invalid measure");
                    return;
                }
                categoryID = categorySpinner.getSelectedItemPosition();
                if (categoryID == 0) {
                    selectedItem = (TextView) categorySpinner.getSelectedView();
                    selectedItem.setError("invalid food category");
                    return;
                }
                expiryTermID = expirySpinner.getSelectedItemPosition();
                if (expiryTermID == 0) {
                    selectedItem = (TextView) expirySpinner.getSelectedView();
                    selectedItem.setError("invalid expiry term");
                    return;
                }
                long currentTimeInMilli = Calendar.getInstance().getTimeInMillis();
                Product product = new Product(name, measureID, categoryID);
                product.setPurchaseDateInMilli(currentTimeInMilli);
                product.setExpiryTermID(expiryTermID);
                product.setQuantity(quantity + quantityArgs);
                DBManager.getInstance(getActivity()).addProduct(product);
                productsFragment.getAdapter().addProduct(product);
                productsFragment.getAdapter().notifyDataSetChanged();
                dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((quantityEditText.getText().toString().equals(""))) {
                    quantityEditText.setError("invalid quantity");
                    return;
                }
                quantity = Double.parseDouble(quantityEditText.getText().toString());
                if (DBManager.households.get(DBManager.currentHousehold).getProducts().containsKey(name)) {
                    Product productInFridge = DBManager.households.get(DBManager.currentHousehold).getProducts().get(name);
                    if (productInFridge.getQuantity() < quantity) {
                        Toast.makeText(getContext(), "Invalid quantity to be removed! ", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        DBManager.getInstance(getActivity()).removeProduct(name, quantity);
                        productsFragment.getAdapter().removeProduct(name, quantity);
                        productsFragment.getAdapter().notifyDataSetChanged();
                        dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid product name!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }


}
