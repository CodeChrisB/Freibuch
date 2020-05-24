package com.foodgent.buchfrei.UserInterface.Recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.Entity.RecipeItemListAdapter;
import com.foodgent.buchfrei.Entity.StepListAdapter;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.google.gson.Gson;

public class CookNowActivity extends AppCompatActivity {

    public static RecyclerView mRecyclerview;
    private static RecyclerView.LayoutManager mLayoutManager;
    private Recipe recipe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_now);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        Bundle extras = getIntent().getExtras();

        Gson gson = new Gson();
        String json = extras.getString("recipe");
        recipe = gson.fromJson(json, Recipe.class);


        TextView name = findViewById(R.id.cooknow_recipeName);
        name.setText(recipe.getName());


        Button ingredients = findViewById(R.id.button_ingredientsShow);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpRecipeItems();
            }
        });

        Button tutorial = findViewById(R.id.button_tutorialShow);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpTutorial();
            }
        });


        setUpRecipeItems();
    }


    public void setUpRecipeItems() {

        mRecyclerview = findViewById(R.id.cooknow_recylerView);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecipeItemListAdapter reyclerAdapter = new RecipeItemListAdapter(recipe.getListOfItems(), false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(reyclerAdapter);
    }

    public void setUpTutorial() {
        mRecyclerview = findViewById(R.id.cooknow_recylerView);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        StepListAdapter reyclerAdapter = new StepListAdapter(recipe.getSteps());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(reyclerAdapter);
    }
}
