package com.example.fragment.AppData.Entities;

import java.io.Serializable;

public class Barcode implements Serializable {

    Barcode barcode;
    Item item;

    public Barcode(Barcode barcode, Item item) {
        this.barcode = barcode;
        this.item = item;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
