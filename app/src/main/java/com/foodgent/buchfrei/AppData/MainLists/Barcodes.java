package com.foodgent.buchfrei.AppData.MainLists;

import com.foodgent.buchfrei.AppData.Entities.Barcode;
import com.foodgent.buchfrei.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
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
    public boolean addTo(Barcode object) {

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
        return true;
    }

    @Override
    public void removeObject(Barcode object) {
        barcodes.remove(object);
    }


    @Override
    public  void removeAll(){
        barcodes = new ArrayList<Barcode>();
    }

    @Override
    public ArrayList<Barcode> getObject() {
        return barcodes;
    }

    @Override
    public void setObject(ArrayList<Barcode> objectList) {
        barcodes = objectList;
    }

    private Boolean isNewBarCode(String barcode) {
        for(Barcode code : barcodes){
            if(code.getBarcode().equals(barcode))
                return false;
        }
        return true;
    }


}
