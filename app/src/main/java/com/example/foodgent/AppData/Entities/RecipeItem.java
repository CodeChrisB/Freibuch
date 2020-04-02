package com.example.foodgent.AppData.Entities;

public class RecipeItem {
    int amount;
    String name;

    public RecipeItem(int amount, String name) {
        this.amount = amount;
        this.name = name;
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
