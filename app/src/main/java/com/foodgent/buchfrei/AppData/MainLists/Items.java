package com.foodgent.buchfrei.AppData.MainLists;

import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class Items implements SaveArrayAble<Item>, Serializable {

    private static final int MAX_ITEM = 20;
    ArrayList<Item> items = new ArrayList<>();

    public Items(){
    }

    @Override
    public ArrayList<Item> getArray() {
        return items;
    }

    @Override
    public boolean addTo(Item object) {
        //wenn weniger als MAX.ITEM oder Premium aktiv
        if (items.size() < MAX_ITEM || AppData.getInstance().isPremium())
            return items.add(object);
        return false;
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


    public void removeSelected() {
        //to remove all we create a new list with all unselected entries and overwrite the list with it
        ArrayList<Item> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isSelected())
                list.add(items.get(i));
        }
        items = list;
    }

    public void addAll(ArrayList<Item> itemList) {
        for (Item item : items) {
            items.add(item);
        }
    }


}
