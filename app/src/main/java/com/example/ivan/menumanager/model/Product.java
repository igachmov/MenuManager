package com.example.ivan.menumanager.model;

/**
 * Created by Ivan on 4/4/2017.
 */

public class Product {
    public enum Measure{KG,GRAM,LITЕR,MILLILITER,PIECES};


    private String name;
    private int pic;
    private double quantity;
    private String date;
    private Measure measure;


    public Product(String name, int pic, double quantity, String date, Measure measure) {
        this.name = name;
        this.pic = pic;
        this.quantity = quantity;
        this.date = date;
        this.measure = measure;

    }

    public String getName() {
        return name;
    }

    public int getPic() {
        return pic;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity){ this.quantity += quantity; }

    public String getDate() {
        return date;
    }

    public Measure getMeasure() {
        return measure;
    }


}
