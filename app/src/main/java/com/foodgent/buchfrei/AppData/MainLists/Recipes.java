package com.foodgent.buchfrei.AppData.MainLists;

import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipes implements SaveArrayAble<Recipe>, Serializable {

    ArrayList<Recipe> recipes = new ArrayList<>();

    public Recipes(){

    }

    public Recipes(ArrayList<Recipe> list){
        recipes = list;
    }

    @Override
    public ArrayList<Recipe> getArray() {

        if (recipes == null) {
            this.removeAll();
        }

        return recipes;
    }

    @Override
    public boolean addTo(Recipe object) {
        recipes.add(object);
        return true;
    }



    @Override
    public void removeObject(Recipe object) {
        recipes.remove(object);
    }

    public void removeObject(String object) {
        ArrayList<Recipe> temp = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (!recipe.getName().equals(object))
                temp.add(recipe);
        }
        recipes = temp;
    }

    @Override
    public void removeAll() {
        recipes = new ArrayList<>();
    }

    @Override
    public ArrayList<Recipe> getObject() {
        return recipes;
    }

    @Override
    public void setObject(ArrayList<Recipe> objectList) {
        recipes = objectList;
    }

    public void removeSelected() {
        //to remove all we create a new list with all unselected entries and overwrite the list with it
        ArrayList<Recipe> list = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            if (!recipes.get(i).isSelected())
                list.add(recipes.get(i));
        }
        recipes = list;
    }
}
