package com.example.magdalena.nlife;

import java.util.ArrayList;

/**
 * Created by Marija on 2/6/2017.
 */

public class Product {

    String name;
    int ndbno;
    ArrayList<Nutrient> nutrienti;

    public Product(){}

    public Product(String name, int ndbno){

        this.name = name;
        this.ndbno = ndbno;
        nutrienti = new ArrayList();
    }
}
