package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.fragments.RecipesFragment;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Ivan on 4/18/2017.
 */

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.NewViewHolder>{

    private List<Recipe> recipes;
    private Context context;

    public RecipeSearchAdapter(Context context,  List<Recipe> recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public RecipeSearchAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.layout_recipe_search, parent,false);
        RecipeSearchAdapter.NewViewHolder vh = new RecipeSearchAdapter.NewViewHolder(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecipeSearchAdapter.NewViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeImage.setImageBitmap(recipe.getPicBitmap());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    class NewViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;
        TextView progressBar;
        TextView ingredients;

        public NewViewHolder(View row) {
            super(row);
            recipeImage = (ImageView) row.findViewById(R.id.recipe_image);
            recipeName = (TextView) row.findViewById(R.id.recipe_name_tv);
            progressBar = (TextView) row.findViewById(R.id.progress_bar);
            ingredients = (TextView) row.findViewById(R.id.ingredients_tv);
        }


    }

}
