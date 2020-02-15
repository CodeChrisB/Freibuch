package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Entities.Item;
import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.Barcode;

import java.io.Serializable;
import java.util.ArrayList;

public class Barcodes implements SaveArrayAble<Barcode>, Serializable {


    ArrayList<Barcode> barcodes;

    public Barcodes(ArrayList<Barcode> barcodes) {
        this.barcodes = barcodes;
    }

    public Item getItemFromBarcode(String barcode){
        for(Barcode b : barcodes){
            if(b.getBarcode().equals(barcode))
                return b.getItem();
        }
        return null;
    }

    public void AddBarCode(Barcode barcode){
        barcodes.add(barcode);
    }

    @Override
    public ArrayList<Barcode> getArray() {
        return barcodes;
    }

    @Override
    public void addTo(Barcode object) {
        barcodes.add(object);
    }


}
