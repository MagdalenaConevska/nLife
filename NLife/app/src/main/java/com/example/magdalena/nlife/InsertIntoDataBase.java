package com.example.magdalena.nlife;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Magdalena on 2/11/2017.
 */

public class InsertIntoDataBase extends AsyncTask<Void,Void,Void> {

    String ime,den,id;
    int kolichina;
    ArrayList<Nutrient> lista;
    Context context;

    public InsertIntoDataBase(Context c,String i,String d,String id,int kol,ArrayList<Nutrient> l){

        context=c;
        den=d;
        this.id=id;
        kolichina=kol;
        lista=l;
        ime=i;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent();
        intent.setAction("InsertToSQLite");
        context.sendBroadcast(intent);
        Log.d("InsertIntoDatabase","broadcast sent");
    }

    @Override
    protected Void doInBackground(Void... params) {


        double protein=0,lipid=0,carbo=0,glucose=0,calcium=0;
        double iron=0,mg=0,zinc=0,vitC=0,thiamin=0,ribo=0;
        double niacin=0,vitB6=0,vitB12=0,vitA=0,vitD=0,vitE=0;

        for (int i=0;i<lista.size();i++){

            Log.d("Vo for",i+"");

            if(lista.get(i).name.equals("Protein")){

                protein=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Total lipid (fat)")){

                lipid=lista.get(i).recorded;
            }

            else if (lista.get(i).name.equals("Carbohydrate, by difference")){

                carbo=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Glucose (dextrose)")){

                glucose=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Calcium, Ca")){

                calcium=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Iron, Fe")){

                iron=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Magnesium, Mg")){

                mg=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Zinc, Zn")){

                zinc=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin C, total ascorbic acid")){

                vitC=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Thiamin")){

                thiamin=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Riboflavin")){

                ribo=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Niacin")){

                niacin=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin B-6")){

                vitB6=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin B-12")){
                vitB12=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin A, RAE")){

                vitA=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin D")){

                vitD=lista.get(i).recorded;
            }
            else if (lista.get(i).name.equals("Vitamin E (alpha-tocopherol)")){

                vitE=lista.get(i).recorded;
            }


        }

        ContentValues values= new ContentValues();

        values.put(NutrientDBEntry.COLUMN_DATE,den);
        values.put(NutrientDBEntry.COLUMN_NDBNO,id);
        values.put(NutrientDBEntry.COLUMN_PRODUCT_NAME,ime);
        values.put(NutrientDBEntry.COLUMN_QUANTITY,kolichina);


        values.put(NutrientDBEntry.COLUMN_PROTEIN,protein);
        values.put(NutrientDBEntry.COLUMN_TOTAL_LIPID,lipid);
        values.put(NutrientDBEntry.COLUMN_CARBOHYDRATE,carbo);

        values.put(NutrientDBEntry.COLUMN_GLUCOSE,glucose);
        values.put(NutrientDBEntry.COLUMN_CALCIUM,calcium);
        values.put(NutrientDBEntry.COLUMN_IRON,iron);

        values.put(NutrientDBEntry.COLUMN_MAGNESIUM,mg);
        values.put(NutrientDBEntry.COLUMN_ZINC,zinc);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_C,vitC);

        values.put(NutrientDBEntry.COLUMN_THIAMIN,thiamin);
        values.put(NutrientDBEntry.COLUMN_RIBOFLAVIN,ribo);
        values.put(NutrientDBEntry.COLUMN_NIACIN,niacin);

        values.put(NutrientDBEntry.COLUMN_VITAMIN_B6,vitB6);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_B12,vitB12);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_A,vitA);

        values.put(NutrientDBEntry.COLUMN_VITAMIN_D,vitD);
        values.put(NutrientDBEntry.COLUMN_VITAMIN_E,vitE);

        NutrientsDBHelper dbHelper=new NutrientsDBHelper(context); //this=context
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

        try {
            dbWrite.insertOrThrow(NutrientDBEntry.TABLE_NAME, null, values);
            Log.d("Insert was"," successfull");

        }
        catch (SQLException e){

            Log.d("Insert was"," not successfull");
            Log.d("Now lets try "," update");

            Tuple conflictTuple=null;

           // dbWrite.close();
           // SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
            String projection[] = {NutrientDBEntry.COLUMN_PRODUCT_NAME, NutrientDBEntry.COLUMN_DATE,
                    NutrientDBEntry.COLUMN_NDBNO, NutrientDBEntry.COLUMN_QUANTITY, NutrientDBEntry.COLUMN_PROTEIN,
                    NutrientDBEntry.COLUMN_TOTAL_LIPID, NutrientDBEntry.COLUMN_CARBOHYDRATE, NutrientDBEntry.COLUMN_GLUCOSE,
                    NutrientDBEntry.COLUMN_CALCIUM, NutrientDBEntry.COLUMN_IRON, NutrientDBEntry.COLUMN_MAGNESIUM,
                    NutrientDBEntry.COLUMN_ZINC, NutrientDBEntry.COLUMN_VITAMIN_C, NutrientDBEntry.COLUMN_THIAMIN,
                    NutrientDBEntry.COLUMN_RIBOFLAVIN, NutrientDBEntry.COLUMN_NIACIN, NutrientDBEntry.COLUMN_VITAMIN_B6,
                    NutrientDBEntry.COLUMN_VITAMIN_B12, NutrientDBEntry.COLUMN_VITAMIN_A, NutrientDBEntry.COLUMN_VITAMIN_D,
                    NutrientDBEntry.COLUMN_VITAMIN_E};

            String selection = NutrientDBEntry.COLUMN_NDBNO + "=?"+" and "+NutrientDBEntry.COLUMN_DATE + "=?";
            String selectionArgs[]=new String[2];
            selectionArgs[0]=id;
            selectionArgs[1]=den;

            Cursor cursor = dbWrite.query(NutrientDBEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                conflictTuple = new Tuple(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                        cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10), cursor.getDouble(11),
                        cursor.getDouble(12), cursor.getDouble(13), cursor.getDouble(14), cursor.getDouble(15), cursor.getDouble(16),
                        cursor.getDouble(17), cursor.getDouble(18), cursor.getDouble(19), cursor.getDouble(20));

                //shtom postoi konfliktna torka, ke izvrshime update namesto insert

                Log.d("Konfliktanata torka e: ", conflictTuple.getDate() + " " + conflictTuple.getId());

                String whereClause = NutrientDBEntry.COLUMN_DATE + "=?" +" and " + NutrientDBEntry.COLUMN_NDBNO + "=?";

                String selectionArgs2[] = new String[2];
                selectionArgs2[0]=conflictTuple.getDate();
                selectionArgs2[1]=conflictTuple.getId();



                ContentValues valuesForUpdate = new ContentValues();

                 valuesForUpdate.put(NutrientDBEntry.COLUMN_DATE,den);
                 valuesForUpdate.put(NutrientDBEntry.COLUMN_NDBNO,id);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_PRODUCT_NAME,ime);

                int kolichina2=kolichina+conflictTuple.getQuantity();

                valuesForUpdate.put(NutrientDBEntry.COLUMN_QUANTITY,kolichina2);

               valuesForUpdate.put(NutrientDBEntry.COLUMN_PROTEIN,protein);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_TOTAL_LIPID,lipid);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_CARBOHYDRATE,carbo);

                valuesForUpdate.put(NutrientDBEntry.COLUMN_GLUCOSE,glucose);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_CALCIUM,calcium);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_IRON,iron);

                valuesForUpdate.put(NutrientDBEntry.COLUMN_MAGNESIUM,mg);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_ZINC,zinc);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_C,vitC);

                valuesForUpdate.put(NutrientDBEntry.COLUMN_THIAMIN,thiamin);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_RIBOFLAVIN,ribo);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_NIACIN,niacin);

                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_B6,vitB6);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_B12,vitB12);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_A,vitA);

                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_D,vitD);
                valuesForUpdate.put(NutrientDBEntry.COLUMN_VITAMIN_E,vitE);

                Log.d("Before"," update");

                dbWrite.update(NutrientDBEntry.TABLE_NAME,valuesForUpdate,whereClause,selectionArgs2);

                Log.d("Update"," was successfull");


            }


        }

        return null;
    }
}
