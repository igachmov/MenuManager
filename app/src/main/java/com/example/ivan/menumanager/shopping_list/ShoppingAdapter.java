package com.example.ivan.menumanager.shopping_list;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;
import com.example.ivan.menumanager.recipe.RecipeViewFragment;

import java.util.ArrayList;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.NewViewHolder>{

    private ArrayList<String> recipeNames;
    private ArrayList<Recipe> recipes ;
    private Activity activity;
    private String recipeName;
    public ShoppingAdapter(Activity activity, ArrayList<String> recipeNames) {
        this.recipeNames = recipeNames;
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
            int position = getAdapterPosition();
            RecipeViewFragment recipeViewFragment = new RecipeViewFragment(position);
            ViewPageActivity myActivity = (ViewPageActivity) activity;
            recipeViewFragment.show(myActivity.getSupportFragmentManager(), "shoppingView");
        }
    }

   public void getInfo(String name,Recipe recipe){
       recipeNames.add(name);
       if(recipes==null){
           recipes = new ArrayList<>();
       }
       recipes.add(recipe);
       notifyDataSetChanged();
   }
}


