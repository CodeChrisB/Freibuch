package com.example.fragment;

import com.example.fragment.AppData.Entities.Barcode;
import com.example.fragment.AppData.Entities.Item;
import com.example.fragment.AppData.Entities.Recipe;
import com.example.fragment.AppData.Logic.AppData;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

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

        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("12345",item1);
        Barcode barcode2 = new Barcode("12345",item2);
        Barcode barcode3 = new Barcode("12345",item3);


        appData.addBarcode(barcode1);
        appData.addBarcode(barcode2);
        appData.addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = appData.getBarcodes();

        assertEquals(3,barcodes.size());
    }

    @Test
    public void items_addTest(){
        AppData appData = new AppData();
        LocalDate date = LocalDate.now(); // Create a date object


        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);


        appData.addItem(item1);
        appData.addItem(item2);
        appData.addItem(item3);

        ArrayList<Barcode>  items = appData.getBarcodes();

        assertEquals(3,items.size());
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