package com.example.ivan.menumanager.model;

import java.util.ArrayList;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Recipes {
    private String name;
    private String description;
    private String picURL;
    private ArrayList<Product> necessaryProduct;

    public Recipes(String name, String description, String picURL, ArrayList<Product> necessaryProduct) {
        this.name = name;
        this.description = description;
        this.picURL = picURL;
        this.necessaryProduct = necessaryProduct;
    }
}
