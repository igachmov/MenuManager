package com.example.ivan.menumanager.model;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Product {

    private int id;
    private String name;
    private int measureID;
    private int foodCategoryID;
    private String expiryTerm;
    private String unit;
    private int purchaseDateMinutes;
    boolean expired;
    private double quantity;


    public Product(String name, int measureID, int foodCategoryID) {
        this.name = name;
        this.measureID = measureID;
        this.foodCategoryID = foodCategoryID;
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public int getMeasureID() {
        return measureID;
    }

    public int getFoodCategoryID() {
        return foodCategoryID;
    }

    public String getExpiryTerm() {
        return expiryTerm;
    }

    public int getPurchaseDateMinutes() {
        return purchaseDateMinutes;
    }

    public boolean isExpired() {
        return expired;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity){ this.quantity += quantity; }

    public void setId(int id) {
        this.id = id;
    }
    //for visualize
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
