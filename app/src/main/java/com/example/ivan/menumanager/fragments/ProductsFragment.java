package com.example.ivan.menumanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.adapters.ProductsRecyclerAdapter;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    private ImageView fridgeImage;
    private List<Product> fridge;


    private View root;
    private RecyclerView recyclerView;
    private LinearLayout productLayout;
    private LinearLayout fridgeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_products, container, false);

        productLayout = (LinearLayout) root.findViewById(R.id.category_layout);
        fridgeLayout = (LinearLayout) root.findViewById(R.id.household_products_layout);

        fridge = new ArrayList<>();


        recyclerView = (RecyclerView) root.findViewById(R.id.household_products_recyclerview);
        ProductsRecyclerAdapter adapter = new ProductsRecyclerAdapter(getActivity(), fridge);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fridgeImage = (ImageView) root.findViewById(R.id.fridge_image);
        fridgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Products is pressed", Toast.LENGTH_SHORT).show();
                setLayout();
            }
        });
        return root;
    }

    public LinearLayout getProductLayout() {
        return productLayout;
    }

    public LinearLayout getFridgeLayout() {
        return fridgeLayout;
    }

    private void setLayout() {
        productLayout.setVisibility(View.GONE);
        fridgeLayout.setVisibility(View.VISIBLE);
    }


}
