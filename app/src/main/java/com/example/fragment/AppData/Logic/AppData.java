package com.example.fragment.AppData.Logic;

import android.content.Context;

import com.example.fragment.AppData.Entities.Barcode;
import com.example.fragment.AppData.Entities.Item;
import com.example.fragment.AppData.Entities.Recipe;
import com.example.fragment.AppData.Entities.ShoppingEntry;
import com.example.fragment.AppData.MainLists.Barcodes;
import com.example.fragment.AppData.MainLists.Items;
import com.example.fragment.AppData.MainLists.Recipes;
import com.example.fragment.AppData.MainLists.ShoppingEntries;
import com.example.fragment.UserInterface.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {

    protected static Barcodes barcodes = new Barcodes();
    protected static Recipes recipes = new Recipes();
    protected static Items items = new Items();
    protected static Settings settings = new Settings();
    protected static ShoppingEntries shoppingEntries = new ShoppingEntries();
    private static AppData instance = null;


    private String pathName = "appdata.txt";



    //region static Class
    public static AppData getInstance() {
        if (instance == null)
            instance = new AppData();
        return instance;
    }

    //endregion


    //the public Constructor is empty all data
    // will be loaded after creation of the object.
    public AppData() {
        File yourFile = new File("appdata.txt");
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
        } catch (IOException e) {
            // TODO: 21/02/2020
            //create an exclusive constructor only for the resaon to create an empty appdata object


        }
    }


    private void Init() {
    }


    //private Constructor for the save mechanism
    private AppData(Barcodes barcodes, Recipes recipes, Items items, Settings settings, ShoppingEntries shoppingEntries) {
        AppData.barcodes = barcodes;
        AppData.recipes = recipes;
        AppData.items = items;
        AppData.settings = settings;
        AppData.shoppingEntries = shoppingEntries;
    }


    //get the saved data from memory
    public AppData loadData() {

        //Load all the AppData object
        // deserialize the object
        Object appData = null;
        try {
            FileInputStream fos = MainActivity.getInstance().getContext().openFileInput("appdata.txt");
            ObjectInputStream ois = new ObjectInputStream(fos);
            // write object to file
            appData = ois.readObject();
            System.out.println("Done");
            // closing resources
            ois.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (AppData) appData;

    }


    public void saveAppData() {
        //Save all Lists, Settings and Barcodes from Memory
        AppData saveData = new AppData(barcodes, recipes, items, settings, shoppingEntries);
        shoppingEntries.addTo(new ShoppingEntry("hello"));
        //Save the current State to a file

        try {
            FileOutputStream fos = MainActivity.getInstance().getContext().openFileOutput("appdata.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(saveData);
            System.out.println("Done");
            // closing resources
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private String AppDataObjectToString(AppData saveData) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
            os.writeObject(saveData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(out.toByteArray());
    }
    //Create a new empty AppData Object and save it,
    //to "delete" all memories.
    public void DeleteAppData() {
        barcodes = new Barcodes();
        recipes = new Recipes();
        items = new Items();
        settings = new Settings();
        shoppingEntries = new ShoppingEntries();
        //Changes would be lost after closing the app so we have to save now.
        saveAppData();
    }


    //region get Object
    public ArrayList<Barcode> getBarcodes() {
        return barcodes.getArray();
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes.getArray();
    }

    public ArrayList<ShoppingEntry> getShoppingEntries() {
        return shoppingEntries.getArray();
    }

    public ArrayList<Item> getItems() {
        return items.getArray();
    }
    //endregion

    //region add Object
    public void addBarcode(Barcode barcode) {
        barcodes.addTo(barcode);

    }

    public void addRecipe(Recipe recipe) {
        recipes.addTo(recipe);
    }

    public void addShoppingEntry(ShoppingEntry shoppingEntry) {
        shoppingEntries.addTo(shoppingEntry);
    }

    public void addItem(Item item) {
        items.addTo(item);
    }
    //endregion

    //region remove object
    public void removeBarcode(Barcode barcode) {
        barcodes.removeObject(barcode);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.removeObject(recipe);
    }

    public void removeShoppingEntry(ShoppingEntry shoppingEntry) {
        shoppingEntries.removeObject(shoppingEntry);
    }

    public void removeItem(Item item) {
        items.removeObject(item);
    }
    //endregion

    //region RemoveAll
    public void removeAllBarCodes() {
        barcodes.removeAll();
    }

    public void removeAllRecipes() {
        recipes.removeAll();
    }

    public void removeAllItems() {
        items.removeAll();
    }

    public void removeAllShoppingEntries() {
        shoppingEntries.removeAll();
    }
    //endregion

    //region Barcode Functions
    public Item searchForItem(String barcode) {
        ArrayList<Barcode> list = barcodes.getArray();

        for (Barcode code : list) {
            if (code.getBarcode().equals(barcode)) {
                return code.getItem();
            }
        }
        return null;
    }

    ///endregion

}
