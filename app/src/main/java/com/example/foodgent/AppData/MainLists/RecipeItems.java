package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Logic.SaveArrayAble;

import java.util.ArrayList;

public class RecipeItems implements SaveArrayAble<String> {
    ArrayList<String> recipeItems = new ArrayList<>();


    @Override
    public ArrayList<String> getArray() {
        return recipeItems;
    }

    @Override
    public boolean addTo(String object) {
        recipeItems.add(object);
        return true;
    }

    public String getAtPos(int i) {
        return recipeItems.get(i);
    }

    @Override
    public void removeObject(String object) {
        recipeItems.remove(object);
    }

    @Override
    public void removeAll() {
        recipeItems = new ArrayList<>();
    }

    @Override
    public ArrayList<String> getObject() {
        return recipeItems;
    }

    @Override
    public void setObject(ArrayList<String> objectList) {
        recipeItems = objectList;

    }
}
