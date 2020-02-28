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
import java.io.FileNotFoundException;
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
        File file = new File(MainActivity.getInstance().getContext().getFilesDir(), "appdata.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
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

        Context c = MainActivity.getInstance().getContext();
        String filepath = c.getFilesDir().getPath() + "/appdata.txt";

        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream objectIn = null;
        try {
            objectIn = new ObjectInputStream(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppData appdata = null;
        try {
            appdata = (AppData) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The Object has been read from the file");
        try {
            objectIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ShoppingEntry> list = appdata.getShoppingEntries();
        return appdata;

    }


    public void saveAppData() {
        //Save all Lists, Settings and Barcodes from Memory
        AppData saveData = new AppData(barcodes, recipes, items, settings, shoppingEntries);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("appdata.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(saveData);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*Context c = MainActivity.getInstance().getContext();
        String filePath = c.getFilesDir().getPath().toString() + "/appdata.txt";
        File file = new File(filePath);



        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))){
            oos.writeObject(saveData);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
