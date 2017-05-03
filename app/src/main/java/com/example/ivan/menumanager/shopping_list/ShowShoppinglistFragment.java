package com.example.ivan.menumanager.shopping_list;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ivan.menumanager.R;
import com.example.ivan.menumanager.ViewPageActivity;
import com.example.ivan.menumanager.model.DBManager;
import com.example.ivan.menumanager.model.Product;
import com.example.ivan.menumanager.model.Recipe;
import com.example.ivan.menumanager.recipe.RecipeViewFragment;

import java.util.List;

/**
 * Created by Ivan on 5/2/2017.
 */

public class ShowShoppinglistFragment extends DialogFragment {

    private static final CharSequence PRODUCTS_TO_BUY = "PRODUCTS TO BUY";
    private RecyclerView recyclerView;
    private Button remove;
    private Button showRecipe;
    private List<Product> products;
    private List<Recipe> recipes;
    private int position;
    public ShowShoppinglistFragment (List<Product> products, int position){
        this.products = products;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        recipes = DBManager.households.get(DBManager.currentHousehold).getRecipes();

        View dialog = inflater.inflate(R.layout.show_shoppinglist_fragment, container, false);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.show_shoppinglist_recycler);
        remove = (Button) dialog.findViewById(R.id.remove_button);
        showRecipe = (Button) dialog.findViewById(R.id.show_recipe_button);
        showRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPageActivity myActivity = (ViewPageActivity) getActivity();
                RecipeViewFragment recipeViewFragment = new RecipeViewFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                recipeViewFragment.setArguments(bundle);
                recipeViewFragment.show(myActivity.getSupportFragmentManager(), "shoppingView");
            }
        });
        ShowShoppinglistAdapter adapter = new ShowShoppinglistAdapter(getActivity(),products );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return dialog;
    }


}
