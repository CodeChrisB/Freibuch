package com.example.fragment.Logic;

import android.app.Activity;

public class ActivityValues {

    private static ActivityValues instance = null;

    private ActivityValues(){

    }

    public  static ActivityValues getInstance(){
        if(instance==null) {
            instance = new ActivityValues();
        }
        return instance;
    }

   private String barcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
