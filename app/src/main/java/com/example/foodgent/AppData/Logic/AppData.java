package com.example.foodgent.AppData.Logic;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.BarcodeItem;
import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Entities.RecipeItem;
import com.example.foodgent.AppData.Entities.Settings;
import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.MainLists.Barcodes;
import com.example.foodgent.AppData.MainLists.Items;
import com.example.foodgent.AppData.MainLists.RecipeItems;
import com.example.foodgent.AppData.MainLists.Recipes;
import com.example.foodgent.AppData.MainLists.ShoppingEntries;
import com.example.foodgent.UserInterface.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    protected static RecipeItems recipeItems = new RecipeItems();
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
        String recipeItem = internalStorage.loadData("recipeItems");


        recipes = (recipe != null) ? loadRecipe(recipe) : setRecipe();
        for (Recipe r: get_json()) {
            recipes.addTo(r);
        }
        recipeItems = (recipeItem != null) ? loadRecipeItem(recipeItem) : setRecipeItems();
        items = (item != null) ? loadItem(item) : setItem();
        barcodes = (barcode != null) ? loadBarcode(barcode) : setBarcode();
        shoppingEntries = (shopping != null) ? loadShopping(shopping) : setShopping();
        settings = (setting != null) ? loadSetting(setting) : setSetting();

        //endregion
    }

    private RecipeItems setRecipeItems() {
        return new RecipeItems();
    }

    private RecipeItems loadRecipeItem(String recipe) {
        return (!recipe.equals("null")) ? gson.fromJson(recipe, RecipeItems.class) : setRecipeItems();
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
            saveRecipe();
            saveShopEntries();
            saveItems();
            saveSettings();
            saveSettings();
            saveBarcode();
            saveRecipeItems();

        } catch (Exception ex) {
            return false;
        }
        return true;
        //endregion
    }

    private ArrayList<Recipe> get_json(){
        String json;
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            InputStream is = MainActivity.getInstance().getAssets().open("recipe01.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            String name;
            String desc = "";
            ArrayList<RecipeItem> list = new ArrayList<>();
            int sum, count;

            for (int i = 0; i < jsonArray.length();i++){
                list = new ArrayList<>();
                JSONObject object = jsonArray.getJSONObject(i);
                name = object.getString("name");
                sum = object.getInt("portions");

                count = 1;
                while (!object.get(String.format("item%d", count)).equals(null)){
                    String[] parts = object.getString(String.format("item%d", count)).split(";");
                    list.add(new RecipeItem(Integer.parseInt(parts[0]), parts[1]));
                    count++;
                }

                count = 1;
                while (!object.get(String.format("line%d", count)).equals(null)){
                    count++;
                }

                recipes.add(new Recipe(name, desc, list, sum, object.getInt("time"), false));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public boolean saveRecipe() {
        try {
            internalStorage.saveData("recipes", gson.toJson(recipes));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveRecipeItems() {
        try {
            internalStorage.saveData("recipeItems", gson.toJson(recipeItems));
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

    public ArrayList<String> getRecipeItems() {
        return recipeItems.getRecipeItems();
    }

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

    public boolean addRecipeItem(String item) {
        return recipeItems.addTo(item);
    }

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

    //region share list

    public String getFormattedShoppingList() {
        return shoppingEntries.toFormatedList();
    }

    //endregion

    //region premium

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

    //endregion


    //region remove selected
    public void removeSelectedShoppingEntries() {
        shoppingEntries.removeSelected();
    }

    public void removeSelectedItems() {
        items.removeSelected();
    }

    public void removeSelectedRecipes() {
        recipes.removeSelected();
    }

    //endregion


    //region darkmode

    public boolean isDarkMode() {
        return settings.isUseDarkmode();
    }

    public void setDarkMode(boolean darkmode) {
        settings.setUseDarkmode(darkmode);
    }

    //endregion


    //region text size

    public boolean isBigText() {
        return settings.isUseBigText();
    }

    public void setBigText(boolean text) {
        settings.setUseBigText(text);
    }

    //endregion


    public boolean isNotificationOn() {
        return settings.isSendNotification();
    }

    public void setNotification(boolean status) {
        settings.setSendNotification(status);
    }

    //region shoppinglist coustomization

    public String getShopHeader() {
        return shoppingEntries.getHeader();
    }

    public void setShopHeader(String header) {
        shoppingEntries.setHeader(header);
    }

    public String getRowMaker() {
        return shoppingEntries.getRowMarker();
    }

    public void setRowMaker(String marker) {
        shoppingEntries.setRowMarker(marker);
    }

    public String getSeperator() {
        return shoppingEntries.getSeperartor();
    }

    public void setSeperator(String seperator) {
        shoppingEntries.setSeperartor(seperator);
    }

    //endregion

}
