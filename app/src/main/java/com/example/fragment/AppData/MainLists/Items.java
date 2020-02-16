package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Entities.Recipe;
import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.Item;

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
    public void addTo(Item object) {
        items.add(object);
    }

    @Override
    public void removeObject(Item object) {
        items.remove(object);
    }

    @Override
    public void removeAll() {
        items = new ArrayList<Item>();
    }
}
