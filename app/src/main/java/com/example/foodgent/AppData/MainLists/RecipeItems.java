package com.example.foodgent.AppData.MainLists;

import java.util.ArrayList;

public class RecipeItems {
    ArrayList<String> list = new ArrayList<>();


    public boolean addTo(String item) {
        list.add(item);
        return true;
    }

    public ArrayList<String> getRecipeItems() {
        return list;
    }
}
