package com.foodgent.buchfrei.AppData.Entities;

public class RecipeItem {
    int amount;
    String name;
    String unit;

    public RecipeItem(int amount, String name, String unit) {
        this.amount = amount;
        this.name = name;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
