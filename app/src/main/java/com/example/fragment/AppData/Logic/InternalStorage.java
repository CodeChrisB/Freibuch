package com.example.fragment.AppData.Logic;

import android.content.Context;

import com.example.fragment.UserInterface.MainActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalStorage {

    private static InternalStorage instance = null;
    private static Gson gson = new Gson();

    public boolean saveData(AppData saveData) {

        gson.toJson(saveData);
        String s = gson.toJson(saveData);

        FileOutputStream fOut = null;
        try {
            fOut = MainActivity.getInstance().getContext().openFileOutput("appdata.txt", Context.MODE_PRIVATE);
            fOut.write(s.getBytes());
            fOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public AppData loadData() {

        FileInputStream fis = null;
        try {
            fis = MainActivity.getInstance().getContext().openFileInput("appdata.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if (((line = bufferedReader.readLine()) != null))
                    sb.append(line + "\n");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String s = sb.toString();
        AppData newData = null;
        try {
            newData = gson.fromJson(s, AppData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newData;
    }


}
