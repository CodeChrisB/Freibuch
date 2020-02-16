package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.ShoppingEntry;

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
}
