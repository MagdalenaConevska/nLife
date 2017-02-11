package com.example.magdalena.nlife;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Magdalena on 2/9/2017.
 */

public class getDataFromSQLite extends AsyncTask<Void,Void,Void> {

    Context context;
    String daySelectionFilter;

    public getDataFromSQLite(Context c,String d) {
        context = c;
        this.daySelectionFilter=d;
    }



    @Override
    protected Void doInBackground(Void... params) {

        ArrayList<Tuple> torki = new ArrayList<Tuple>();

        NutrientsDBHelper dbHelper = new NutrientsDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String projection[] = {NutrientDBEntry.COLUMN_PRODUCT_NAME, NutrientDBEntry.COLUMN_DATE,
                NutrientDBEntry.COLUMN_NDBNO, NutrientDBEntry.COLUMN_QUANTITY, NutrientDBEntry.COLUMN_PROTEIN,
                NutrientDBEntry.COLUMN_TOTAL_LIPID, NutrientDBEntry.COLUMN_CARBOHYDRATE, NutrientDBEntry.COLUMN_GLUCOSE,
                NutrientDBEntry.COLUMN_CALCIUM, NutrientDBEntry.COLUMN_IRON, NutrientDBEntry.COLUMN_MAGNESIUM,
                NutrientDBEntry.COLUMN_ZINC, NutrientDBEntry.COLUMN_VITAMIN_C, NutrientDBEntry.COLUMN_THIAMIN,
                NutrientDBEntry.COLUMN_RIBOFLAVIN, NutrientDBEntry.COLUMN_NIACIN, NutrientDBEntry.COLUMN_VITAMIN_B6,
                NutrientDBEntry.COLUMN_VITAMIN_B12, NutrientDBEntry.COLUMN_VITAMIN_A, NutrientDBEntry.COLUMN_VITAMIN_D,
                NutrientDBEntry.COLUMN_VITAMIN_E};


        String selection = NutrientDBEntry.COLUMN_DATE + " = ?";

        String[] selectionArgs = new String[1];

        //ova treba da go prezememe od spinnerot- i da dodavame ime na den
        selectionArgs[0] = daySelectionFilter;

        //selectionArgs[0] = "09.02.2018";

        Cursor cursor = db.query(NutrientDBEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int size = cursor.getCount();
            for (int i = 0; i < size; i++) {
                Tuple torka = new Tuple(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                        cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10), cursor.getDouble(11),
                        cursor.getDouble(12), cursor.getDouble(13), cursor.getDouble(14), cursor.getDouble(15), cursor.getDouble(16),
                        cursor.getDouble(17), cursor.getDouble(18), cursor.getDouble(19), cursor.getDouble(20));
                torki.add(torka);
                //  Log.d("od baza",torka.getName()+" "+torka.getDate());
                cursor.moveToNext();
            }
        }
        // else Log.d("nema ","Monday");

        Intent intent = new Intent();
        intent.setAction("GetDailyValues");
        intent.putExtra("Nutrients2",torki);
        context.sendBroadcast(intent);


        return null;

    }

}

