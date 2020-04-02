package com.example.foodgent.UserInterface;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.fragment.R;
import com.google.gson.Gson;

public class ShowRecipeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe);

        Bundle extras = getIntent().getExtras();

        Gson gson = new Gson();
        String json = extras.getString("recipe");
        Recipe recipe = gson.fromJson(json, Recipe.class);

        TextView name = findViewById(R.id.textView_showRecipe_name);
        name.setText(recipe.getName());


        setInfo(recipe);


        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion


    }

    private void setInfo(Recipe recipe) {
        //init the needed fields
        String recipeType = recipe.getRecipeType();
        String name = recipe.getName();
        int portions = recipe.getPortions();
        int timeMinutes = recipe.getTime();
        boolean isFav = recipe.isFavourite();
        String fav = "Lieblingsrezept : ";
        fav += isFav ? "Ja" : "Nein";


        //add to string if the information is available
        String text = "";
        if (!recipeType.equals(""))
            text += "•Recipe Type " + recipeType;

        if (!name.equals(""))
            text += "\n•Name " + name;

        if (portions > 0)
            text += "\n•Portionen" + portions;

        if (timeMinutes > 0)
            text += "\n•Zeit " + timeMinutes;


        text += "\n•" + fav;


        //set the information we got from the fields into the TextView
        text = text.replace("\n", "&lt;br&gt;");
        TextView informationText = findViewById(R.id.textView_showRecipe_information);
        informationText.setText(Html.fromHtml(Html.fromHtml(text).toString()));
    }
}
