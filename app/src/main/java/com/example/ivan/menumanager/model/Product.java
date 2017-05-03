package com.example.ivan.menumanager.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Product implements Serializable {

    private int id;
    private String name;
    private int measureID;
    private int foodCategoryID;
    private int expiryTermID;
    private String unit;
    private long purchaseDateInMilli;
    private double quantity;
    private boolean hasItem;



    public Product(String name, int measureID, int foodCategoryID) {
        this.name = name;
        this.measureID = measureID;
        this.foodCategoryID = foodCategoryID;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public boolean getIsHasItem() {
        return hasItem;
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

    public int getExpiryTermID() {
        return this.expiryTermID;
    }

    public void setExpiryTermID(int expiryTermID) {
        this.expiryTermID = expiryTermID;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(double quantity){ this.quantity = quantity; }

    public long getPurchaseDateInMilli() {
        return purchaseDateInMilli;
    }

    public void setPurchaseDateInMilli(long purchaseDateInMilli){
        this.purchaseDateInMilli =  purchaseDateInMilli;
    }


    public boolean isExpired(){
        if(this.purchaseDateInMilli != 0){
            long currentTimeMilli = (long) (Calendar.getInstance().getTimeInMillis());
            if((currentTimeMilli - purchaseDateInMilli) >= getExpiryTermInMilli()){
                return true;
            }
            return false;
        }
        return false;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void fixMeasures(String unit, double quantity){
        //teaspoons, teaspoon, tsp - 5ml , ounce,oz-0.03 ml, inch, tablespoons, tablespoon, tbsp-14.8 ml,
        String units = unit.toLowerCase();
        double quantities = quantity;
        switch (units){
            case "cup":
            case "cups":
                units ="kg";
                quantities=quantities*0.2;
                break;
            case "pound":
            case "lbs":
            case "lb":
                units ="kg";
                quantities=quantities*0.45;
                break;
            case "teaspoon":
            case "teaspoons":
            case "tsp":
                units ="ml";
                quantities=quantities*5;
                break;
            case "ounce":
            case "ounces":
            case "oz":
                units ="ml";
                quantities=quantities*0.03 ;
                break;
            case "tablespoon":
            case "tablespoons":
            case "tbsp":
                units ="ml";
                quantities=quantities*14.8 ;
                break;
            default:
                break;

        }

        this.unit = units;
        this.quantity = round(quantities,2);;

    }

    public long getExpiryTermInMilli(){
        long expiryInMilli = 0;
        switch(expiryTermID){
            case 1:
                expiryInMilli = 120000;
            break;
            case 2:
                expiryInMilli = 86400000;
            break;
            case 3:
                expiryInMilli = 259200000;
            break;
            case 4:
                expiryInMilli = 604800000;
                break;
            case 5:
                expiryInMilli = 1209600000;
                break;
            case 6:
                expiryInMilli = 2592000000L;
                break;
        }
        return expiryInMilli;
    }



    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

}
