package com.example.magdalena.nlife;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AppCompatDelegate mDelegate;

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar mActionBar = getDelegate().getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayShowHomeEnabled(false);
        }
        addPreferencesFromResource(R.xml.custom_pref);
        checkValidity();
    }

    private void checkValidity(){
        String gender=PreferenceManager.getDefaultSharedPreferences(this).getString(getApplicationContext().getResources().getString(R.string.pref_gender_key),getApplicationContext().getResources().getString(R.string.pref_gender_defaultValue));
        Log.d("Settings1Activity",gender);
        if(!gender.equals(getApplicationContext().getResources().getString(R.string.female))) {
            getPreferenceScreen().findPreference(getApplicationContext().getResources().getString(R.string.pref_pr_key)).setEnabled(false);
        }
        else{
            getPreferenceScreen().findPreference(getApplicationContext().getResources().getString(R.string.pref_pr_key)).setEnabled(true);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("Settings1Activity","onSharedPreferenceChanged called");
        checkValidity();
    }
}
