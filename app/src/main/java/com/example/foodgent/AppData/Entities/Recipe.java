package com.example.foodgent.AppData.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    String name,description;
    boolean isSeleted = false;
    ArrayList<RecipeItem> recipeList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public ArrayList<RecipeItem> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(ArrayList<RecipeItem> recipeList) {
        this.recipeList = recipeList;
    }
}
