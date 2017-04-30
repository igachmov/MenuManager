package com.example.ivan.menumanager.recipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.menumanager.R;

import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;

import com.example.ivan.menumanager.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Ivan on 4/18/2017.
 */

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.NewViewHolder>{

    public static ArrayList<Recipe> recipes;
    private Context context;
    private Recipe recipe;
    private int position2;
    private  int x = 0;
    private int counter2 = 0;
    private int counter3 = 0;


    public RecipeSearchAdapter(Context context,  ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.context = context;

    }

    @Override
    public RecipeSearchAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View row = li.inflate(R.layout.recipe_search_recycler, parent,false);
        RecipeSearchAdapter.NewViewHolder vh = new RecipeSearchAdapter.NewViewHolder(row);

        if(x<1) {
            Log.e("Vleze","INSIDE");
            for (int i = 0; i <recipes.size() ; i++) {
                String recipeImg = recipes.get(i).getPicURL();
                String id = recipes.get(i).getId();
                   new DownloadRecipeInstruction().execute(id);
                   new DownloadImageTask().execute(recipeImg);


            }
            x++;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeSearchAdapter.NewViewHolder holder, final int position) {
        //izvikva se asyntask za izteglqne na img i na recepta za receptata na opredelenata poziciq

            //v onBindViewHolder anonimen klas s asynctask
            recipe = recipes.get(position);
            holder.recipeName.setText(recipe.getName());
            holder.recipeImage.setImageBitmap(recipe.getPicBitmap());
            holder.ingredients.setText(recipe.getProductCounter() + "" + "/" + recipe.getIngredients().size() + "");


//            if(counter3<20) {
//
//            new AsyncTask<String, Void, Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(String... params) {
//                    Log.e("Ivan", "Counter in DownloadTask " + counter2 + "");
//                    String urldisplay = params[0];
//                    URLConnection urlConnection = null;
//                    Bitmap mIcon11 = null;
//                    // Bitmap defaul = BitmapFactory.decodeFile(fname);
//                    //Log.e("Ivan",defaul.toString());
//                    InputStream in = null;
//                    URL url = null;
//                    try {
//                        url = new URL(urldisplay);
//                        urlConnection = url.openConnection();
//                        urlConnection.connect();
//                        in = new URL(urldisplay).openStream();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    int file_size = urlConnection.getContentLength();
//                    if (file_size > 150000 || file_size == 4384) {
////                recipe.setPicBitmap(defaul);
////                return defaul;
//                    }
//                    mIcon11 = BitmapFactory.decodeStream(in);
//                    recipe.setPicBitmap(mIcon11);
//                    return mIcon11;
//                }
//
//                @Override
//                protected void onPostExecute(Bitmap bitmap) {
//                    holder.recipeImage.setImageBitmap(recipe.getPicBitmap());
//                    counter2++;
//                }
//            }.execute(recipeImg);
//            new AsyncTask<String, Void, Recipe>() {
//                JSONObject json = null;
//                JSONArray jsonArr = null;
//                Product product = null;
//                int productCounter = 0;
//
//                @Override
//                protected Recipe doInBackground(String... params) {
//                    String recipeId = params[0];
//                    try {
//                        URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + recipeId + "/information");
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                        con.setRequestMethod("GET");
//                        con.setRequestProperty("X-Mashape-Key", "y6PWzRnUUrmshSwL1nXeJXgDCJuop1nEGLPjsnlBLEuOxNFyXY");
//                        con.setRequestProperty("Accept", "application/json");
//                        Scanner sc = new Scanner(con.getInputStream());
//                        StringBuilder jsonResponse = new StringBuilder();
//                        while (sc.hasNextLine()) {
//                            jsonResponse.append(sc.nextLine());
//                        }
//                        json = new JSONObject(jsonResponse.toString());
//                        jsonArr = json.getJSONArray("extendedIngredients");
//                        if (jsonArr != null) {
//                            for (int i = 0; i < jsonArr.length(); i++) {
//                                JSONObject jsonObj = jsonArr.getJSONObject(i);
//                                String name = jsonObj.getString("name");
//                                String amount = jsonObj.getString("amount");
//                                DecimalFormat df = new DecimalFormat("#.##");
//                                String dx = df.format(Double.parseDouble(amount));
//                                double qunatity = Double.parseDouble(dx);
//                                product = new Product(name, 0, 0);
//                                for (Map.Entry<String, Product> e : DBManager.households.get(DBManager.currentHousehold).getProducts().entrySet()) {
//                                    String productInFridge = e.getKey();
//                                    String plularNameInFridge = productInFridge + "s";
//                                    double quantityInFridge = e.getValue().getQuantity();
//                                    String unit = jsonObj.getString("unit");
//                                    String unitInFridge = e.getValue().getUnit();
//                                    Log.e("Ivan", productInFridge);
//                                    Log.e("Ivan", plularNameInFridge);
//                                    Log.e("Ivan", name);
//                                    Log.e("Ivan", quantityInFridge + "");
//                                    Log.e("Ivan", qunatity + "");
//                                    Log.e("Ivan", unit);
//                                    if ((productInFridge.toLowerCase().contains(name.toLowerCase()) || plularNameInFridge.toLowerCase().contains(name.toLowerCase()))) {
////                                if(unitInFridge.equalsIgnoreCase("kg") && unit.equalsIgnoreCase("g")){
////                                    quantityInFridge = quantityInFridge*1000;
////                                    if(quantityInFridge>=qunatity){
////                                        productCounter++;
////                                    }
////                                }
////                                if(unitInFridge.equalsIgnoreCase("kg") && unit.equalsIgnoreCase("kg")){
////                                    if(quantityInFridge>=qunatity){
////                                        productCounter++;
////                                    }
////                                }
////                                if(unitInFridge.equalsIgnoreCase("liter") && unit.equalsIgnoreCase("ml")){
////                                    quantityInFridge = quantityInFridge*1000;
////                                    if(quantityInFridge>=qunatity){
////                                        productCounter++;
////                                    }
////                                }
////                                if(unitInFridge.equalsIgnoreCase("liter") && unit.equalsIgnoreCase("liter")){
////                                    if(quantityInFridge>=qunatity){
////                                        productCounter++;
////                                    }
////                                }
//                                        productCounter++;
//                                    }
//                                }
//                                recipe.getIngredients().add(product);
//
//                            }
//                            recipe.setProductCounter(productCounter);
//                            Log.e("Ivan", "Product counter " + counter3 + "");
//
//                        }
//                    } catch (MalformedURLException e) {
//                        Log.e("Ivan", "Malformed");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (ProtocolException e) {
//                        Log.e("Ivan", "ProtocolException");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return recipe;
//                }
//
//                @Override
//                protected void onPostExecute(Recipe recipe) {
//                    holder.ingredients.setText(recipe.getProductCounter() + "" + "/" + recipe.getIngredients().size() + "");
//                    counter3++;
//
//                }
//            }.execute(id);
//        }
//
//        else{
//            holder.recipeImage.setImageBitmap(recipe.getPicBitmap());
//            holder.ingredients.setText(recipe.getProductCounter() + "" + "/" + recipe.getIngredients().size() + "");
//
//        }



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
            intent.putExtra("position", getAdapterPosition());
         //   intent.putExtra("bitmap", recipes.get(getLayoutPosition()).getPicBitmap());
            context.startActivity(intent);
        }


    }



    public class DownloadRecipeInstruction extends AsyncTask<String, Void, ArrayList<Recipe>> {
        JSONObject json = null;
        JSONArray jsonArr = null;
        Product product = null;
        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            String recipeId = params[0];
            try {
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/"+recipeId+"/information");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Mashape-Key", "y6PWzRnUUrmshSwL1nXeJXgDCJuop1nEGLPjsnlBLEuOxNFyXY");
                con.setRequestProperty("Accept", "application/json");
                Scanner sc = new Scanner(con.getInputStream());
                StringBuilder jsonResponse = new StringBuilder();
                while (sc.hasNextLine()) {
                    jsonResponse.append(sc.nextLine());
                }
                json = new JSONObject(jsonResponse.toString());
                jsonArr = json.getJSONArray("extendedIngredients");
                if (jsonArr != null) {
                    int productCounter = 0 ;
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String name = jsonObj.getString("name");
                        String amount = jsonObj.getString("amount");
                        DecimalFormat df = new DecimalFormat("#.##");
                        String dx=df.format(Double.parseDouble(amount));
                        double qunatity = Double.parseDouble(dx);
                        product = new Product(name, 0, 0);
                        for(Map.Entry<String,Product> e : DBManager.households.get(DBManager.currentHousehold).getProducts().entrySet()){
                            String productInFridge = e.getKey();
                            String plularNameInFridge = productInFridge+"s";
                            double quantityInFridge = e.getValue().getQuantity();
                            String unit = jsonObj.getString("unit");
                            String unitInFridge = e.getValue().getUnit();
                            Log.e("Ivan",productInFridge);
                            Log.e("Ivan",plularNameInFridge);
                            Log.e("Ivan",name);
                            Log.e("Ivan",quantityInFridge+"");
                            Log.e("Ivan",qunatity+"");
                            Log.e("Ivan",unit);
                            if((productInFridge.toLowerCase().contains(name.toLowerCase()) || plularNameInFridge.toLowerCase().contains(name.toLowerCase()))){
//                                if(unitInFridge.equalsIgnoreCase("kg") && unit.equalsIgnoreCase("g")){
//                                    quantityInFridge = quantityInFridge*1000;
//                                    if(quantityInFridge>=qunatity){
//                                        productCounter++;
//                                    }
//                                }
//                                if(unitInFridge.equalsIgnoreCase("kg") && unit.equalsIgnoreCase("kg")){
//                                    if(quantityInFridge>=qunatity){
//                                        productCounter++;
//                                    }
//                                }
//                                if(unitInFridge.equalsIgnoreCase("liter") && unit.equalsIgnoreCase("ml")){
//                                    quantityInFridge = quantityInFridge*1000;
//                                    if(quantityInFridge>=qunatity){
//                                        productCounter++;
//                                    }
//                                }
//                                if(unitInFridge.equalsIgnoreCase("liter") && unit.equalsIgnoreCase("liter")){
//                                    if(quantityInFridge>=qunatity){
//                                        productCounter++;
//                                    }
//                                }
                                productCounter++;
                            }
                        }
                        if(counter3<20) {
                            recipes.get(counter3).getIngredients().add(product);
                        }

                    }
                    if(counter3<20) {
                        recipes.get(counter3).setProductCounter(productCounter);
                    }
                    Log.e("Counter","Product counter "+counter3+"");

                }
            } catch (MalformedURLException e) {
                Log.e("Ivan", "Malformed");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("Ivan", "ProtocolException");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            counter3++;

        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Log.e("Counter", "Counter in DownloadTask " + counter2 + "");
            String urldisplay = params[0];
            URLConnection urlConnection = null;
            Bitmap mIcon11 = null;
           // Bitmap defaul = BitmapFactory.decodeFile(fname);
            //Log.e("Ivan",defaul.toString());
            InputStream in = null;
            URL url = null;
            try {
                url = new URL(urldisplay);
                urlConnection = url.openConnection();
                urlConnection.connect();
                in = new URL(urldisplay).openStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int file_size = urlConnection.getContentLength();
            if(file_size>150000 || file_size==4384){
//                recipe.setPicBitmap(defaul);
//                return defaul;
            }
            mIcon11 = BitmapFactory.decodeStream(in);
            if(counter2<20) {
                recipes.get(counter2).setPicBitmap(mIcon11);
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            notifyDataSetChanged();
            counter2++;
        }
    }

}
