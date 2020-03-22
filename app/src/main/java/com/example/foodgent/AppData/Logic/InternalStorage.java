package com.example.foodgent.AppData.Logic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.foodgent.UserInterface.MainActivity;
import com.google.gson.Gson;


public class InternalStorage {

    private static Gson gson = new Gson();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public InternalStorage() {
        //set the SharedPrefernces
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getInstance());
    }

    public boolean saveData(String key, String json) {
        //save the json string at the location of key
        try {
            editor = sharedPreferences.edit();
            editor.putString(key, json);
            editor.commit();
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public String loadData(String key) {
        return sharedPreferences.getString(key, "");
    }


}
