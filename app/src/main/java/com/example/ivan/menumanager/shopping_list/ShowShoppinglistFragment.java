package com.example.ivan.menumanager.shopping_list;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShowShoppinglistFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private Button remove;
    private Button showRecipe;
    private ArrayList<Product> products;

    public ShowShoppinglistFragment (ArrayList<Product> products){
        this.products = products;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        View dialog = inflater.inflate(R.layout.show_shoppinglist_fragment, container, false);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.show_shoppinglist_recycler);
        remove = (Button) dialog.findViewById(R.id.remove_button);
        showRecipe = (Button) dialog.findViewById(R.id.show_recipe_button);

        ShowShoppinglistAdapter adapter = new ShowShoppinglistAdapter(getActivity(),products );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return dialog;
    }


}
