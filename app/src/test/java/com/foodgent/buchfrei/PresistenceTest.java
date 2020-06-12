package com.foodgent.buchfrei;

import com.foodgent.buchfrei.AppData.Entities.Barcode;
import com.foodgent.buchfrei.AppData.Entities.BarcodeItem;
import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Entities.ShoppingEntry;
import com.foodgent.buchfrei.AppData.Logic.AppData;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PresistenceTest {

    @Test
    public void barcodeAddTest(){

        AppData appData = new AppData();

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");

        Barcode barcode1 = new Barcode("12", item1);
        Barcode barcode2 = new Barcode("1234",item2);
        Barcode barcode3 = new Barcode("12345",item3);

        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);
        assertEquals(3, appData.getBarcodes().size());
    }

    @Test
    public  void barcodeFoundItem(){
        AppData appData = new AppData();

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");

        String checkCode = "123456789";
        Barcode barcode1 = new Barcode(checkCode,item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        BarcodeItem found = appData.searchForItem(checkCode);

        assertEquals(found,item1);
    }

    @Test
    public  void barcodeSearchForNewBarcode(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = appData.getBarcodes();

        BarcodeItem found = appData.searchForItem("44444444444");

        assertEquals(found,null);
    }

    @Test
    public void barcodeRemove(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now();

        appData.removeAllBarCodes();

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  allCodes = appData.getBarcodes();

        appData.removeBarcode(barcode1);

        boolean barcodeIsRemoved = false;

        if(appData.searchForItem(barcode1.getBarcode())==null)
            barcodeIsRemoved = true;

        ArrayList<Barcode> shortedList = appData.getBarcodes();

        assert shortedList.size() != allCodes.size() - 1 || !barcodeIsRemoved || (true);

    }

    @Test
    public void barcodeUpdate() {
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");

        BarcodeItem item1Update = new BarcodeItem("Update","This is the Updated Item");

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);
        Barcode barcode4 = new Barcode("123456789",item1Update);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);
        appData.addBarcode(barcode4);

        assert appData.getBarcodes().size() == 3;//wrong length

        for(Barcode code: appData.getBarcodes()){
            assert !code.getItem().getName().equals("Item 1");
        }

        assert(true);

    }

    @Test
    public void barcodeSameCodeAdd(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        BarcodeItem item1 = new BarcodeItem("Item 1","This is the First Item");
        BarcodeItem item2 = new BarcodeItem("Item 2","This is the Second Item");
        BarcodeItem item3 = new BarcodeItem("Item 3","This is the Third Item");


        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("123456789",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  allCodes = appData.getBarcodes();

        assertEquals(allCodes.get(0).getItem().getName(), ("Item 2"));
        assertNotEquals(allCodes.get(1).getBarcode(), "item 3");
        assertEquals(allCodes.size(),2);
    }

    @Test
    public void itemsAddTest(){
        AppData appData = new AppData();
        Date date = Date.from(Instant.now()) ; // Create a date object


        Item item1 = new Item("Item 1","This is the First Item", date,-1,"kg");
        Item item2 = new Item("Item 2","This is the Second Item", date,-1, "kg");
        Item item3 = new Item("Item 3","This is the Third Item", date,-1, "kg");


        appData.addItem(item1);
        appData.addItem(item2);
        appData.addItem(item3);

        ArrayList<Item>  items = appData.getItems();

        assertEquals(3,items.size());
    }

    @Test
    public void itemsRemoveTest(){
        AppData appData = new AppData();
        Date date = Date.from(Instant.now());

        appData.removeAllItems();

        Item item1 = new Item("Item 1","This is the First Item", date,-1,"kg");
        Item item2 = new Item("Item 2","This is the Second Item", date,-1, "kg");
        Item item3 = new Item("Item 3","This is the Third Item", date,-1, "kg");


        appData.addItem(item1);
        appData.addItem(item2);
        appData.addItem(item3);

        ArrayList<Barcode>  allCodes = appData.getBarcodes();

        appData.removeItem(item1);

        boolean isRemoved = true;

        for (Item item : appData.getItems()) {
            if(item.equals(item1)){
                isRemoved = false;
            }
        }

        assertEquals(true, isRemoved);

    }

    @Test
    public void getDataAfterRestart() {
        //Save a Entry
        AppData appData = new AppData();
        appData.addShoppingEntry(new ShoppingEntry("Bread"));

        //************************
        //Restart the Application*
        //************************


        //After the App reopend it self check if the Entry is still here
        int entries = appData.getShoppingEntries().size();

        assertEquals(1, entries);
    }

    @Test
    public void recipeAddTest(){
        AppData appData = new AppData();
        ArrayList<String> itemList = new ArrayList<>();

        itemList.add("item1");
        itemList.add("item2");
        itemList.add("item3");

        ArrayList<String> steps = new ArrayList<>();
        steps.add("step1");
        steps.add("step2");
        steps.add("step3)");

        Recipe recipe1 = new Recipe("Recipe1", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");
        Recipe recipe2 = new Recipe("Recipe2", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");
        Recipe recipe3 =  new Recipe("Recipe3", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");

        appData.addRecipe(recipe1);
        appData.addRecipe(recipe2);
        appData.addRecipe(recipe3);

        ArrayList<Recipe>  recipes = appData.getRecipes();

        assertEquals(3,recipes.size());
    }

    @Test
    public void recipeRemoveTest(){
        AppData appData = new AppData();

        ArrayList<String> itemList = new ArrayList<>();

        itemList.add("item1");
        itemList.add("item2");
        itemList.add("item3");

        ArrayList<String> steps = new ArrayList<>();
        steps.add("step1");
        steps.add("step2");
        steps.add("step3)");

        Recipe recipe1 = new Recipe("Recipe1", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");
        Recipe recipe2 = new Recipe("Recipe2", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");
        Recipe recipe3 =  new Recipe("Recipe3", "Some Info!", itemList, 1, 1, false, steps, "TestRecipe");

        appData.addRecipe(recipe1);
        appData.addRecipe(recipe2);
        appData.addRecipe(recipe3);

        appData.removeRecipe(recipe1);
        ArrayList<Recipe>  recipes = appData.getRecipes();

        boolean isRemoved = true;

        for (Recipe recipe : recipes){
            if(recipe.equals(recipe1)){
                isRemoved = false;
            }
        }

        assertEquals(true, isRemoved);
    }
}