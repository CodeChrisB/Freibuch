package com.example.foodgent.AppData.Entities;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    private String name, description;
    private Date dateTime;
    private int amount;
    private boolean isSelected = false;

    public Item(String name, String description, Date dateTime, int amount) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Amount: " + amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SuppressLint("DefaultLocale")
    public String getDateString() {

        return String.format("%tY-%<tm-%<td %<tH:%<tM:%<tS", dateTime);
    }
}
