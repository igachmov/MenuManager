package com.example.ivan.menumanager.shopping_list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    private List<ShoppingList> shoppingLists ;
    private RecyclerView recyclerView;
    private ShoppingAdapter shoppingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(shoppingLists==null) {
            shoppingLists = new ArrayList<>();
        }
        shoppingLists = DBManager.households.get(DBManager.currentHousehold).getShoppingLists();
        View root = inflater.inflate(R.layout.fragment_shopping, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.shoppingList_recyclerview);
        shoppingAdapter  = new ShoppingAdapter(getActivity(),shoppingLists);
        recyclerView.setAdapter(shoppingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }
    public ShoppingAdapter getShoppingAdapter() {
        return shoppingAdapter;
    }
}
