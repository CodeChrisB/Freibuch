package com.example.fragment.AppData.Logic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.fragment.UserInterface.MainActivity;
import com.google.gson.Gson;


public class InternalStorage {

    private static Gson gson = new Gson();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public InternalStorage() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getInstance().getContext());

    }

    public boolean saveData(String key, String json) {

        editor = sharedPreferences.edit();
        editor.putString(key, json);
        editor.commit();
        return false;

    }

    public String loadData(String key) {
        return sharedPreferences.getString(key, "defValue");
    }


}
