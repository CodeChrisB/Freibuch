package com.foodgent.buchfrei.AppData.Entities;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.example.fragment.R;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    private String name, description, unit;
    private Date dateTime;
    private int amount;
    private boolean isSelected;

    public Item(String name, String description, Date dateTime, int amount, String unit) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.amount = amount;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
        return amount + ":" + name + ":" + unit;
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


        //get the day of the date
        if (dateTime != null) {
            String currentDay = getCurrentDay();

            String message = "";

            int remainingDays = getDifferenceDays(dateTime, new Date());

            //expired item
            if (remainingDays < 0) {

                //ist schon abgelaufen.
                message = "Abgelaufen!";

            } else if (remainingDays < 7) {

                //läuft diese woche ab
                message = "Diesen " + currentDay + ".";

            } else if (remainingDays < 14) {

                //läuft nächse woche ab
                message = "Läuft nächsten " + currentDay + " ab.";


            } else if (remainingDays > 365) {

                message = "Läuft in mehr als 1 Jahr ab.";
            } else {

                //läuft in N Wochen ab
                int weeks = remainingDays / 7;
                message = "Läuft in " + weeks + " Wochen ab.";

            }

            return message;
        }

        //get the days till trash date (Ablaufsdatum)

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

    private int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d1.getTime() - d2.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }
}
