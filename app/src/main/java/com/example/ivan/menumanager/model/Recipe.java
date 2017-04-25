package com.example.ivan.menumanager.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Recipe implements Serializable {
    private String name;
    private String id;
    private String picURL;
    private Bitmap picBitmap;
    private ArrayList<Product> ingridients;

    public Recipe(String name, String id, String picURL) {
        this.name = name;
        this.id = id;
        this.picURL = picURL;
        this.ingridients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPicURL() {
        return picURL;
    }

    public List<Product> getIngridients() {
        return Collections.unmodifiableList(this.ingridients);
    }

    public void setPicBitmap(Bitmap picBitmap) {
        this.picBitmap = picBitmap;
    }

    public Bitmap getPicBitmap() {
        return picBitmap;
    }
}
