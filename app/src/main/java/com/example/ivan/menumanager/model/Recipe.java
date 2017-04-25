package com.example.ivan.menumanager.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Recipe {
    private int id;
    private String name;
    private String description;
    private String picURL;
    private Bitmap picBitmap;

    //frequent product add and get
    private ArrayList<Product> ingredients;

    public Recipe(String name, String description, String picURL) {
        this.name = name;
        this.description = description;
        this.picURL = picURL;
        this.ingredients = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicBitmap(Bitmap picBitmap) {
        this.picBitmap = picBitmap;
    }

    public Bitmap getPicBitmap() {
        return picBitmap;
    }

    public void addIngredient(Product ingredient){
        this.ingredients.add(ingredient);
    }

    public List<Product> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }


}
