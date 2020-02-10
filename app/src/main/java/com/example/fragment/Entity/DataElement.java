package com.example.fragment.Entity;

import android.content.Context;

import com.example.fragment.R;
import com.example.fragment.UserInterface.MainActivity;

import java.util.ArrayList;

public class DataElement {

    private static final int ITEM = 0;
    private static final int COOKING = 1;
    private static final int SHOPPING = 2;

    private ArrayList<ArrayList<Object>> dataList = new ArrayList<>();


    private ArrayList<Item>  items = new ArrayList<>();
    private ArrayList<CookingRecipe>  cookingRecipes = new ArrayList<>();
    private ArrayList<ShoppingEntry>  shoppingEntries = new ArrayList<>();

    public DataElement() {

        // TODO: 10/02/2020 get all 3 lists
    }

    public void saveData(){
        dataList=new ArrayList<>();

    }
}
