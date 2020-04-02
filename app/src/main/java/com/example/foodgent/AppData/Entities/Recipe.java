package com.example.foodgent.AppData.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    String name, description, recipeType;
    ArrayList<RecipeItem> listOfItems;
    boolean selected;
    boolean isFavourite;
    int portions, time;

    public Recipe(String name, String description, ArrayList<RecipeItem> listOfItems, int portions, int time, boolean isFavourite) {
        this.name = name;
        this.description = description;
        this.listOfItems = listOfItems;
        this.isSeleted = isSeleted;
        this.portions = portions;
        this.time = time;
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPortions() {
        if (portions > 0)
            return portions;
        return -1;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getRecipeType() {
        if (recipeType != null)
            return recipeType;
        return "";
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    boolean isSeleted = false;

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


    public String getFormatedRecipeList(Textview setRecipe)

}
