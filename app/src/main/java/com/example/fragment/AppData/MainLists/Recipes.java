package com.example.fragment.AppData.MainLists;

import com.example.fragment.AppData.Logic.ArrayAble;
import com.example.fragment.AppData.Entities.Recipe;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipes implements ArrayAble<Recipe>, Serializable {

    @Override
    public ArrayList<Recipe> getArray() {
        return null;
    }
}
