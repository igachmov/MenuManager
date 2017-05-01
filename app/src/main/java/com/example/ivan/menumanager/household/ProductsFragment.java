package com.example.ivan.menumanager.household;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andexert.library.RippleView;
import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;
import java.util.Collection;

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

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int categoryID = 0;
                switch (v.getId()) {
                    case R.id.fridge_image:
                        categoryID = 0;
                        break;
                    case R.id.bakery_image:
                        categoryID = 1;
                        break;
                    case R.id.dairy_image:
                        categoryID = 2;
                        break;
                    case R.id.dressing_image:
                        categoryID = 3;
                        break;
                    case R.id.fruits_image:
                        categoryID = 4;
                        break;
                    case R.id.grain_image:
                        categoryID = 5;
                        break;
                    case R.id.meat_image:
                        categoryID = 6;
                        break;
                    case R.id.sauce_image:
                        categoryID = 7;
                        break;
                    case R.id.veggies_image:
                        categoryID = 8;
                        break;
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
                    if (products.size() != 0) {
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
                    }
                    else{
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
}
