package com.example.ivan.menumanager.shopping_list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.ShoppingList;

import java.util.List;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.NewViewHolder>{

    private List<ShoppingList> shoppingLists;
    private Activity activity;
    private ShoppingList shoppingList;


    public ShoppingAdapter(Activity activity, List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        this.activity = activity;
    }
    @Override
    public ShoppingAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(activity);
        View row = li.inflate(R.layout.shoppinglist_recipe_recycler, parent,false);
        ShoppingAdapter.NewViewHolder vh = new ShoppingAdapter.NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.NewViewHolder holder, int position) {
        shoppingList = shoppingLists.get(position);
        holder.recipeName.setText(shoppingList.getName());
    }

    @Override
    public int getItemCount() {
       return shoppingLists.size();
    }



    public class NewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeName;

        public NewViewHolder(View row) {
            super(row);
            row.setOnClickListener(this);
            recipeName = (TextView) row.findViewById(R.id.shoppinglist_name_recipe);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ShowShoppinglistFragment showShoppinglistFragment = new ShowShoppinglistFragment(shoppingLists.get(position).getProductsToBuy(),position);
            ViewPageActivity myActivity = (ViewPageActivity) activity;
            showShoppinglistFragment.show(myActivity.getSupportFragmentManager(), "shoppingView");
        }
    }
    public void setInfo() {
        notifyDataSetChanged();
    }
}


