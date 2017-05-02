package com.example.ivan.menumanager.recipe;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    public RecipeViewFragment() {

    }

    public RecipeViewFragment(int position) {
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        final Recipe recipe = RecipeSearchAdapter.recipes.get(position);
        productData = recipe.getIngredients();
        instructions = recipe.getInstructions();
        sourceUrl = recipe.getSourceUrl();

        View dialog = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        checkUrlButton = (Button) dialog.findViewById(R.id.url_button);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recipe_view_recyclerview);
        recipeImage = (ImageView) dialog.findViewById(R.id.recipe_ingr_image);
        recipeImage.setImageBitmap(recipe.getPicBitmap());
        recipeText = (TextView) dialog.findViewById(R.id.recipe_ingr_text);
        if(instructions.equals("null") || instructions == null){
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
                shoppingFragment.getShoppingAdapter().getInfo(recipe.getName());
                Toast.makeText(getActivity(), "Recipe added to shoppinglist", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return dialog;
    }

//    public class DownloadRecipeInstruction extends AsyncTask<String, Void, ArrayList<Product>> {
//        JSONObject json = null;
//        JSONArray jsonArr = null;
//        Product product = null;
//
//        @Override
//        protected ArrayList<Product> doInBackground(String... params) {
//            String recipeId = params[0];
//            try {
//                Log.e("Ivan", "connection");
//                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/"+recipeId+"/information");
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("GET");
//                con.setRequestProperty("X-Mashape-Key", "y6PWzRnUUrmshSwL1nXeJXgDCJuop1nEGLPjsnlBLEuOxNFyXY");
//                con.setRequestProperty("Accept", "application/json");
//                Scanner sc = new Scanner(con.getInputStream());
//                StringBuilder jsonResponse = new StringBuilder();
//                while (sc.hasNextLine()) {
//                    jsonResponse.append(sc.nextLine());
//                }
//                json = new JSONObject(jsonResponse.toString());
//                jsonArr = json.getJSONArray("extendedIngredients");
//                instructions = json.getString("instructions");
//                sourceUrl = json.getString("sourceUrl");
//
//                Log.e("Ivan",sourceUrl);
//                if (jsonArr != null) {
//                    for (int i = 0; i < jsonArr.length(); i++) {
//                        JSONObject jsonObj = jsonArr.getJSONObject(i);
//                        String name = jsonObj.getString("name");
//                        String amount = jsonObj.getString("amount");
//                        String unit = jsonObj.getString("unit");
//                        product = new Product(name, 0, 0);
//                        DecimalFormat df = new DecimalFormat("#.##");
//                        String dx=df.format(Double.parseDouble(amount));
//                        product.setQuantity(Double.parseDouble(dx));
//                        product.setUnit(unit);
//
//                        for(Map.Entry<String,Product> e : DBManager.households.get(DBManager.currentHousehold).getProducts().entrySet()){
//                            String productInFridge = e.getKey();
//                            String plularNameInFridge = productInFridge+"s";
//                            double quantityInFridge = e.getValue().getQuantity();
//                            String unitInFridge = e.getValue().getUnit();
//                            if((productInFridge.toLowerCase().contains(name.toLowerCase()) || plularNameInFridge.toLowerCase().contains(name.toLowerCase()))){
//                                product.setHasItem(true);
//                            }
//                        }
//                        productData.add(product);
//                    }
//                }
//            } catch (MalformedURLException e) {
//                Log.e("Ivan", "Malformed");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                Log.e("Ivan", "ProtocolException");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return productData;
//        }

//        @Override
//        protected void onPostExecute(ArrayList<Product> productData) {
//            if(instructions.equals("null") || instructions == null){
//                recipeText.setText("Sorry there is no instructions.\n Would you like to check the link.");
//                checkUrlButton.setVisibility(View.VISIBLE);
//                checkUrlButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Uri uri = Uri.parse(sourceUrl);
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(intent);
//                    }
//                });
//            }
//            else if(instructions!=null && !instructions.isEmpty()) {
//                recipeText.setText(instructions);
//            }
//
//            recipeName.setText(name);
//            recipeImage.setImageBitmap(bitmap);
//            IngredientAdapter adapter = new IngredientAdapter(getActivity(), productData);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        }
//    }

//    public Boolean checkMeasures(Product product,String unitInFridge, String unitInRecipe,double quantityInFridge, double quantityInRecipe){
//        String unitFridge = unitInFridge.toLowerCase();
//        String unitRecipe = unitInFridge.toLowerCase();
//        double quantityFridge = quantityInFridge;
//        double quantityRecipe = quantityInRecipe;
//
//
////        g,kg,ml,liter,item,pack
//
//
////        cup - cups = 340 g || 236.588 ml
////        dash = 0.72 g;
////        ounce - oz = 28.3495 g;
////
////        teaspoons - tsp = 4.92892 ml;
////        tablespoon - tbsp  = 14.7868 ml;
////
////        pounds - lb - lbs = 0.453592 kg;
////        bunch = 0.34 kg;
////        switch (unitFridge){
////            case "g":
////                    switch (unitRecipe){
////                        case "cup":
////                        case "cups":
////                            if(quantityFridge >= quantityRecipe*340){
////                                flag = true;
////                            }
////                            else {
////                                flag = false;
////                            }
////                            break;
////                        case "dash":
////                            if(quantityFridge >= quantityRecipe*0.72){
////                                flag = true;
////                            }
////                            else {
////                                flag = false;
////                            }
////                            break;
////                        case "ounce":
////                        case "oz":
////                            if(quantityFridge >= quantityRecipe*28.3495){
////                                flag = true;
////                            }
////                            else {
////                                flag = false;
////                            }
////                            break;
////                        case "pounds":
////                        case "lb":
////                        case "lbs":
////                            if(quantityFridge >= quantityRecipe*454){
////                                flag = true;
////                            }
////                            else {
////                                flag = false;
////                            }
////                            break;
////                        case "bunch":
////                            if(quantityFridge >= quantityRecipe*340){
////                                flag = true;
////                            }
////                            else {
////                                flag = false;
////                            }
////                            break;
////                        default:
////                            flag = true;
////                            break;
////                    }
////
////
////
////        }
//
//
//
//
//
//
//    }



}
