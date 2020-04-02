package com.example.foodgent.AppData.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    String name;
    ArrayList<Item> listOfItems;
    ArrayList<String> steps;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNumOfSeps(){
        return steps.size();
    }

    public Recipe(String name, ArrayList<String> steps, ArrayList<Item> listOfItems) {
        this.name = name;
        this.steps = steps;
        this.listOfItems = listOfItems;
        this.isSeleted = isSeleted;
    }

    boolean isSeleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(ArrayList<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

}
