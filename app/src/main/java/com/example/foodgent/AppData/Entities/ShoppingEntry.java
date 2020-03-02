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
}
