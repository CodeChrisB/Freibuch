package com.foodgent.buchfrei.UserInterface.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;

import java.util.ArrayList;

public class ItemsNotAvailableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_items_not_available);

        Button ignore = findViewById(R.id.recipe_ignoreItems);
        Button shoppingList = findViewById(R.id.recipe_createShoppingList);

        ArrayList na = AppData.getGson().fromJson(getIntent().getStringExtra("na"), ArrayList.class);

        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipe = getIntent().getStringExtra("recipe");
                Intent intent = new Intent(getApplicationContext(), CookNowActivity.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }
        });

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
