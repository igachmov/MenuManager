package com.example.ivan.menumanager.recipe;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ivan.menumanager.R;

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
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    private ImageView searchButton;
    private RecyclerView recyclerView;
    private EditText searchName;
    private static ArrayList<Recipe> recipeData ;
    private LinearLayout fridgeLayout;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private String  name;
    private Bitmap defaul ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        fridgeLayout = (LinearLayout) root.findViewById(R.id.recipe_search_layout);
        searchName = (EditText) root.findViewById(R.id.search_recipe);
        recyclerView = (RecyclerView) root.findViewById(R.id.recipe_search_recyclerview);
        progressBar = (ProgressBar) root.findViewById(R.id.recipe_progress_bar);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.searc_relative_layout);
        defaul =  BitmapFactory.decodeResource(getResources(), R.mipmap.img_default);

        if(recipeData != null && recipeData.size()!=0){
            Log.e("Fragment",recipeData.size()+"");
            relativeLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
            fridgeLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            Log.e("Fragment"," before new "+RecipeSearchAdapter.recipes.size()+"");
            RecipeSearchAdapter.recipes = new ArrayList<Recipe>();
            Log.e("Fragment"," after new "+RecipeSearchAdapter.recipes.size()+"");
            RecipeSearchAdapter adapter = new RecipeSearchAdapter(getActivity(), recipeData,defaul);
            Log.e("Fragment"," visualiziation "+RecipeSearchAdapter.recipes.size()+"");
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }

        searchButton = (ImageView) root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = searchName.getText().toString();
              if(isNetworkAvailable()) {
                  if (name != null && !name.isEmpty()) {
                      root.setBackgroundResource(R.drawable.z_recipe_image_blurry);
                      recipeData = new ArrayList<>();
                      RecipeSearchAdapter.recipes = new ArrayList<Recipe>();
                      relativeLayout.setVisibility(View.GONE);
                      progressBar.setVisibility(View.GONE);
                      fridgeLayout.setVisibility(View.VISIBLE);
                      recyclerView.setVisibility(View.VISIBLE);
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
                Toast.makeText(getActivity(), "No information found on search criteria\nPlease try again", Toast.LENGTH_SHORT).show();
            }
            RecipeSearchAdapter adapter = new RecipeSearchAdapter(getActivity(), recipeData,defaul);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}