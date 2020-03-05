package com.example.foodgent.AppData.Entities;

import java.io.Serializable;

public class Barcode implements Serializable {

    String barcode;
    BarcodeItem item;

    public Barcode(String barcode, BarcodeItem item) {
        this.barcode = barcode;
        this.item = item;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String  barcode) {
        this.barcode = barcode;
    }

    public BarcodeItem getItem() {
        return item;
    }

    public void setItem(BarcodeItem item) {
        this.item = item;
    }
}
