package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingEntries implements SaveArrayAble<ShoppingEntry>, Serializable {

    ArrayList<ShoppingEntry> shoppingEntries = new ArrayList<>();

    public ShoppingEntries(){
    }

    @Override
    public ArrayList<ShoppingEntry> getArray() {
        return shoppingEntries;
    }

    @Override
    public void addTo(ShoppingEntry object) {
        shoppingEntries.add(object);
    }

    @Override
    public void removeObject(ShoppingEntry object) {
        shoppingEntries.remove(object);
    }

    @Override
    public void removeAll() {
        shoppingEntries = new ArrayList<ShoppingEntry>();
    }

    @Override
    public ArrayList<ShoppingEntry> getObject() {
        return shoppingEntries;
    }

    @Override
    public void setObject(ArrayList<ShoppingEntry> objectList) {
        shoppingEntries = objectList;
    }

    public String toFormatedList() {
        //Create Header
        StringBuilder sb = new StringBuilder();
        sb.append("Meine Einkaufsliste: ");
        sb.append(System.getProperty("line.separator"));

        //call toString Method for every Entry in the List
        for (ShoppingEntry item : AppData.getInstance().getShoppingEntries()) {
            sb.append("-" + item.toString());
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
