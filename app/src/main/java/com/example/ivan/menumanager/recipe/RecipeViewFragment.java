package com.example.ivan.menumanager.recipe;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.model.Product;

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
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeViewFragment extends Fragment {


    private RecyclerView recyclerView;
    protected ArrayList<Product> productData ;
    private ImageView recipeImage;
    private TextView recipeText;
    private TextView recipeName;
    private String id;
    private String name;
    private Bitmap bitmap;
    private String instructions;
    private Button checkUrlButton;
    private String sourceUrl;

    public RecipeViewFragment() {

    }

    public RecipeViewFragment(String id, String name, Bitmap bitmap) {
        this.id = id;
        this.name = name;
        this.bitmap = bitmap;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productData = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        recipeImage = (ImageView) root.findViewById(R.id.recipe_ingr_image);
        recipeText = (TextView) root.findViewById(R.id.recipe_ingr_text);
        recipeText.setMovementMethod(new ScrollingMovementMethod());
        recipeName = (TextView) root.findViewById(R.id.recipe_ingr_name);
        checkUrlButton = (Button) root.findViewById(R.id.url_button);
        recyclerView = (RecyclerView) root.findViewById(R.id.recipe_view_recyclerview);
        new DownloadRecipeInstruction().execute(id);
        return root;
    }

    public class DownloadRecipeInstruction extends AsyncTask<String, Void, ArrayList<Product>> {
        JSONObject json = null;
        JSONArray jsonArr = null;
        Product product = null;

        @Override
        protected ArrayList<Product> doInBackground(String... params) {
            String recipeId = params[0];
            try {
                Log.e("Ivan", "connection");
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

                Log.e("Ivan",sourceUrl);
                if (jsonArr != null) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String name = jsonObj.getString("name");
                        String amount = jsonObj.getString("amount");
                        String unit = jsonObj.getString("unit");
                        product = new Product(name, 0, 0);
                        DecimalFormat df = new DecimalFormat("#.##");
                        String dx=df.format(Double.parseDouble(amount));
                        product.setQuantity(Double.parseDouble(dx));
                        product.setUnit(unit);
                        productData.add(product);
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
            return productData;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> productData) {
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

            recipeName.setText(name);
            recipeImage.setImageBitmap(bitmap);
            IngredientAdapter adapter = new IngredientAdapter(getActivity(), productData);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

}
