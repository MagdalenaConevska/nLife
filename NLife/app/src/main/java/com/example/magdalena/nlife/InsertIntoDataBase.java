package com.example.magdalena.nlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

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

        NutrientsDBHelper dbHelper=new NutrientsDBHelper(context); //this=context
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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

      //  Log.d("msg:","Pred insert");

        db.insert(NutrientDBEntry.TABLE_NAME,null,values);

        Log.d("msg:","Posle insert");



        return null;
    }
}
