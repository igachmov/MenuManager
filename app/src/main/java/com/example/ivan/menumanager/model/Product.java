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
    private int purchaseDateMinutes;
    boolean expired;
    private double quantity;



    public Product(String name, int measureID, int foodCategoryID) {

        this.name = name;
        this.measureID = measureID;
        this.foodCategoryID = foodCategoryID;
        if(this.getPurchaseDateMinutes() != 0){
            this.expired = isExpired();
        }

    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getMeasureID() {
        return this.measureID;
    }

    public void setMeasure(int measure){
        this.measureID = measure;
    }

    public int getFoodCategoryID() {
        return this.foodCategoryID;
    }

    public void setFoodCategory(int foodCategory){
        this.foodCategoryID = foodCategory;
    }

    public String getExpiryTerm() {
        return this.expiryTerm;
    }

    public void setExpiryTerm(String expiryTerm){
        this.expiryTerm = expiryTerm;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity){ this.quantity = quantity; }


    public void setPurchaseDateMinutes(int purchaseDate){
        //we get current time in minutes when
        //pressing button add product to fridge and set it here
        this.purchaseDateMinutes =  purchaseDate;
    }

    public int getPurchaseDateMinutes() {
        return purchaseDateMinutes;
    }

    private boolean isExpired(){
        int currentTimeMinutes = (int) ((System.currentTimeMillis()/1000)/60);
        if((currentTimeMinutes - purchaseDateMinutes) >= expiryTermToMinutes()){
            return true;
        }
        return false;
    }


    private int expiryTermToMinutes(){
        int expiryInMinutes = 0;
        switch(expiryTerm){
            case "5 minutes":
                expiryInMinutes = 5;
            break;
            case "1 day":
                expiryInMinutes = 1440;
            break;
            case "3 days":
                expiryInMinutes = 4320;
            break;
            case "7 days":
                expiryInMinutes = 10080;
                break;
            case "2 weeks":
                expiryInMinutes = 20160;
                break;
            case "1 month":
                expiryInMinutes = 43200;
                break;
        }
        return expiryInMinutes;
    }

}
