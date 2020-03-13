package com.example.foodgent.AppData.Entities;

import java.io.Serializable;

public class Recipe implements Serializable {

    String name,description;
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

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
