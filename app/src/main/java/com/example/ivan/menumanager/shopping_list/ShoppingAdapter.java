package com.example.ivan.menumanager.shopping_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;

import java.util.ArrayList;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.NewViewHolder>{

    public static ArrayList<String> recipeNames;
    private Context context;
    private String recipeName;
    public ShoppingAdapter(Context context, ArrayList<String> recipeNames) {
        this.recipeNames = recipeNames;
        this.context = context;
    }
    @Override
    public ShoppingAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.shoppinglist_recipe_recycler, parent,false);
        ShoppingAdapter.NewViewHolder vh = new ShoppingAdapter.NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.NewViewHolder holder, int position) {
        recipeName = recipeNames.get(position);
        holder.recipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
       return recipeNames.size();
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
            notifyDataSetChanged();
        }
    }

   public void getInfo(String name){
       recipeNames.add(name);
       notifyDataSetChanged();
   }
}


