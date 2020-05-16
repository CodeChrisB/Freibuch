package com.foodgent.buchfrei.AppData.MainLists;

import java.util.ArrayList;

public class RecipeItems {
    ArrayList<String > list = new ArrayList<>();


    public boolean addTo(String item) {

        for (String s : list) {
            if (s.toLowerCase().equals(item.toLowerCase())) {
                //duplicate item
                return false;
            }
        }

        list.add(item);
        return true;
    }

    public ArrayList<String > getRecipeItems() {
        return list;
    }
}
