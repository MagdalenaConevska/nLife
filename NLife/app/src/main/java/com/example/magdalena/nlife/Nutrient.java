package com.example.magdalena.nlife;

/**
 * Created by Marija on 2/6/2017.
 */

public class Nutrient {
    String name;
    Double recorded;
    String unit;

    public Nutrient(){}

    public Nutrient(String name, Double recorded, String unit){

        this.name = name;
        this.recorded = recorded;
        this.unit = unit;
    }
}
