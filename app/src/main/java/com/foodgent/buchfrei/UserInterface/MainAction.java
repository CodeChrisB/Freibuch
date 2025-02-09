package com.foodgent.buchfrei.UserInterface;

import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import com.example.fragment.R;
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

    public static void setGestureArea() {
        Guideline guideLine = MainActivity.getInstance().findViewById(R.id.guideLineGesture);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();

        Float value = AppData.getInstance().getGesture().isAny() ? 0.9f : 1f;
        params.guidePercent = value;

        guideLine.setLayoutParams(params);
    }

    public Intent getCorrectAddPage(int currentPage) {
        Intent intent;

        switch (currentPage) {
            case 0:

                intent = new Intent(MainActivity.getInstance(), AddItemActivity.class);
                intent.putExtra("type", "item");
                return intent;

            case 1:

                intent = new Intent(MainActivity.getInstance(), AddCooking.class);
                intent.putExtra("type", "cook");
                return intent;

            case 2:
                String s = AppData.getInstance().getFormattedShoppingList();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
                sharingIntent.putExtra("type", "shop");
                return sharingIntent;
        }
        return null;
    }



    public void nextViewPager() {
        fragmentChanger.nextPage();
    }

    public void prevViewPager() {
        fragmentChanger.prevPage();
    }

    public void setViewPager(int fragmentNumber) {
        fragmentChanger.change(fragmentNumber, MainActivity.getmViewPager());

        //set the correct add logo
        Button addButton = MainActivity.getInstance().findViewById(R.id.button_add);

        int draw = fragmentNumber == 2 ? R.drawable.share : R.drawable.add;
        addButton.setBackgroundResource(draw);
    }

    public void startAddIntent(Intent intent) {
        if (!intent.getStringExtra("type").equals("shop")) {
            MainActivity.getInstance().getContext().startActivity(intent);
        } else {
            MainActivity.getInstance().getContext().startActivity(Intent.createChooser(intent, "Einkaufsliste teilen..."));
        }
    }
}
