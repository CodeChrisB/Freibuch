package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Entities.RecipeItem;

import java.util.ArrayList;

public class RecipeItems {
    ArrayList<RecipeItem> list = new ArrayList<>();


    public boolean addTo(RecipeItem item) {

        for (RecipeItem s : list) {
            if (s.getName().toLowerCase().equals(item.getName().toLowerCase())) {
                //duplicate item
                return false;
            }
        }

        list.add(item);
        return true;
    }

    public ArrayList<RecipeItem> getRecipeItems() {
        return list;
    }
}
