package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class Items implements SaveArrayAble<Item>, Serializable {

    ArrayList<Item> items = new ArrayList<>();

    public Items(){
    }

    @Override
    public ArrayList<Item> getArray() {
        return items;
    }

    @Override
    public boolean addTo(Item object) {
        items.add(object);
        return true;
    }

    @Override
    public void removeObject(Item object) {
        items.remove(object);
    }

    @Override
    public void removeAll() {
        items = new ArrayList<Item>();
    }

    @Override
    public ArrayList<Item> getObject() {
        return items;
    }

    @Override
    public void setObject(ArrayList<Item> objectList) {
        items = objectList;
    }
}
