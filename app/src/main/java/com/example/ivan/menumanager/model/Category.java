package com.example.ivan.menumanager.model;

/**
 * Created by Vanya on 24.4.2017 г..
 */

public class Category {

    private int id;
    private String name;
    private int image;

    public Category(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
