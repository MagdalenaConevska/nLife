package com.example.magdalena.nlife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Toni on 07.02.2017.
 */

public class NutrientsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="nLife_Nutrients.db";
    private static final String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS " + NutrientDBEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES="";

    public NutrientsDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
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
