package com.example.foodgent.AppData.Logic;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Entities.Settings;
import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.MainLists.Barcodes;
import com.example.foodgent.AppData.MainLists.Items;
import com.example.foodgent.AppData.MainLists.Recipes;
import com.example.foodgent.AppData.MainLists.ShoppingEntries;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {

    //region Saved Values
    protected static Barcodes barcodes = new Barcodes();
    protected static Recipes recipes = new Recipes();
    protected static Items items = new Items();
    protected static Settings settings = new Settings();
    protected static ShoppingEntries shoppingEntries = new ShoppingEntries();
    //endregion

    //region Gson & InternalStorage Init
    private static Gson gson = new Gson();
    private InternalStorage internalStorage = new InternalStorage();
    //endregion

    //region static Class
    private static AppData instance = null;
    public static AppData getInstance() {
        if (instance == null)
            instance = new AppData();
        return instance;
    }
    //endregion

    //region Constructors

    //the public Constructor is empty all data
    // will be loaded after creation of the object.
    public AppData() {
        barcodes = new Barcodes();
        recipes = new Recipes();
        items = new Items();
        settings = new Settings();
        shoppingEntries = new ShoppingEntries();
    }

    //private Constructor for the save mechanism
    private AppData(Barcodes barcodes, Recipes recipes, Items items, Settings settings, ShoppingEntries shoppingEntries) {
        AppData.barcodes = barcodes;
        AppData.recipes = recipes;
        AppData.items = items;
        AppData.settings = settings;
        AppData.shoppingEntries = shoppingEntries;
    }

    //endregion

    //region Presistence (Save/Load/Delete)

    public void loadData() {

        //region Load all JSON Strings from SharedStorage
        String recipe = internalStorage.loadData("recipes");
        String item = internalStorage.loadData("items");
        String barcode = internalStorage.loadData("barcode");
        String shopping = internalStorage.loadData("shopping");
        //endregion

        //region check if the JSON Strings are not empty and fill Values
        if (!recipe.equals("")) {
            recipes = gson.fromJson(recipe, Recipes.class);
        }

        if (!item.equals("")) {
            items = gson.fromJson(recipe, Items.class);
        }

        if (!barcode.equals("")) {
            barcodes = gson.fromJson(barcode, Barcodes.class);
        }

        if (!shopping.equals("")) {
            shoppingEntries = gson.fromJson(shopping, ShoppingEntries.class);
        }
        //endregion
    }

    public boolean saveAppData() {

        // TODO: 01/03/2020 add settings
        //region Save all Values
        try {
            internalStorage.saveData("recipes", gson.toJson(recipes));
            internalStorage.saveData("items", gson.toJson(items));
            internalStorage.saveData("barcode", gson.toJson(barcodes));
            internalStorage.saveData("shopping", gson.toJson(shoppingEntries));
        } catch (Exception ex) {
            return false;
        }
        return true;
        //endregion
    }

    public boolean saveRecipe() {
        try {
            internalStorage.saveData("recipes", gson.toJson(recipes));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveItems() {
        try {
            internalStorage.saveData("items", gson.toJson(items));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveBarcode() {
        try {
            internalStorage.saveData("barcode", gson.toJson(barcodes));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveShopEntries() {
        try {
            internalStorage.saveData("recipes", gson.toJson(shoppingEntries));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void DeleteAppData() {
        barcodes = new Barcodes();
        recipes = new Recipes();
        items = new Items();
        settings = new Settings();
        shoppingEntries = new ShoppingEntries();
        //Changes would be lost after closing the app so we have to save now.
        saveAppData();
    }

    //endregion

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

    public String getFormattedShoppingList() {
        return shoppingEntries.toFormatedList();
    }
}
