package com.example.foodgent.AppData.Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Item implements Serializable {

    String name,description;
    LocalDate dateTime;
    int amount;
    boolean isSelected = false;

    public Item(String name, String description, LocalDate dateTime, int amount) {
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

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
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
        return name + " " + description + " Amount: " + amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
