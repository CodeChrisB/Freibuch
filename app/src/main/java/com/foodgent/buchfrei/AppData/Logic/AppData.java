package com.foodgent.buchfrei.AppData.Logic;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Barcode;
import com.foodgent.buchfrei.AppData.Entities.BarcodeItem;
import com.foodgent.buchfrei.AppData.Entities.Gesture;
import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Entities.Settings;
import com.foodgent.buchfrei.AppData.Entities.ShoppingEntry;
import com.foodgent.buchfrei.AppData.MainLists.Barcodes;
import com.foodgent.buchfrei.AppData.MainLists.Items;
import com.foodgent.buchfrei.AppData.MainLists.RecipeItems;
import com.foodgent.buchfrei.AppData.MainLists.Recipes;
import com.foodgent.buchfrei.AppData.MainLists.ShoppingEntries;
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
    private Gesture gesture;
    protected static RecipeItems recipeItems = new RecipeItems();
    //endregion


    //region Gson & InternalStorage Init
    private static Gson gson = new Gson();
    private InternalStorage internalStorage = new InternalStorage();

    public static Gson getGson() {
        return gson;
    }
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
        String gest = internalStorage.loadData("gest");
        String setting = internalStorage.loadData("settings");
        String recipeItem = internalStorage.loadData("recipeItems");

        recipes = (recipe != null) ? loadRecipe(recipe) : setRecipe();

        if(!standardRecipesExits()){
            addStandardRecipes();
        }
        //recipeItems = (recipeItem != null) ? loadRecipeItem(recipeItem) : setRecipeItems();
        recipeItems = (recipeItem != null) ? loadRecipeItem(recipeItem) : setRecipeItems();
        items = (item != null) ? loadItem(item) : setItem();
        barcodes = (barcode != null) ? loadBarcode(barcode) : setBarcode();
        shoppingEntries = (shopping != null) ? loadShopping(shopping) : setShopping();
        settings = (setting != null) ? loadSetting(setting) : setSetting();
        gesture = (gest != null) ? loadGest(gest) : setGesture();

        //endregion
    }

    private Gesture loadGest(String setting) {
        return (!setting.equals("null")) ? gson.fromJson(setting, Gesture.class) : setGesture();
    }

    private Gesture setGesture() {
        return new Gesture();
    }

    public Gesture getGesture() {

        if (gesture == null) {
            gesture = new Gesture();
        }
        return gesture;

    }

    private void addStandardRecipes() {
        /*for (Recipe r : getJson()) {
            recipes.addTo(r);
        }*/
    }

    private boolean standardRecipesExits() {

        /*boolean r;
        //without checking for null using an object!?
        if (recipes == null)
            recipes = new Recipes();


        //this does not say if we have standard recipes
       // we could check for a specific file name in the
       // assests or parse this file into an Recipes() and check
       // this if its empty.

        r = !this.getRecipes().isEmpty();

*/
        return true;
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

    public void setGesture(Gesture gesture) {
        this.gesture = gesture;
    }

    public boolean saveAppData() {

        //region Save all Values
        try {
            saveRecipe();
            saveShopEntries();
            saveItems();
            saveSettings();
            saveSettings();
            saveBarcode();
            saveRecipeItems();
            saveGesture();
        } catch (Exception ex) {
            return false;
        }
        return true;
        //endregion
    }

    public ArrayList<Recipe> getJson() {
        /*
        String json;
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            InputStream is = MainActivity.getInstance().getAssets().open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();
            recipes = gson.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipes;
         */
        return new ArrayList<>();
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

    public boolean savePremium() {
        try {
            internalStorage.saveData("premium", gson.toJson(premium));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveGesture() {
        try {
            internalStorage.saveData("gest", gson.toJson(gesture));
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

        addStandardRecipes();
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

    public void removeRecipe(String recipe) {
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
        premium = set ? "yes" : "no";
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
        if (settings == null)
            return false;
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


    //region CrashData
    String message;
    String stacktrace;
    boolean handleError = false;

    public void setError(String message, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
        handleError = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public boolean handleError() {
        return handleError;
    }


    //widget stuff
    public String getEntryAt(int index) {
        return shoppingEntries.getEntryAt(index).toString();
    }

    public int ShoppingEntrySize() {
        return shoppingEntries.size();
    }

    public String getUnitFromName(String s) {
        for (int i = 0; i < recipeItems.size(); i++) {
            if (recipeItems.getAt(i).split(":")[1].equals(s))
                return recipeItems.getUnitAt(i);
        }
        return "Keine Einheit gefunden!";
    }

    public void addAllItems(ArrayList<Item> itemList) {
        items.addAll(itemList);
    }


    public void addShoppingList(ArrayList<String> list) {
        shoppingEntries.addList(list);
    }

    public int getFoodIcon(String type) {
        int image;
        boolean isDarkMode = this.isDarkMode();
        if (type.equals("Fleisch")) {
            image = isDarkMode ? R.drawable.steak_dark : R.drawable.steak;
        } else if (type.equals("Vegetarisch")) {
            image = isDarkMode ? R.drawable.vegan_dark : R.drawable.vegan;
        } else if (type.equals("Vegan")) {
            image = isDarkMode ? R.drawable.veggie_dark : R.drawable.veggie;
        } else {
            image = isDarkMode ? R.drawable.cake_dark : R.drawable.cake;
        }

        return image;
    }
}
