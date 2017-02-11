package com.example.magdalena.nlife;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Magdalena on 2/8/2017.
 */

public class Tuple implements Parcelable{

    public String name;
    public String date;
    public String id;
    public int quantity;
    public double protein;
    public double lipid;
    public double carbohydrate;
    public double glucose;
    public double calcium;
    public double iron;
    public double magnesium;
    public double zinc;
    public double vitaminC;
    public double thiamin;
    public double ribofavin;
    public double niacin;
    public double vitaminB6;
    public double vitaminB12;
    public double vitaminA;
    public double vitaminD;
    public double vitaminE;

    public Tuple(){


    }

    public Tuple(String name, String date, String id, int quantity, double protein, double lipid, double carbohydrate, double glucose, double calcium, double iron, double magnesium, double zinc, double vitaminC, double thiamin, double ribofavin, double niacin, double vitaminB6, double vitaminB12, double vitaminA, double vitaminD, double vitaminE) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.quantity = quantity;
        this.protein = protein;
        this.lipid = lipid;
        this.carbohydrate = carbohydrate;
        this.glucose = glucose;
        this.calcium = calcium;
        this.iron = iron;
        this.magnesium = magnesium;
        this.zinc = zinc;
        this.vitaminC = vitaminC;
        this.thiamin = thiamin;
        this.ribofavin = ribofavin;
        this.niacin = niacin;
        this.vitaminB6 = vitaminB6;
        this.vitaminB12 = vitaminB12;
        this.vitaminA = vitaminA;
        this.vitaminD = vitaminD;
        this.vitaminE = vitaminE;
    }

    protected Tuple(Parcel in) {
        name = in.readString();
        date = in.readString();
        id = in.readString();
        quantity = in.readInt();
        protein = in.readDouble();
        lipid = in.readDouble();
        carbohydrate = in.readDouble();
        glucose = in.readDouble();
        calcium = in.readDouble();
        iron = in.readDouble();
        magnesium = in.readDouble();
        zinc = in.readDouble();
        vitaminC = in.readDouble();
        thiamin = in.readDouble();
        ribofavin = in.readDouble();
        niacin = in.readDouble();
        vitaminB6 = in.readDouble();
        vitaminB12 = in.readDouble();
        vitaminA = in.readDouble();
        vitaminD = in.readDouble();
        vitaminE = in.readDouble();
    }

    public static final Creator<Tuple> CREATOR = new Creator<Tuple>() {
        @Override
        public Tuple createFromParcel(Parcel in) {
            return new Tuple(in);
        }

        @Override
        public Tuple[] newArray(int size) {
            return new Tuple[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getProtein() {
        return protein;
    }

    public double getLipid() {
        return lipid;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getGlucose() {
        return glucose;
    }

    public double getCalcium() {
        return calcium;
    }

    public double getIron() {
        return iron;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public double getZinc() {
        return zinc;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public double getThiamin() {
        return thiamin;
    }

    public double getRibofavin() {
        return ribofavin;
    }

    public double getNiacin() {
        return niacin;
    }

    public double getVitaminB6() {
        return vitaminB6;
    }

    public double getVitaminB12() {
        return vitaminB12;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public double getVitaminD() {
        return vitaminD;
    }

    public double getVitaminE() {
        return vitaminE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setLipid(double lipid) {
        this.lipid = lipid;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public void setMagnesium(double magnesium) {
        this.magnesium = magnesium;
    }

    public void setZinc(double zinc) {
        this.zinc = zinc;
    }

    public void setVitaminC(double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public void setThiamin(double thiamin) {
        this.thiamin = thiamin;
    }

    public void setRibofavin(double ribofavin) {
        this.ribofavin = ribofavin;
    }

    public void setNiacin(double niacin) {
        this.niacin = niacin;
    }

    public void setVitaminB6(double vitaminB6) {
        this.vitaminB6 = vitaminB6;
    }

    public void setVitaminB12(double vitaminB12) {
        this.vitaminB12 = vitaminB12;
    }

    public void setVitaminA(double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public void setVitaminD(double vitaminD) {
        this.vitaminD = vitaminD;
    }

    public void setVitaminE(double vitaminE) {
        this.vitaminE = vitaminE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(id);
        dest.writeInt(quantity);
        dest.writeDouble(protein);
        dest.writeDouble(lipid);
        dest.writeDouble(carbohydrate);
        dest.writeDouble(glucose);
        dest.writeDouble(calcium);
        dest.writeDouble(iron);
        dest.writeDouble(magnesium);
        dest.writeDouble(zinc);
        dest.writeDouble(vitaminC);
        dest.writeDouble(thiamin);
        dest.writeDouble(ribofavin);
        dest.writeDouble(niacin);
        dest.writeDouble(vitaminB6);
        dest.writeDouble(vitaminB12);
        dest.writeDouble(vitaminA);
        dest.writeDouble(vitaminD);
        dest.writeDouble(vitaminE);
    }
}