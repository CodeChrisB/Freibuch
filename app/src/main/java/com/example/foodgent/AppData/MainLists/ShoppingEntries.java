package com.example.foodgent.AppData.MainLists;

import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.AppData.Logic.SaveArrayAble;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingEntries implements SaveArrayAble<ShoppingEntry>, Serializable {

    ArrayList<ShoppingEntry> shoppingEntries = new ArrayList<>();
    String rowMarker = "";
    private String header = "";

    public ShoppingEntries(){

    }

    public String getHeader() {
        return header.equals("") ? "\uD83D\uDED2 Meine Einkaufsliste: " : header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRowMarker() {
        return rowMarker.equals("") ? "-" : rowMarker;
    }

    public void setRowMarker(String rowMarker) {
        this.rowMarker = rowMarker;
    }

    @Override
    public ArrayList<ShoppingEntry> getArray() {
        return shoppingEntries;
    }

    @Override
    public boolean addTo(ShoppingEntry object) {

        //add  if less than 10 items or premium is active
        if (shoppingEntries.size() <= 10 || AppData.getInstance().isPremium()) {
            shoppingEntries.add(object);
            return true;
        }
        return false;

    }

    @Override
    public void removeObject(ShoppingEntry object) {
        shoppingEntries.remove(object);
    }

    @Override
    public void removeAll() {
        shoppingEntries = new ArrayList<ShoppingEntry>();
    }

    @Override
    public ArrayList<ShoppingEntry> getObject() {
        return shoppingEntries;
    }

    @Override
    public void setObject(ArrayList<ShoppingEntry> objectList) {
        shoppingEntries = objectList;
    }

    public String toFormatedList() {

        if (header.equals(""))
            header = "\uD83D\uDED2 Meine Einkaufsliste: ";
        if (rowMarker.equals(""))
            rowMarker = "-";

        //Create Header
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader());
        sb.append(System.getProperty("line.separator"));
        sb.append("====================");
        sb.append(System.getProperty("line.separator"));


        //call toString Method for every Entry in the List
        for (ShoppingEntry item : AppData.getInstance().getShoppingEntries()) {
            sb.append(rowMarker + item.toString());
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public void removeSelected() {
        //to remove all we create a new list with all unselected entries and overwrite the list with it
        ArrayList<ShoppingEntry> list = new ArrayList<>();
        for (int i = 0; i < shoppingEntries.size(); i++) {
            if (!shoppingEntries.get(i).isSelected())
                list.add(shoppingEntries.get(i));
        }
        shoppingEntries = list;
    }
}
