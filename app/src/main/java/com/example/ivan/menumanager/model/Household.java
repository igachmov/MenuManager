package com.example.ivan.menumanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class Household {

    private String name;
    private HashMap<String, Product> products;
    private ArrayList<Recipe> recipes;
    private ArrayList<ShoppingList> shoppingLists;

    public Household (String name){
        this.name = name;
        this.products = new HashMap<>();
        this.recipes = new ArrayList<>();
        this.shoppingLists = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Product> getProducts() {
        return Collections.unmodifiableMap(this.products);
    }

    public void addProducts(ArrayList<Product> products) {
        for (Product product: products) {
            if(this.products.containsKey(product.getName())){
                this.products.get(product.getName()).setQuantity(product.getQuantity());
            }
            else{
                this.products.put(product.getName(), new Product(product.getName(),0,0));
            }
        }
    }

    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(this.recipes);
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public List<ShoppingList> getShoppingLists() {
        return Collections.unmodifiableList(shoppingLists);
    }

    public void addShoppingLists(ShoppingList shoppingList) {
        this.shoppingLists.add(shoppingList);
    }
}
