package com.example.magdalena.nlife;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Marija on 2/8/2017.
 */

public class GetRecipesService extends Service {



    public GetRecipesService(){}

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        Log.d("GetRecipesService","Service started");
        //new GetRecipes(this).execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service stopped",Toast.LENGTH_LONG).show();
        Log.d("GetRecipesService","Service ended");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_LONG).show();
        new GetRecipes(this).execute();
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
