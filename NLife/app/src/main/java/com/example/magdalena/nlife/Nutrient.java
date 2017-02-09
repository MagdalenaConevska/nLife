package com.example.magdalena.nlife;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marija on 2/6/2017.
 */

public class Nutrient implements Parcelable {
    String name;
    Double recorded;
    String unit;

    public Nutrient(){}

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" : ");
        sb.append(recorded);
        sb.append(" ");
        sb.append(unit);
        return sb.toString();
    }

    public Nutrient(String name, Double recorded, String unit){

        this.name = name;
        this.recorded = recorded;
        this.unit = unit;
    }



    protected Nutrient(Parcel in) {
        name = in.readString();
        unit = in.readString();
        recorded=in.readDouble();
    }

    public static final Creator<Nutrient> CREATOR = new Creator<Nutrient>() {
        @Override
        public Nutrient createFromParcel(Parcel in) {
            return new Nutrient(in);
        }

        @Override
        public Nutrient[] newArray(int size) {
            return new Nutrient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeDouble(recorded);
    }


}