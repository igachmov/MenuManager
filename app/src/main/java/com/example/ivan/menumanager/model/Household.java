package com.example.ivan.menumanager.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class
Household {

    private int id;
    private String name;

    //to show all products in alpahabetical order
    private TreeMap<String, Product> products;
    private ArrayList<Recipe> favouriteRecipes;
    private ArrayList<ShoppingList> shoppingLists;

    public Household (String name){
        this.name = name;
        this.products = new TreeMap<>();
        this.favouriteRecipes = new ArrayList<>();
        this.shoppingLists = new ArrayList<>();
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Product> getProducts() {
        return Collections.unmodifiableMap(this.products);
    }

    public void addProduct(Product product) {


        this.products.put(product.getName(), product);

    }

    public boolean removeProduct(String productName){
        if(this.products.containsKey(productName)){
            this.products.remove(productName);
            return true;
        }
        return false;
    }

    public boolean updateProduct(String productName, double quantity){
        double currentQuantity = this.products.get(productName).getQuantity();
        this.products.get(productName).setQuantity(currentQuantity - quantity);
        return true;
    }



    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(this.favouriteRecipes);
    }

    public void addRecipe(Recipe recipe) {
        this.favouriteRecipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        this.favouriteRecipes.remove(recipe);
    }

    public List<ShoppingList> getShoppingLists() {
        return Collections.unmodifiableList(this.shoppingLists);
    }

    public void addShoppingLists(ShoppingList shoppingList) {
        this.shoppingLists.add(shoppingList);
    }

    public void removeShoppingLists(ShoppingList shoppingList) {
        this.shoppingLists.remove(shoppingList);
    }


}
