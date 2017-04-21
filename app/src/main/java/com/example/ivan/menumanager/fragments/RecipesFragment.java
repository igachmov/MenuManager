package com.example.ivan.menumanager.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.RecipeActivity;
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
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    private Button searchButton;
    private TextView recipeName;
    private TextView recipeDescription;
    private ImageView imageView;
    private Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipeName = (TextView) root.findViewById(R.id.recipe_name_tv);
        recipeDescription = (TextView) root.findViewById(R.id.descripion_tv);
        imageView = (ImageView) root.findViewById(R.id.recipe_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("imageBitmap", byteArray);
                intent.
                getActivity().startActivity(intent);
            }
        });


        searchButton = (Button) root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchName = (EditText) root.findViewById(R.id.search_recipe);
                String name = searchName.getText().toString();
                new DownloadRecipeTask().execute(name);
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }


    class DownloadRecipeTask extends AsyncTask<String, Void, Recipe> {

        JSONObject json = null;
        JSONArray jsonArr = null;
        Recipe recipe = null;

        @Override
        protected Recipe doInBackground(String... params) {

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
                JSONObject jsonObj = jsonArr.getJSONObject(0);
                String name = jsonObj.getString("title");


                String description = jsonObj.getString("readyInMinutes");
                String image = jsonObj.getString("image");
                String imageURL = ("https://spoonacular.com/recipeImages/" + image);
                recipe = new Recipe(name, description, imageURL);
                Log.e("ivan", imageURL);

            } catch (MalformedURLException e) {
                Log.e("Ivan", "Malformed");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("Ivan", "ProtocolException");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipe;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            if (recipe != null) {
                recipeName.setText(recipe.getName());
                recipeDescription.setText(recipe.getDescription());
                new DownloadImageTask().execute(recipe.getPicURL());
            }
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... params) {
                String urldisplay = params[0];
                Bitmap mIcon11 = null;
                InputStream in = null;
                try {
                    in = new URL(urldisplay).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIcon11 = BitmapFactory.decodeStream(in);
                return mIcon11;


            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageBitmap = bitmap;
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

}
