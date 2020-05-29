package com.foodgent.buchfrei.UserInterface.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.util.ArrayList;

public class ItemsNotAvailableActivity extends AppCompatActivity {
    private ArrayList<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_items_not_available);

        Button ignore = findViewById(R.id.recipe_ignoreItems);
        Button shoppingList = findViewById(R.id.recipe_createShoppingList);

        ArrayList na = AppData.getGson().fromJson(getIntent().getStringExtra("na"), ArrayList.class);

        list = setList(na);

        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCookNow();
            }
        });

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().addShoppingList(list);
                startMain();
            }
        });
    }

    private void startMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("action", "opnShop");
        startActivity(intent);
    }

    private void startCookNow() {
        String recipe = getIntent().getStringExtra("recipe");
        Intent intent = new Intent(getApplicationContext(), CookNowActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }

    private ArrayList<String> setList(ArrayList<String> na) {
        TextView textList = findViewById(R.id.recipe_notAvailableList);
        String textForList = "";
        ArrayList<String> list = new ArrayList<>();

        for (String line : na) {
            String[] data = line.split(":");
            String newLine = data[0] + "    " + data[1] + " " + data[2];
            textForList += newLine;
            list.add(newLine);
        }

        textList.setText(textForList);
        return list;
    }
}
