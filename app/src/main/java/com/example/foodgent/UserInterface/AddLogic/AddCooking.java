package com.example.foodgent.UserInterface.AddLogic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.UserInterface.Fragment.RecipeActivity;
import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

import java.util.ArrayList;


public class AddCooking extends AppCompatActivity {

    private static final String TAG = "Add Items";


    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0,0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage_cooking);
        Log.d(TAG,"OnCreate: Started");


        final TextView recipeName = findViewById(R.id.eT_recipeName);

        Button addRecipe = findViewById(R.id.button_addRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("Essen");
                strings.add("Trinken");
                strings.add("Orbit kauen");

                AppData.getInstance().addRecipe(new Recipe(recipeName.getText().toString(), strings, null));
                AppData.getInstance().saveRecipe();
                RecipeActivity.setUpRecipeList();
                finish();
            }
        });


        Button navButton = findViewById(R.id.button_navSecond);
        navButton.bringToFront();
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCooking.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
