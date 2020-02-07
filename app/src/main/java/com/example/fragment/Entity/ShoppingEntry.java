package com.example.fragment.Entity;

/**
 * Created by User on 3/14/2017.
 */

public class ShoppingEntry {
    private String name;


    public ShoppingEntry(String note) {
        this.name = note;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
