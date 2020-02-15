package com.example.fragment.AppData.Entities;

import java.io.Serializable;

public class Barcode implements Serializable {

    String barcode;
    Item item;

    public Barcode(String barcode, Item item) {
        this.barcode = barcode;
        this.item = item;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String  barcode) {
        this.barcode = barcode;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
