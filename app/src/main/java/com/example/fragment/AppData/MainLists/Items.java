package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Items implements SaveArrayAble<Item>, Serializable {

    ArrayList<Item> items;

    @Override
    public ArrayList<Item> getArray() {
        return null;
    }

    @Override
    public void addTo(Item object) {
        items.add(object);
    }
}
