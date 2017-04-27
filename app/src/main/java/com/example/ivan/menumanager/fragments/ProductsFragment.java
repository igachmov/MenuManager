package com.example.ivan.menumanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.adapters.ProductsFridgeAdapter;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment  {

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


        //inflating recyclerView only if visible
        recyclerView = (RecyclerView) root.findViewById(R.id.household_products_recyclerview);
        Collection<Product> productsInFridge = DBManager.households.get(DBManager.currentHousehold).getProducts().values();
        final ArrayList<Product> productsList = new ArrayList<>();
        for(Product product: productsInFridge){
            productsList.add(product);
        }

        if(recyclerView.getVisibility() == View.VISIBLE){
            ProductsFridgeAdapter adapter = new ProductsFridgeAdapter(getActivity(), (ProductsFridgeAdapter.ICommunicator) getActivity(), productsList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // floating button
            ImageView icon = new ImageView(getActivity());
            // icon.setImageDrawable( ... );

            FloatingActionButton actionButton = new FloatingActionButton.Builder(getActivity())
                    .setContentView(icon)
                    .build();
           // actionButton.setVisibility();

        }



        fridgeImage = (ImageView) root.findViewById(R.id.fridge_image);
        fridgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productsList.size() != 0){
                    setLayout();
                }
                else{
                    ChooseFragment chooseFragment = new ChooseFragment();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    chooseFragment.show(fm, "chooseItem");
                }
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
