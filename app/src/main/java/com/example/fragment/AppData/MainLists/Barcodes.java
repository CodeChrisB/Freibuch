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

        if(isNewBarCode(object.getBarcode())){
            //just add barcode its a new one!
            barcodes.add(object);
        }else {
            //we have to delete the old barcode and add the new one.
            for (Barcode code : barcodes) {
                if (code.getBarcode().equals(object.getBarcode())) {
                    barcodes.remove(code);
                    barcodes.add(object);
                    break;
                }
            }
        }
    }

    @Override
    public void removeObject(Barcode object) {
        barcodes.remove(object);
    }


    @Override
    public  void removeAll(){
        barcodes = new ArrayList<Barcode>();
    }


    private Boolean isNewBarCode(String barcode) {
        for(Barcode code : barcodes){
            if(code.getBarcode().equals(barcode))
                return false;
        }
        return true;
    }

}
