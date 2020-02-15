package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Logic.SaveArrayAble;
import com.example.fragment.AppData.Entities.Recipe;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipes implements SaveArrayAble<Recipe>, Serializable {

    ArrayList<Recipe> recipes;
    @Override
    public ArrayList<Recipe> getArray() {
        return null;
    }

    @Override
    public void addTo(Recipe object) {
        recipes.add(object);
    }
}
