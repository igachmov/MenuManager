package com.example.ivan.menumanager.recipe;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;
import com.example.ivan.menumanager.shopping_list.ShoppingFragment;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeViewFragment extends DialogFragment {


    private RecyclerView recyclerView;
    private static ArrayList<Product> productData ;
    private ImageView recipeImage;
    private TextView recipeText;
    private TextView recipeName;
    private ImageView clear;
    private int position;
    private String instructions;
    private Button checkUrlButton;
    private String sourceUrl;
    private FloatingActionButton fab;
    private Recipe recipe;
    public RecipeViewFragment() {

    }

    public RecipeViewFragment(int position) {
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        recipe = RecipeSearchAdapter.recipes.get(position);
        productData = recipe.getIngredients();
        instructions = recipe.getInstructions();
        sourceUrl = recipe.getSourceUrl();

        View dialog = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        checkUrlButton = (Button) dialog.findViewById(R.id.url_button);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recipe_view_recyclerview);
        recipeImage = (ImageView) dialog.findViewById(R.id.recipe_ingr_image);
        recipeImage.setImageBitmap(recipe.getPicBitmap());
        recipeText = (TextView) dialog.findViewById(R.id.recipe_ingr_text);
        if(instructions == null || instructions.equals("null")){
                recipeText.setText("Sorry there is no instructions.\n Would you like to check the link.");
                checkUrlButton.setVisibility(View.VISIBLE);
                checkUrlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(sourceUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
            else if(instructions!=null && !instructions.isEmpty()) {
                recipeText.setText(instructions);
            }
        recipeText.setMovementMethod(new ScrollingMovementMethod());
        recipeName = (TextView) dialog.findViewById(R.id.recipe_ingr_name);
        recipeName.setText(recipe.getName());
        clear = (ImageView) dialog.findViewById(R.id.clear_image_button);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        IngredientAdapter adapter = new IngredientAdapter(getActivity(), productData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        fab = (FloatingActionButton) dialog.findViewById(R.id.fab_view_recipe_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPageActivity activity = (ViewPageActivity) getActivity();
                ShoppingFragment shoppingFragment = (ShoppingFragment) activity.getAdapter().getItem(1);
                shoppingFragment.getShoppingAdapter().getInfo(recipe.getName(),recipe);
                Toast.makeText(getActivity(), "Recipe added to shoppinglist", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return dialog;
    }
}
