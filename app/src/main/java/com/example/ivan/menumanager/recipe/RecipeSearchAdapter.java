package com.example.ivan.menumanager.recipe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static final int MAX_SIZE_OF_IMAGE = 150000;
    private static final int SIZE_OF_DEFAULT_IMAGE = 4384;

    public static ArrayList<Recipe> recipes;
    private Activity activity;
    private Recipe recipe;
    private Bitmap bitmap;
    private boolean isFinished = true ;
    private int counter2 = 0;
    private int counter3 = 0;
    private String instructions;
    private String sourceUrl;

    public RecipeSearchAdapter(Activity activity, ArrayList<Recipe> recipes,Bitmap bitmap) {
        this.recipes = recipes;
        this.activity = activity;
        this.bitmap = bitmap;
    }

    @Override
    public RecipeSearchAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(activity);
        View row = li.inflate(R.layout.recipe_search_recycler, parent,false);
        RecipeSearchAdapter.NewViewHolder vh = new RecipeSearchAdapter.NewViewHolder(row);
        if(isFinished && recipes.get(0).getIngredients().size()==0) {
            for (int i = 0; i <recipes.size() ; i++) {
                String recipeImg = recipes.get(i).getPicURL();
                String id = recipes.get(i).getId();
                new DownloadRecipeInstruction().execute(id);
                new DownloadImageTask().execute(recipeImg);
            }
            isFinished = false;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecipeSearchAdapter.NewViewHolder holder, final int position) {
            recipe = recipes.get(position);
            holder.recipeName.setText(recipe.getName());
            holder.recipeImage.setImageBitmap(recipe.getPicBitmap());
            holder.ingredients.setText(recipe.getProductCounter() + "" + "/" + recipe.getIngredients().size() + "");
            holder.progressBar.setMax(recipe.getIngredients().size());
            holder.progressBar.setProgress(recipe.getProductCounter());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class NewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeImage;
        TextView recipeName;
        ProgressBar progressBar;
        TextView ingredients;

        public NewViewHolder(View row) {
            super(row);
                row.setOnClickListener(this);
                recipeImage = (ImageView) row.findViewById(R.id.recipe_image);
                recipeName = (TextView) row.findViewById(R.id.recipe_name_tv);
                progressBar = (ProgressBar) row.findViewById(R.id.progress_bar);
                ingredients = (TextView) row.findViewById(R.id.ingredients_tv);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            RecipeViewFragment recipeViewFragment = new RecipeViewFragment(position,bitmap);
            ViewPageActivity myActivity = (ViewPageActivity) activity;
            recipeViewFragment.show(myActivity.getSupportFragmentManager(), "recipeView");
        }
    }
    public class DownloadRecipeInstruction extends AsyncTask<String, Integer, ArrayList<Recipe>> {
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
                instructions = json.getString("instructions");
                sourceUrl = json.getString("sourceUrl");
                recipes.get(counter3).setInstructions(instructions);
                recipes.get(counter3).setSourceUrl(sourceUrl);
                if (jsonArr != null) {
                    int productCounter = 0 ;
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String name = jsonObj.getString("name");
                        String amount = jsonObj.getString("amount");
                        String unit = jsonObj.getString("unit");
                        DecimalFormat df = new DecimalFormat("#.##");
                        String dx=df.format(Double.parseDouble(amount));
                        double qunatity = Double.parseDouble(dx);
                        product = new Product(name, 0, 0);
                        product.fixMeasures(unit,qunatity);

                        for(Map.Entry<String,Product> e : DBManager.households.get(DBManager.currentHousehold).getProducts().entrySet()){
                            String productInFridge = e.getKey();
                            String plularNameInFridge = productInFridge+"s";
                            if((name.toLowerCase().contains(productInFridge.toLowerCase()) || name.toLowerCase().contains(plularNameInFridge.toLowerCase()))){
                                productCounter++;
                                product.setHasItem(true);
                            }
                        }
                        recipes.get(counter3).getIngredients().add(product);
                    }
                    recipes.get(counter3).setProductCounter(productCounter);
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
            String urldisplay = params[0];
            URLConnection urlConnection = null;
            Bitmap mIcon11 = null;
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
            if(file_size>MAX_SIZE_OF_IMAGE || file_size==SIZE_OF_DEFAULT_IMAGE){
                recipes.get(counter2).setPicBitmap(bitmap);
                return bitmap;
            }
            mIcon11 = BitmapFactory.decodeStream(in);
                recipes.get(counter2).setPicBitmap(mIcon11);
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            notifyDataSetChanged();
            counter2++;
        }
    }

}
