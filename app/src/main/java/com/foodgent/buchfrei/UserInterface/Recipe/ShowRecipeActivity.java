package com.foodgent.buchfrei.UserInterface.Recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowRecipeActivity extends AppCompatActivity {


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_modern);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        Bundle extras = getIntent().getExtras();

        final Gson gson = new Gson();
        String json = extras.getString("recipe");
        final Recipe recipe = gson.fromJson(json, Recipe.class);

        TextView name = findViewById(R.id.textView_showRecipe_name);
        name.setText(recipe.getName());

        TextView desc = findViewById(R.id.textView_showRecipe_desc);
        desc.setText(recipe.getDescription());


        TextView portions = findViewById(R.id.textView_showRecipe_portions);
        if (recipe.getPortions() > 0) {
            portions.setText(" Portionen " + recipe.getPortions() + " ");
        } else {
            portions.setVisibility(View.GONE);
        }

        TextView type = findViewById(R.id.textView_showRecipe_type);
        if (!recipe.getRecipeType().equals("")) {
            type.setText(" " + recipe.getRecipeType() + " ");
        } else {
            type.setVisibility(View.GONE);
        }


        TextView time = findViewById(R.id.textView_showRecipe_time);
        if (recipe.getTime() > 0) {
            time.setText(" " + recipe.getTime() + " Minuten ");
        } else {
            time.setVisibility(View.GONE);
        }

        TextView fav = findViewById(R.id.textView_showRecipe_fav);
        if (recipe.isFavourite()) {
            fav.setText(" Favorit ❤️ ");
        } else {
            fav.setVisibility(View.GONE);
        }
        //setInfo(recipe);


        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion

        ConstraintLayout innerConstraint = findViewById(R.id.innerConstraint);


        Button cookNow = findViewById(R.id.button_showRecipeCookNow);
        cookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = recipe.getListOfItems();
                CheckItems(list);
                Toast.makeText(ShowRecipeActivity.this, "Test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CookNowActivity.class);
                intent.putExtra("recipe", gson.toJson(recipe));
                startActivity(intent);
            }
        });


    }

    private boolean CheckItems(ArrayList<String> recipeList) {

        ArrayList<String> thingsNotAvailable = new ArrayList<>();

        //ask the user to subtract items from list
        boolean removeItems = true;
        if (!removeItems)
            return false;

        AppData app = AppData.getInstance();
        ArrayList<Item> itemList = AppData.getInstance().getItems();

        for (String s : recipeList) {
            //setup the current item data
            String recipeItemName = s.split(":")[1];
            int recipeAmount = Integer.parseInt(s.split(":")[0]);

            //check if this is in the fridge
            for (int i = 0; i < itemList.size(); i++) {
                String name = itemList.get(i).getName();

                if (recipeItemName.equals(name)) {
                    //// TODO: 20/05/2020  we found something in the fride do stuff
                    int currentAmount = itemList.get(i).getAmount();

                    //how much is left of the item after we
                    //subtract the needed amount
                    int amount = currentAmount - recipeAmount;

                    /*
                    Amount:
                    positive --> we have enoguh
                    0 ---> remove from list
                    negative --> remove from list, Math.abs on shopping list
                     */
                    if (amount > 0) {
                        itemList.get(i).setAmount(amount);
                    } else if (amount == 0) {
                        itemList.remove(i);
                    } else {


                        amount = Math.abs(amount);
                        String notAvailableItem = name + ":" + amount + " " + itemList.get(i).getUnit();
                        thingsNotAvailable.add(notAvailableItem);

                        //remove from list
                        itemList.remove(i);

                    }

                    app.removeAllItems();
                    app.addAllItems(itemList);


                    //do we have enoguh of the item?
                    //if so just subtract the needed ones
                    //if not make a list with name and amount to create
                    //the shopping list later
                }
            }
        }
        return true;
    }


}
