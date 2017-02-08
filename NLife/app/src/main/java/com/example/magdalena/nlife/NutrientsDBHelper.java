package com.example.magdalena.nlife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Toni on 07.02.2017.
 */

public class NutrientsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "nLife_Nutrients.db";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NutrientDBEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + NutrientDBEntry.TABLE_NAME + " (" +
            NutrientDBEntry.COLUMN_DATE + " TEXT NOT NULL," +
            NutrientDBEntry.COLUMN_NDBNO + " TEXT NOT NULL," +
            NutrientDBEntry.COLUMN_PRODUCT_NAME + " TEXT," +
            NutrientDBEntry.COLUMN_QUANTITY + " INTEGER," +
            NutrientDBEntry.COLUMN_ENERGY + " REAL," +
            NutrientDBEntry.COLUMN_PROTEIN + " REAL," +
            NutrientDBEntry.COLUMN_TOTAL_LIPID + " REAL," +
            NutrientDBEntry.COLUMN_CARBOHYDRATE + " REAL," +
            NutrientDBEntry.COLUMN_GLUCOSE + " REAL," +
            NutrientDBEntry.COLUMN_CALCIUM + " REAL," +
            NutrientDBEntry.COLUMN_IRON + " REAL," +
            NutrientDBEntry.COLUMN_MAGNESIUM + " REAL," +
            NutrientDBEntry.COLUMN_ZINC + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_C + " REAL," +
            NutrientDBEntry.COLUMN_THIAMIN + " REAL," +
            NutrientDBEntry.COLUMN_RIBOFLAVIN + " REAL," +
            NutrientDBEntry.COLUMN_NIACIN + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_B6 + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_B12 + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_A + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_D + " REAL," +
            NutrientDBEntry.COLUMN_VITAMIN_E + " REAL," +
            "PRIMARY KEY (" + NutrientDBEntry.COLUMN_DATE + "," + NutrientDBEntry.COLUMN_NDBNO + "))";

    public NutrientsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}