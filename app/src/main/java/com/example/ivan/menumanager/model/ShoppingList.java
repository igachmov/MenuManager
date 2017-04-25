package com.example.ivan.menumanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class ShoppingList {

    private int id;
    private String name;

    //frequent product adding
    private ArrayList<Product> productsToBuy;

    public ShoppingList(String name, ArrayList<Product> productsToBuy){
        this.name = name;
        this.productsToBuy = productsToBuy;
    }


    public String getName(){
        return  this.name;
    }

    public  int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //update product values regardles some of them have changed
    public void updateProduct(Product product) {
        for (int i = 0; i < productsToBuy.size(); i++) {
            if (productsToBuy.get(i).getName() == product.getName()) {
                productsToBuy.get(i).setQuantity(product.getQuantity());
                productsToBuy.get(i).setMeasure(product.getMeasureID());
                productsToBuy.get(i).setFoodCategory(product.getFoodCategoryID());
            }
        }
    }


    public void addProduct(Product product){
            productsToBuy.add(product);
    }


    public void removeProduct(Product product){
        for (int i = 0; i < productsToBuy.size(); i++) {
            if (productsToBuy.get(i).getName() == product.getName()) {
                productsToBuy.remove(productsToBuy.get(i));
            }
        }
    }


    public List<Product> getProductsToBuy() {
        return Collections.unmodifiableList(this.productsToBuy);
    }

}
