package com.example.ivan.menumanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vanya on 14.4.2017 г..
 */

public class ShoppingList {

    private String name;
    private boolean isConsumed;
    private ArrayList<Product> productsToBuy;

    public ShoppingList(String name, ArrayList<Product> productsToBuy){
        this.name = name;
        this.isConsumed = false;
        this.productsToBuy = productsToBuy;
    }


    public String getName(){
        return  this.name;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public void changeStatus(){
        this.isConsumed = true;
    }

    public List<Product> getProductsToBuy() {
        return Collections.unmodifiableList(this.productsToBuy);
    }

}
