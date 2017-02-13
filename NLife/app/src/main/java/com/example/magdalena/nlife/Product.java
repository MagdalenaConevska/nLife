package com.example.magdalena.nlife;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Marija on 2/6/2017.
 */

public class Product implements Parcelable{

    String name;
    int ndbno;
    ArrayList<Nutrient> nutrienti;
    int amount;

    public Product(){}

    public Product(String name, int ndbno, int amount){

        this.name = name;
        this.ndbno = ndbno;
        nutrienti = new ArrayList();
        this.amount = amount;
    }

    protected Product(Parcel in) {
        name = in.readString();
        ndbno = in.readInt();
        nutrienti = in.createTypedArrayList(Nutrient.CREATOR);
        amount = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(ndbno);
        parcel.writeTypedList(nutrienti);
        parcel.writeInt(amount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String []parts = name.split(",");
        sb.append(parts[0]);
        sb.append(" ");
        sb.append(amount);
        sb.append(" g");
        return sb.toString();
    }
}
