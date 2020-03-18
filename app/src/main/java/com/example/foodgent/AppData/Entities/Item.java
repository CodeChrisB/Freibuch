package com.example.foodgent.AppData.Entities;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

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

        // TODO: 18/03/2020 make the format better

        if (dateTime != null) {
            String currentDay = getCurrentDay();
            return currentDay;
        }

       /* if (dateTime != null)
            return String.format("%tY-%<tm-%<td", dateTime);

        return "----";*/
        return "----";
    }

    private String getCurrentDay() {
        @SuppressLint("DefaultLocale") String[] dates = String.format("%td/%<tm/%<tY", dateTime).split("/");
        int d = Integer.parseInt(dates[0]);
        int m = Integer.parseInt(dates[1]);
        int y = Integer.parseInt(dates[2]);

        if (m < 3) {
            m += 12;
            y -= 1;
        }

        int k = y % 100;
        int j = y / 100;

        int day = ((d + (((m + 1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;

        Resources res = MainActivity.getInstance().getResources();
        String[] weekDays = res.getStringArray(R.array.weekDays);
        return weekDays[day];
    }


}
