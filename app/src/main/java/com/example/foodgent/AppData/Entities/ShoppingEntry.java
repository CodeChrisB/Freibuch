package com.example.foodgent.AppData.Entities;

import java.io.Serializable;

public class ShoppingEntry implements Serializable {

    String entry;

    public ShoppingEntry(String entry) {
        this.entry = entry;
    }

    public String getName(){
        return entry;
    }

    @Override
    public String toString() {
        //make the first letter uppcase and the rest lower case
        //example input is : breaD ==> first letter b is going to be B and  reaD will all be lowercase
        String output = entry.substring(0, 1).toUpperCase() + entry.substring(1);
        return output;
    }

}
