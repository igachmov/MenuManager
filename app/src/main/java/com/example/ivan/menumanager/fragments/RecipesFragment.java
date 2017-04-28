package com.example.ivan.menumanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ivan.menumanager.R;

import com.example.ivan.menumanager.adapters.RecipeSearchAdapter;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Household;
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
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {


    private ImageView searchButton;
    private TextView recipeName;

    private RecyclerView recyclerView;
    private EditText searchName;
    protected ArrayList<Recipe> recipeData ;
    private LinearLayout fridgeLayout;
    private int counter = 0;
    private int counter2 = 0;
    private int counter3 = 0;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private String  name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        fridgeLayout = (LinearLayout) root.findViewById(R.id.recipe_search_layout);
        searchName = (EditText) root.findViewById(R.id.search_recipe);
        recyclerView = (RecyclerView) root.findViewById(R.id.recipe_search_recyclerview);
        progressBar = (ProgressBar) root.findViewById(R.id.recipe_progress_bar);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.searc_relative_layout);

        searchButton = (ImageView) root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = searchName.getText().toString();
              if(isNetworkAvailable()) {
                  if (name != null && !name.isEmpty()) {
                      counter = 0;
                      counter2 = 0;
                      counter3 = 0;
                      recipeData = new ArrayList<>();
                      RecipeSearchAdapter.recipes = new ArrayList<Recipe>();
                      relativeLayout.setVisibility(View.VISIBLE);
                      progressBar.setVisibility(View.VISIBLE);
                      fridgeLayout.setVisibility(View.GONE);
                      new DownloadRecipeTask().execute(name);
                      Log.e("Ivan", Integer.toString(recipeData.size()));
                      dismissKeyboard(getActivity());
                  } else {
                      Toast.makeText(getActivity(), "Please enter a name for recipe", Toast.LENGTH_SHORT).show();
                  }
              }
              else {
                  Toast.makeText(getActivity(), "There is no internet connection", Toast.LENGTH_SHORT).show();
              }
            }
        });
        return root;
    }

    public  void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }


    public class DownloadRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {
        JSONObject json = null;
        JSONArray jsonArr = null;
        Recipe recipe = null;

        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            String recipeTitle = params[0];
            try {
                Log.e("Ivan", "connection");
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?number=20&query=" + recipeTitle.replace(" ", "+").trim());
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
                jsonArr = json.getJSONArray("results");

                if (jsonArr != null) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String name = jsonObj.getString("title");
                        String id = jsonObj.getString("id");
                        String image = jsonObj.getString("image");
                        String imageURL = ("https://spoonacular.com/recipeImages/" + image);
                        recipe = new Recipe(name, id, imageURL);
                        recipeData.add(recipe);
                    }
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
            return recipeData;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            if(jsonArr.isNull(0)) {
                relativeLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
                fridgeLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Sorry there are no such recipes with that name try again", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < recipeData.size(); i++) {
                String recipeImg = recipeData.get(i).getPicURL();
                new DownloadImageTask().execute(recipeImg);
                String id = recipeData.get(i).getId();
                new DownloadRecipeInstruction().execute(id);
                Log.e("Ivan", "Counter in DownloadTask counter  " + counter + "");
                counter++;
            }
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
                Log.e("Ivan", "Counter in DownloadIngredients " + counter3 + "");
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
                        recipeData.get(counter3).getIngredients().add(product);

                    }
                    recipeData.get(counter3).setProductCounter(productCounter);
                    Log.e("Ivan","Product counter "+counter3+"");

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
            return recipeData;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            counter3++;
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Log.e("Ivan", "Counter in DownloadTask " + counter2 + "");
            String urldisplay = params[0];
            URLConnection urlConnection = null;
            Bitmap mIcon11 = null;
            Bitmap defaul = BitmapFactory.decodeResource(getResources(), R.mipmap.img_default);
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
                recipeData.get(counter2).setPicBitmap(defaul);
                return defaul;
            }
            mIcon11 = BitmapFactory.decodeStream(in);
            recipeData.get(counter2).setPicBitmap(mIcon11);
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            counter2++;
            if (counter2==counter || recipeData.size()==0){
                relativeLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
                fridgeLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                RecipeSearchAdapter adapter = new RecipeSearchAdapter(getActivity(), recipeData);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}