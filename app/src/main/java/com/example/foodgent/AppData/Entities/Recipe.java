package com.example.foodgent.AppData.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {


    String name, recipeType;
    ArrayList<RecipeItem> listOfItems;
    String description;
    boolean selected;
    boolean isFavourite;
    int portions, time;
    ArrayList<RecipeStep> steps;

    public Recipe(String name, String description, ArrayList<RecipeItem> listOfItems, int portions, int time, boolean isFavourite, ArrayList<RecipeStep> steps, String recipeType) {
        this.name = name;
        this.description = description;

        this.isSeleted = isSeleted;
        this.portions = portions;
        this.time = time;
        this.isFavourite = isFavourite;
        this.recipeType = recipeType;
        //if a list is null (has no values) just create a empty one
        //so we dont have to deal with null values
        this.steps = (steps != null) ? steps : new ArrayList<RecipeStep>();
        this.listOfItems = (listOfItems != null) ? listOfItems : new ArrayList<RecipeItem>();
    }

    public ArrayList<RecipeItem> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(ArrayList<RecipeItem> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
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

        if (description == null)
            return "Keine Beschreibung gefunden!";

        return description.length() > 0 ? description : "Keine Beschreibung gefunden!";
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

    public String getFormattedList() {

        if (listOfItems == null)
            return "";

        String itemList = "";
        for (RecipeItem item : listOfItems) {
            itemList += item.getName() + "  " + item.getAmount() + "\n";
        }

        return itemList;
    }
}
