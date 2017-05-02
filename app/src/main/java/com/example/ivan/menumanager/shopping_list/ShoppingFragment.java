package com.example.ivan.menumanager.shopping_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Product;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {


    private TextView recipeName;
    private ArrayList<String> names ;
    private RecyclerView recyclerView;
    private ShoppingAdapter shoppingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Bundle1",names+"");
        if(names==null) {
            names = new ArrayList<>();
        }
        Log.e("Bundle1","OnCreate");
        Log.e("Bundle1",names.size()+"");
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            String name = bundle.getString("name");
            Log.e("Bundle1",bundle.getString("name")+"");
            ArrayList<Product> productsToBuy = (ArrayList<Product>) bundle.getSerializable("ARRAYLIST");
            names.add(name);
        }
        View root = inflater.inflate(R.layout.fragment_shopping, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.shoppingList_recyclerview);
        shoppingAdapter  = new ShoppingAdapter(getActivity(),names);
        recyclerView.setAdapter(shoppingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    public ShoppingAdapter getShoppingAdapter() {
        return shoppingAdapter;
    }

    public ArrayList<String> getNames() {
        return names;
    }
    public void getArray(ArrayList<String> arrayList){
        this.names = arrayList;
    }
}
