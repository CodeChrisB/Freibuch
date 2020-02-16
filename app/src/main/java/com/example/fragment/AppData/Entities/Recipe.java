package com.example.fragment.AppData.Entities;

import java.io.Serializable;

public class Recipe implements Serializable {

    String name,description;

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
