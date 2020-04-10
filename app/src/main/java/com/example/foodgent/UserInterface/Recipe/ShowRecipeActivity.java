package com.example.foodgent.UserInterface.Recipe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.fragment.R;
import com.google.gson.Gson;

public class ShowRecipeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_recipe_modern);

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
                Intent intent = new Intent(getApplicationContext(), CookNowActivity.class);
                intent.putExtra("recipe", gson.toJson(recipe));
                startActivity(intent);
            }
        });


    }


}
