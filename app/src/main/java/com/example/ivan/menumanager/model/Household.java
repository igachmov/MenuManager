package com.example.ivan.menumanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class Household {

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

    //add product
    // manually (predefined view should have quantity edit text)
    // or through shoppinglist
    public void addProduct(Product product) {

            if(this.products.containsKey(product.getName())){
                this.products.get(product.getName()).setQuantity(product.getQuantity());
            }
            else{
                this.products.put(product.getName(), new Product(product.getName(), product.getMeasureID(), product.getFoodCategoryID()));
            }
    }
    //update a single product
    public void updateProduct(Product product) {
        if(this.products.containsKey(product.getName())){
            Product productToUpdate = this.products.get(product.getName());
            productToUpdate.setQuantity(product.getQuantity());
            productToUpdate.setExpiryTerm(product.getExpiryTerm());
            productToUpdate.setFoodCategory(product.getFoodCategoryID());
            productToUpdate.setMeasure(product.getMeasureID());
        }
    }

    //remove product manually or through recipe
    public void removeProduct(Product product){
        if(this.products.containsKey(product.getName())){
            this.products.remove(product.getName());
        }
    }

    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(this.favouriteRecipes);
    }

    public void addRecipe(Recipe recipe) {
        this.favouriteRecipes.add(recipe);
    }

    public List<ShoppingList> getShoppingLists() {
        return Collections.unmodifiableList(this.shoppingLists);
    }

    public void addShoppingLists(ShoppingList shoppingList) {
        this.shoppingLists.add(shoppingList);
    }

    //show products in one category in alphabetical order
    public TreeMap<Integer, TreeMap<String, Product>> orderByCategory(){
        TreeMap<Integer, TreeMap<String, Product>> orderedByCategory = new TreeMap<>();
        for (Map.Entry<String, Product> product: this.products.entrySet()) {
            Product productToPut = product.getValue();
            int category= productToPut.getFoodCategoryID();
            if(!orderedByCategory.containsKey(category)){
                orderedByCategory.put(category, new TreeMap<String, Product>());
            }
            orderedByCategory.get(category).put(productToPut.getName(), productToPut);
        }
        return orderedByCategory;
    }
}
