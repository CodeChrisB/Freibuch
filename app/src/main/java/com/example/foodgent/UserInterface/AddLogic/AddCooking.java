package com.example.foodgent.UserInterface.AddLogic;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.RecipeItemListAdapter;
import com.example.foodgent.UserInterface.Fragment.RecipeActivity;
import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

import java.util.ArrayList;


public class AddCooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add Items";
    public static RecyclerView mRecyclerview;
    private static RecyclerView.LayoutManager mLayoutManager;
    private boolean isFav = false;
    RecipeItemListAdapter reyclerAdapter;


    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage_cooking);
        Log.d(TAG, "OnCreate: Started");


        final TextView recipeName = findViewById(R.id.eT_recipeName);
        final TextView portions = findViewById(R.id.editText_recipeAdd_portions);
        final TextView time = findViewById(R.id.editText_recipeAdd_min);
        final Button favButton = findViewById(R.id.button_recipeAdd_favButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res = getResources();
                Drawable img = null;
                if (isFav) {
                    img = res.getDrawable(android.R.drawable.star_big_off);
                } else {
                    img = res.getDrawable(android.R.drawable.star_big_on);
                }
                isFav = !isFav;
                favButton.setBackground(img);


            }
        });


        Button addRecipe = findViewById(R.id.button_addRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance()
                        .addRecipe(
                                new Recipe(
                                        recipeName.getText().toString(),
                                        "Decs",
                                        reyclerAdapter.getNeededRecipeItems(),
                                        Integer.parseInt(portions.getText().toString()),
                                        Integer.parseInt(time.getText().toString()),
                                        isFav
                                ));


                AppData.getInstance().saveRecipe();
                RecipeActivity.setUpRecipeList();
                finish();
            }
        });

        Spinner foodType = findViewById(R.id.spinner_recipeType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.foodTypes, android.R.layout.simple_spinner_dropdown_item);

        foodType.setAdapter(adapter);
        foodType.setOnItemSelectedListener(this);

        Button navButton = findViewById(R.id.button_navSecond);
        navButton.bringToFront();
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCooking.this, MainActivity.class);
                startActivity(intent);
            }
        });


        setUpListView();


    }


    public void setUpListView() {
        mRecyclerview = findViewById(R.id.recyclerView_recipeItem);
        // TODO: 01/04/2020 Add Cooking list 
        ArrayList<String> list = AppData.getInstance().getRecipeItems();

        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerAdapter = new RecipeItemListAdapter(list);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(reyclerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
