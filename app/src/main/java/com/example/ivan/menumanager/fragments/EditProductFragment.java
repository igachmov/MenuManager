package com.example.ivan.menumanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.adapters.ProductsFridgeAdapter;
import com.example.ivan.menumanager.model.Category;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProductFragment extends DialogFragment {

    private TextView newProduct;
    private Spinner measureSpinner;
    private Spinner categorySpinner;
    private Spinner expirySpinner;
    private EditText quantity;
    private Button remove;
    private Button update;
    private Button check;
    private Button cancel;
    private String text;
    private double edittedQuantity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        text = getArguments().getString("text");

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
        quantity = (EditText) dialog.findViewById(R.id.qunatity_edit);
        remove = (Button) dialog.findViewById(R.id.remove_product);
        update = (Button) dialog.findViewById(R.id.update_product);
        check = (Button) dialog.findViewById(R.id.check_shopping_list);
        cancel = (Button) dialog.findViewById(R.id.cancel_product);


        ChooseFragment chooseFragment = (ChooseFragment) getActivity().getSupportFragmentManager().findFragmentByTag("chooseItem");
        TextView textFromEdit = chooseFragment.getItemEditText();
        if (!textFromEdit.getText().toString().isEmpty()) {
            newProduct.setText(textFromEdit.getText().toString());
        } else {
            newProduct.setText(text);

        }

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
        measureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        quantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = newProduct.getText().toString();
                if (!(quantity.getText().toString().equals(""))) {
                    edittedQuantity = Double.parseDouble(quantity.getText().toString());
                } else {
                    quantity.setError("invalid quantity");
                    return;
                }
                TextView selectedItem;
                int measureID = measureSpinner.getSelectedItemPosition();
                if (measureID == 0) {
                    selectedItem = (TextView) measureSpinner.getSelectedView();
                    selectedItem.setError("invalid measure");
                    return;
                }
                int categoryID = categorySpinner.getSelectedItemPosition();
                if (categoryID == 0) {
                    selectedItem = (TextView) measureSpinner.getSelectedView();
                    selectedItem.setError("invalid food category");
                    return;
                }
                int expiryTerm = expirySpinner.getSelectedItemPosition();
                if (expiryTerm == 0) {
                    selectedItem = (TextView) measureSpinner.getSelectedView();
                    selectedItem.setError("invalid expiry term");
                    return;
                }
                Product product = new Product(productName, measureID, categoryID);
                product.setQuantity(edittedQuantity);
                product.setExpiryTerm(expiryTerm);
                DBManager.getInstance(getActivity()).addProduct(product);
                dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((quantity.getText().toString().equals(""))) {
                    quantity.setError("invalid quantity");
                    return;
                }
                double edittedQuantity = Double.parseDouble(quantity.getText().toString());
                String productName = newProduct.getText().toString();
                if (DBManager.households.get(DBManager.currentHousehold).getProducts().containsKey(productName)) {
                    Product productInFridge = DBManager.households.get(DBManager.currentHousehold).getProducts().get(productName);
                    if (productInFridge.getQuantity() != edittedQuantity) {
                        Toast.makeText(getContext(), "Update product! ", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        DBManager.getInstance(getActivity()).removeProduct(productName);
                        dismiss();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Invalid product name!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
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

    public static EditProductFragment newInstance(String text) {
        EditProductFragment fr = new EditProductFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        fr.setArguments(args);

        return fr;
    }

}
