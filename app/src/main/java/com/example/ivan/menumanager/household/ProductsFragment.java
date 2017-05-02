package com.example.ivan.menumanager.household;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.andexert.library.RippleView;
import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    private View root;
    private RippleView fridgeImage;
    private RippleView dairyImage;
    private RippleView bakeryImage;
    private RippleView dressingImage;
    private RippleView veggiesImage;
    private RippleView sauceImage;
    private RippleView fruitsImage;
    private RippleView meatImage;
    private RippleView grainImage;
    private RecyclerView recyclerView;
    private LinearLayout catagoryLayout;
    private FrameLayout fridgeLayout;
    private FloatingActionButton fab;
    private ProductsAdapter adapter;

    ArrayList<Product> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_products, container, false);
        catagoryLayout = (LinearLayout) root.findViewById(R.id.category_layout);
        fridgeLayout = (FrameLayout) root.findViewById(R.id.household_products_layout);
        recyclerView = (RecyclerView) root.findViewById(R.id.household_products_recyclerview);

        View.OnClickListener listener = initialiseOnClickListener();

        fridgeImage = (RippleView) root.findViewById(R.id.fridge_image);
        fridgeImage.setOnClickListener(listener);

        dairyImage = (RippleView) root.findViewById(R.id.dairy_image);
        dairyImage.setOnClickListener(listener);

        bakeryImage = (RippleView) root.findViewById(R.id.bakery_image);
        bakeryImage.setOnClickListener(listener);

        dressingImage = (RippleView) root.findViewById(R.id.dressing_image);
        dressingImage.setOnClickListener(listener);

        veggiesImage = (RippleView) root.findViewById(R.id.veggies_image);
        veggiesImage.setOnClickListener(listener);

        sauceImage = (RippleView) root.findViewById(R.id.sauce_image);
        sauceImage.setOnClickListener(listener);

        fruitsImage = (RippleView) root.findViewById(R.id.fruits_image);
        fruitsImage.setOnClickListener(listener);

        meatImage = (RippleView) root.findViewById(R.id.meat_image);
        meatImage.setOnClickListener(listener);

        grainImage = (RippleView) root.findViewById(R.id.grain_image);
        grainImage.setOnClickListener(listener);

        return root;
    }

    public LinearLayout getCatagoryLayout() {
        return this.catagoryLayout;
    }

    public FrameLayout getFridgeLayout() {
        return this.fridgeLayout;
    }

    private View.OnClickListener initialiseOnClickListener() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] viewID = new int[]{R.id.fridge_image, R.id.bakery_image, R.id.dairy_image, R.id.dressing_image,
                        R.id.fruits_image,R.id.grain_image,R.id.meat_image,R.id.sauce_image, R.id.veggies_image};
                int categoryID;
                for (categoryID = 0; categoryID < viewID.length; categoryID++){
                    if( v.getId() == viewID[categoryID]){
                        break;
                    }
                }
                if (DBManager.households.get(DBManager.currentHousehold).getProducts() != null) {
                    products = new ArrayList<Product>();
                    if (v.getId() == R.id.fridge_image) {
                        for (Product product : DBManager.households.get(DBManager.currentHousehold).getProducts().values()) {
                            products.add(product);
                        }
                    } else {
                        for (String productName : DBManager.households.get(DBManager.currentHousehold).getProducts().keySet()) {
                            if (DBManager.predefinedProducts.get(productName).getFoodCategoryID() == categoryID) {
                                products.add(DBManager.households.get(DBManager.currentHousehold).getProducts().get(productName));
                            }
                        }
                    }
                        catagoryLayout.setVisibility(View.GONE);
                        fridgeLayout.setVisibility(View.VISIBLE);
                        adapter = new ProductsAdapter(getActivity(), products);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        fab = (FloatingActionButton) root.findViewById(R.id.fab);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChooseItemFragment chooseItemFragment = new ChooseItemFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("callingObject", "floatingButton");
                                chooseItemFragment.setArguments(bundle);
                                chooseItemFragment.show(getActivity().getSupportFragmentManager(), "chooseItem");
                            }
                        });
                    if (products.size() == 0) {
                        ChooseItemFragment chooseItemFragment = new ChooseItemFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("callingObject", DBManager.predefinedCategories.get(categoryID).getName());
                        chooseItemFragment.setArguments(bundle);
                        chooseItemFragment.show(getActivity().getSupportFragmentManager(), "chooseItem");
                    }
                } else {
                    ChooseItemFragment chooseItemFragment = new ChooseItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("callingObject", DBManager.predefinedCategories.get(categoryID).getName());
                    chooseItemFragment.setArguments(bundle);
                    chooseItemFragment.show(getActivity().getSupportFragmentManager(), "chooseItem");
                }
            }
        };
        return listener;
    }

    public ProductsAdapter getAdapter(){
        return adapter;
    }

}
