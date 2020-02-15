package com.example.fragment;

import com.example.fragment.AppData.Entities.Barcode;
import com.example.fragment.AppData.Entities.Item;
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
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void barcodeToArray(){

        AppData appData = new AppData();



        LocalDate date = LocalDate.now(); // Create a date object

        Item item1 = new Item("Item 1","This is the First Item", date,-1);
        Item item2 = new Item("Item 2","This is the Second Item", date,-1);
        Item item3 = new Item("Item 3","This is the Third Item", date,-1);

        Barcode barcode1 = new Barcode("12345",item1);
        Barcode barcode2 = new Barcode("12345",item2);
        Barcode barcode3 = new Barcode("12345",item3);


        AppData.getInstance().addBarcode(barcode1);
        AppData.getInstance().addBarcode(barcode2);
        AppData.getInstance().addBarcode(barcode3);

        ArrayList<Barcode>  barcodes = AppData.getInstance().getBarcodes();

        assertEquals(3,barcodes.size());
    }
}