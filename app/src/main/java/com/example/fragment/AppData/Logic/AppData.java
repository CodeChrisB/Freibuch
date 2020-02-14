package com.example.fragment.AppData.Logic;

import com.example.fragment.AppData.Entities.Barcode;
import com.example.fragment.AppData.Entities.Recipe;
import com.example.fragment.AppData.Entities.ShoppingEntry;
import com.example.fragment.AppData.MainLists.Barcodes;
import com.example.fragment.AppData.MainLists.Items;
import com.example.fragment.AppData.MainLists.Recipes;
import com.example.fragment.AppData.MainLists.ShoppingEntries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {

    private Barcodes barcodes;
    private Recipes recipes;
    private Items items;
    private Settings settings;
    private ShoppingEntries shoppingEntries;


    private AppData(Barcodes barcodes, Recipes recipes, Items items, Settings settings, ShoppingEntries shoppingEntries) {
        this.barcodes = barcodes;
        this.recipes = recipes;
        this.items = items;
        this.settings = settings;
        this.shoppingEntries = shoppingEntries;
    }

    public void Init(){
        //Load all Lists, Settings and Barcodes from Memory

        //Retrieve the Data from the current Session
        ObjectInputStream input;
        try {
            input = new ObjectInputStream(new FileInputStream("test.txt"));
            AppData receivedData = (AppData) input.readObject();
            barcodes = receivedData.barcodes;
            recipes = receivedData.recipes;
            items = receivedData.items;
            settings = receivedData.settings;
            shoppingEntries = receivedData.shoppingEntries;
        } catch (Exception e) { }


    }

    public void Save(){
        //Save all Lists, Settings and Barcodes from Memory
        AppData saveData = new AppData(barcodes,recipes,items,settings,shoppingEntries);

        //Save the current State to a file
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("test.txt"));
            output.writeObject(saveData);
            output.close();
        } catch (IOException ex) { }

    }

    public void DeleteAppData(){

        //Delete all Data
        barcodes = new Barcodes();
        recipes = new Recipes();
        items = new Items();
        settings = new Settings();
        shoppingEntries = new ShoppingEntries();
        //Changes would be lost after closing the app so we have to save now.
        Save();
    }


   public ArrayList<Barcode> getBarcodes(){
        return barcodes.getArray();
   }

    public ArrayList<Recipe> getRecipes(){
        return recipes.getArray();
    }

    public ArrayList<ShoppingEntry> getShoppingEntries(){
        return shoppingEntries.getArray();
    }
}
