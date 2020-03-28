package com.example.foodgent.AppData.Logic;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.BarcodeItem;
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
    private String premium;
    //endregion

    //region Gson & InternalStorage Init
    private static Gson gson = new Gson();
    private InternalStorage internalStorage = new InternalStorage();
    //endregion

    //region static Class
    private static AppData instance = null;

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
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
        premium = internalStorage.loadData("premium");
        String setting = internalStorage.loadData("settings");


        recipes = (recipe != null) ? loadRecipe(recipe) : setRecipe();
        items = (item != null) ? loadItem(item) : setItem();
        barcodes = (barcode != null) ? loadBarcode(barcode) : setBarcode();
        shoppingEntries = (shopping != null) ? loadShopping(shopping) : setShopping();
        settings = (setting != null) ? loadSetting(setting) : setSetting();

        //endregion
    }

    private Settings setSetting() {
        return new Settings();
    }

    private Settings loadSetting(String setting) {
        return (!setting.equals("null")) ? gson.fromJson(setting, Settings.class) : setSetting();
    }


    private ShoppingEntries setShopping() {
        return new ShoppingEntries();
    }

    private Barcodes setBarcode() {
        return new Barcodes();
    }

    private Items setItem() {
        return new Items();
    }

    private Recipes setRecipe() {
        return new Recipes();
    }

    private ShoppingEntries loadShopping(String shopping) {
        return (!shopping.equals("null")) ? gson.fromJson(shopping, ShoppingEntries.class) : setShopping();
    }

    private Barcodes loadBarcode(String barcode) {
        return (!barcode.equals("null")) ? gson.fromJson(barcode, Barcodes.class) : setBarcode();
    }

    private Items loadItem(String item) {
        return (!item.equals("null")) ? gson.fromJson(item, Items.class) : setItem();
    }

    private Recipes loadRecipe(String recipe) {
        return (!recipe.equals("null")) ? gson.fromJson(recipe, Recipes.class) : setRecipe();
    }


    public boolean saveAppData() {

        // TODO: 01/03/2020 add settings
        //region Save all Values
        try {
            internalStorage.saveData("recipes", gson.toJson(recipes));
            internalStorage.saveData("items", gson.toJson(items));
            internalStorage.saveData("barcode", gson.toJson(barcodes));
            internalStorage.saveData("shopping", gson.toJson(shoppingEntries));
            internalStorage.saveData("premium", premium);
            internalStorage.saveData("settings", gson.toJson(settings));
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
            internalStorage.saveData("shopping", gson.toJson(shoppingEntries));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveSettings() {
        try {
            internalStorage.saveData("settings", gson.toJson(settings));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void DeleteAppData() {
        //barcodes = new Barcodes();
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
        try {
            return barcodes.getArray();
        } catch (Exception ex) {
            barcodes = new Barcodes();
        }
        return barcodes.getArray();
    }

    public ArrayList<Recipe> getRecipes() {
        try {
            return recipes.getArray();
        } catch (Exception ex) {
            recipes = new Recipes();
        }
        return recipes.getArray();
    }

    public ArrayList<ShoppingEntry> getShoppingEntries() {
        try {
            return shoppingEntries.getArray();
        } catch (Exception ex) {
            shoppingEntries = new ShoppingEntries();
        }
        return shoppingEntries.getArray();
    }

    public ArrayList<Item> getItems() {
        try {
            return items.getArray();
        } catch (Exception ex) {
            items = new Items();
        }
        return items.getArray();
    }
    //endregion

    //region add Object
    public boolean addBarcode(Barcode barcode) {
        return barcodes.addTo(barcode);

    }

    public boolean addRecipe(Recipe recipe) {
        return recipes.addTo(recipe);
    }

    public boolean addShoppingEntry(ShoppingEntry shoppingEntry) {
        return shoppingEntries.addTo(shoppingEntry);
    }

    public boolean addItem(Item item) {
        return items.addTo(item);
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
    public BarcodeItem searchForItem(String barcode) {
        ArrayList<Barcode> list = barcodes.getArray();

        for (Barcode code : list) {
            if (code.getBarcode().equals(barcode)) {
                return code.getItem();
            }
        }
        return null;
    }

    public boolean removeBarcode(String barcode) {
        ArrayList<Barcode> list = barcodes.getArray();

        for (Barcode code : list) {
            if (code.getBarcode().equals(barcode)) {
                list.remove(code);
                saveBarcode();
                return true;
            }
        }
        return false;
    }

    ///endregion

    public String getFormattedShoppingList() {
        return shoppingEntries.toFormatedList();
    }

    public boolean isPremium() {
        return !premium.equals("no");
    }

    public void setPremium(boolean set) {
        if (set) {
            premium = "yes";
        } else {
            premium = "no";
        }

    }

    public void removeSelectedShoppingEntries() {
        shoppingEntries.removeSelected();
    }

    public void removeSelectedItems() {
        items.removeSelected();
    }

    public void removeSelectedRecipes() {
        recipes.removeSelected();
    }

    public void setNotification(boolean status) {
        settings.setSendNotification(status);
    }

    public boolean isDarkMode() {
        return settings.isUseDarkmode();
    }

    public void setDarkMode(boolean darkmode) {
        settings.setUseDarkmode(darkmode);
    }

    public boolean isBigText() {
        return settings.isUseBigText();
    }

    public void setBigText(boolean text) {
        settings.setUseBigText(text);
    }

    public boolean isNotificationOn() {
        return settings.isSendNotification();
    }

    public String getShopHeader() {
        return shoppingEntries.getHeader();
    }

    public void setShopHeader(String header) {
        shoppingEntries.setHeader(header);
    }

    public String getRowMaker() {
        return shoppingEntries.getRowMarker();
    }

    public void setRowMaker(String header) {
        shoppingEntries.setRowMarker(header);
    }

}
