package com.example.ivan.menumanager.model;

import java.io.Serializable;
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


    //for visualize
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }





//
//    private int id;
//    private String name;
//    private int measureID;
//    private int foodCategoryID;
//    private int expiryTermID;
//    private String unit;
//    private long purchaseDateInMilli;
//    private double quantity;
//
//
//
//    public Product(String name, int measureID, int foodCategoryID) {
//        this.name = name;
//        this.measureID = measureID;
//        this.foodCategoryID = foodCategoryID;
//    }
//
//    public int getId(){
//        return this.id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public int getMeasureID() {
//        return this.measureID;
//    }
//
//    public void setMeasure(int measure){
//        this.measureID = measure;
//    }
//
//    public int getFoodCategoryID() {
//        return this.foodCategoryID;
//    }
//
//    public void setFoodCategory(int foodCategory){
//        this.foodCategoryID = foodCategory;
//    }
//
//    public int getExpiryTermID() {
//        return this.expiryTermID;
//    }
//
//    public void setExpiryTermID(int expiryTermID) {
//        this.expiryTermID = expiryTermID;
//    }
//
//    public double getQuantity() {
//        return this.quantity;
//    }
//
//    public void setQuantity(double quantity){ this.quantity = quantity; }
//
//    public long getPurchaseDateInMilli() {
//        return purchaseDateInMilli;
//    }
//
//    public void setPurchaseDateInMilli(long purchaseDateInMilli){
//        this.purchaseDateInMilli =  purchaseDateInMilli;
//    }
//
//
//    public boolean isExpired(){
//        if(this.purchaseDateInMilli != 0){
//            long currentTimeMilli = (long) (Calendar.getInstance().getTimeInMillis());
//            if((currentTimeMilli - purchaseDateInMilli) >= getExpiryTermInMilli()){
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
//
//
//    public long getExpiryTermInMilli(){
//        long expiryInMilli = 0;
//        switch(expiryTermID){
//            case 1:
//                expiryInMilli = 120000;
//                break;
//            case 2:
//                expiryInMilli = 86400000;
//                break;
//            case 3:
//                expiryInMilli = 259200000;
//                break;
//            case 4:
//                expiryInMilli = 604800000;
//                break;
//            case 5:
//                expiryInMilli = 1209600000;
//                break;
//            case 6:
//                expiryInMilli = 2592000000L;
//                break;
//        }
//        return expiryInMilli;
//    }
//
//
//    //for visualize
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public String getUnit() {
//        return unit;
//    }

}
