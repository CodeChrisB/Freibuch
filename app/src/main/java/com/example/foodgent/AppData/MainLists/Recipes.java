package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipes implements SaveArrayAble<Recipe>, Serializable {

    ArrayList<Recipe> recipes = new ArrayList<>();

    public Recipes(){
    }

    @Override
    public ArrayList<Recipe> getArray() {
        return recipes;
    }

    @Override
    public void addTo(Recipe object) {
        recipes.add(object);
    }



    @Override
    public void removeObject(Recipe object) {
        recipes.remove(object);
    }

    @Override
    public void removeAll() {
        recipes = new ArrayList<Recipe>();
    }

    @Override
    public ArrayList<Recipe> getObject() {
        return recipes;
    }

    @Override
    public void setObject(ArrayList<Recipe> objectList) {
        recipes = objectList;
    }
}
