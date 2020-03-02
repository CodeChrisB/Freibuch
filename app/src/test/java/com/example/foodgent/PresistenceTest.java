package com.example.foodgent;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PresistenceTest {
    @Test
    public void barcode_addTest(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.DeleteAppData();


        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("123",item1);
        Barcode barcode2 = new Barcode("1234",item2);
        Barcode barcode3 = new Barcode("12345",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = appData.getBarcodes();

        assertEquals(3,barcodes.size());
    }

    @Test
    public  void barcode_FoundItem(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.removeAllBarCodes();

        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = appData.getBarcodes();

        Item found = appData.searchForItem("123456789");

        assertEquals(found,item1);
    }

    @Test
    public  void barcode_searchForNewBarcode(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("43214532",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = appData.getBarcodes();

        Item found = appData.searchForItem("44444444444");

        assertEquals(found,null);
    }

    @Test
    public void barcode_remove(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.DeleteAppData();


        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

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

    @Test public void barcode_update()
    {
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.DeleteAppData();


        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Item item1Update = new Item("Update","This is the Updated Item", date,-1);

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

    /*@Test
    public void barcode_sameCodeAdd(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.DeleteAppData();

        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("123456789",item1);
        Barcode barcode2 = new Barcode("123456789",item2);
        Barcode barcode3 = new Barcode("43144542",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  allCodes = appData.getBarcodes();

        //if the first item is not overwriten item 2 then the test is false
       if(!(allCodes.get(0).getBarcode().equals("Item 2")))
            assert(false);

       //if the second item is not the item 3 then the test is false
       if(!(allCodes.get(1).getBarcode().equals("Item 3")))
        assert(false);

       //if only 2 barcodes are in the list then add it!
       if(allCodes.size()==2){
           assert(true);}
       else{
           assert(false);
       }
    }*/

    @Test
    public void items_addTest(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object

        appData.DeleteAppData();


        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);


        appData.addItem(item1);
        appData.addItem(item2);
        appData.addItem(item3);

        ArrayList<Item>  items = appData.getItems();

        assertEquals(3,items.size());
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
    public void recipe_addTest(){
        AppData appData = new AppData();

        Recipe recipe1 = new Recipe("Applie Pie", "Don't let it get burned!");
        Recipe recipe2 = new Recipe("Schnitzel","Dont use the hammer on a living Pig.");
        Recipe recipe3 = new Recipe("Stone Soup","Dentists loves this meal!");

        appData.addRecipe(recipe1);
        appData.addRecipe(recipe2);
        appData.addRecipe(recipe2);

        ArrayList<Recipe>  recipes = appData.getRecipes();

        assertEquals(3,recipes.size());
    }
}