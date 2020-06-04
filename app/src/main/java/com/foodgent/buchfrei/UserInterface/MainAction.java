package com.foodgent.buchfrei.UserInterface;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.FragmentChanger;
import com.foodgent.buchfrei.UserInterface.Item.AddItemActivity;
import com.foodgent.buchfrei.UserInterface.Recipe.AddCooking;

public class MainAction extends AppCompatActivity {
    MainActivity activity;
    FragmentChanger fragmentChanger;

    public MainAction(MainActivity activity, FragmentChanger fragmentChanger) {
        this.activity = activity;
        this.fragmentChanger = fragmentChanger;

        setMode();


    }

    private void setMode() {
        if (AppData.getInstance().isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void setFragment(String action) {
        if (action != null) {
            switch (action) {
                case "opnRecipe":
                    setViewPager(1);
                    break;
                case "opnShop":
                    setViewPager(2);
                    break;

            }
        }
    }

    public void setViewPager(int fragmentNumber) {
        fragmentChanger.change(fragmentNumber, MainActivity.getmViewPager());
    }

    public Intent getCorrectAddPage(int currentPage) {
        Intent intent;

        switch (currentPage) {
            case 0:
                return new Intent(MainActivity.getInstance(), AddItemActivity.class);

            case 1:
                return intent = new Intent(MainActivity.getInstance(), AddCooking.class);

            case 2:
                String s = AppData.getInstance().getFormattedShoppingList();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
                startActivity(Intent.createChooser(sharingIntent, "Einkaufsliste teilen..."));
        }
        return null;
    }
}
