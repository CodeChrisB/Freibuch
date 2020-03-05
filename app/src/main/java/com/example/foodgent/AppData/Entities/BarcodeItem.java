package com.example.foodgent.AppData.Entities;

public class BarcodeItem {

    String name, description;

    public BarcodeItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BarcodeItem() {
        //just for debugging and testing purpose.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
