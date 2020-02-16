package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Entities.Item;
import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.Barcode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Barcodes implements SaveArrayAble<Barcode>, Serializable {

    ArrayList<Barcode> barcodes = new ArrayList<>();

    public Barcodes(){
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
