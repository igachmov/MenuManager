package com.example.ivan.menumanager.fragments;

import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;

import com.example.ivan.menumanager.RecipeActivity;

import com.example.ivan.menumanager.adapters.ProductsRecyclerAdapter;
import com.example.ivan.menumanager.adapters.RecipeSearchAdapter;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    private Button searchButton;
    private TextView recipeName;
    private RecyclerView recyclerView;
    private EditText searchName;
    private ImageView imageView;
    private Bitmap imageBitmap;
    protected ArrayList<Recipe> recipeData ;
    private LinearLayout fridgeLayout;
    private Bitmap bitmapImage;
    private int counter = 0;
    private static int  counter2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_recipes, container, false);

       // recipeName = (TextView) root.findViewById(R.id.recipe_name_tv);
//        imageView = (ImageView) root.findViewById(R.id.recipe_image);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), RecipeActivity.class);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("imageBitmap", byteArray);
                //TODO intent.
//                getActivity().startActivity(intent);
//            }
//        });


        fridgeLayout = (LinearLayout) root.findViewById(R.id.recipe_search_layout);
        searchName = (EditText) root.findViewById(R.id.search_recipe);
        recyclerView = (RecyclerView) root.findViewById(R.id.recipe_search_recyclerview);

        searchButton = (Button) root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter2 = 0;
                counter = 0;
                recipeData = new ArrayList<>();
                String name = searchName.getText().toString();
                new DownloadRecipeTask().execute(name);
                Log.e("Ivan",Integer.toString(recipeData.size()));
                dismissKeyboard(getActivity());
            }
        });
        return root;
    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }


    public class DownloadRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe> >{


        JSONObject json = null;
        JSONArray jsonArr = null;
        Recipe recipe = null;

        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {

            String recipeTitle = params[0];


            try {

                Log.e("Ivan", "connection");
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?query=" + recipeTitle);
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

                if(jsonArr!=null){
                    for (int i=0;i<jsonArr.length();i++){
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String name = jsonObj.getString("title");
                        String description = jsonObj.getString("readyInMinutes");
                        String image = jsonObj.getString("image");
                        String imageURL = ("https://spoonacular.com/recipeImages/" + image);
                        recipe = new Recipe(name, description, imageURL);
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
            for (int i = 0; i <recipeData.size(); i++) {
                String recipeImg = recipeData.get(i).getPicURL();
                new DownloadImageTask().execute(recipeImg);
                Log.e("Ivan","Counter in DownloadTask counter  " + counter +"");
                counter++;
            }



        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Log.e("Ivan","Counter in DownloadTask " + counter2+"");
            String urldisplay = params[0];
            Bitmap mIcon11 = null;
            InputStream in = null;
            try {
                in = new URL(urldisplay).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mIcon11 = BitmapFactory.decodeStream(in);

                recipeData.get(counter2).setPicBitmap(mIcon11);
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            counter2++;
            RecipeSearchAdapter adapter = new RecipeSearchAdapter(getActivity(),recipeData);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

}