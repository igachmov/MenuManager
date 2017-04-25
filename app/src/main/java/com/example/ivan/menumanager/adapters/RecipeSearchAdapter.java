package com.example.ivan.menumanager.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.ivan.menumanager.RecipeActivity;
import com.example.ivan.menumanager.fragments.RecipesFragment;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 4/18/2017.
 */

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.NewViewHolder>{

    private ArrayList<Recipe> recipes;
    private Context context;

    public RecipeSearchAdapter(Context context,  ArrayList<Recipe> recipes) {
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
//        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, RecipeActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    class NewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeImage;
        TextView recipeName;
        TextView progressBar;
        TextView ingredients;

        public NewViewHolder(View row) {
            super(row);
            row.setOnClickListener(this);
            recipeImage = (ImageView) row.findViewById(R.id.recipe_image);
            recipeName = (TextView) row.findViewById(R.id.recipe_name_tv);
            progressBar = (TextView) row.findViewById(R.id.progress_bar);
            ingredients = (TextView) row.findViewById(R.id.ingredients_tv);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("ID", recipes.get(getLayoutPosition()).getId());
            intent.putExtra("name", recipes.get(getLayoutPosition()).getName());
            intent.putExtra("bitmap", recipes.get(getLayoutPosition()).getPicBitmap());
            context.startActivity(intent);
        }


    }

}
