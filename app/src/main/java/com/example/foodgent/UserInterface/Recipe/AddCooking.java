package com.example.foodgent.UserInterface.Recipe;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Entities.RecipeItem;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.RecipeItemListAdapter;
import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AddCooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add Items";
    private boolean isFav = false;
    public static RecyclerView mRecyclerview;
    private static RecyclerView.LayoutManager mLayoutManager;
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


        final TextView recipeName = findViewById(R.id.stepList_currentStep);
        final TextView portions = findViewById(R.id.editText_recipeAdd_portions);
        final TextView time = findViewById(R.id.editText_recipeAdd_min);
        final TextView description = findViewById(R.id.editText_recipeAdd_desc);
        final Button favButton = findViewById(R.id.button_recipeAdd_favButton);
        final Spinner recipeType = findViewById(R.id.spinner_recipeType);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources res = getResources();
                Drawable img = null;
                if (isFav) {
                    img = res.getDrawable(R.drawable.heart_empty);
                } else {
                    img = res.getDrawable(R.drawable.heart_full);
                }
                isFav = !isFav;
                favButton.setBackground(img);


            }
        });


        Button addRecipe = findViewById(R.id.button_addRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Recipe recipe = new Recipe(
                        recipeName.getText().toString(),
                        description.getText().toString(),
                        reyclerAdapter.getNeededRecipeItems(),
                        tryParseInt(portions.getText().toString()),
                        tryParseInt(time.getText().toString()),
                        isFav,
                        null,
                        recipeType.getSelectedItem().toString());


                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(AddCooking.this);
                final View deleteView = getLayoutInflater().inflate(R.layout.dialog_ask_steplist_add, null);

                helpDialog.setView(deleteView);
                final AlertDialog help = helpDialog.create();


                final TextView helpTextView = deleteView.findViewById(R.id.textView_helpText);
                final Button helpNext = deleteView.findViewById(R.id.button_deleteNext);


                helpNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AddStepList.class);


                        Recipe r = new Recipe(
                                recipeName.getText().toString(),
                                description.getText().toString(),
                                reyclerAdapter.getNeededRecipeItems(),
                                tryParseInt(portions.getText().toString()),
                                tryParseInt(time.getText().toString()),
                                isFav,
                                null,
                                recipeType.getSelectedItem().toString());


                        Gson gson = new Gson();
                        intent.putExtra("recipe", gson.toJson(r));
                        startActivity(intent);
                    }
                });


                final Button buttonStop = deleteView.findViewById(R.id.button_deleteStop);
                buttonStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppData.getInstance().addRecipe(recipe);
                        AppData.getInstance().saveRecipe();
                        finish();
                    }
                });

                help.show();




                /*AppData.getInstance()
                        .addRecipe(


                AppData.getInstance().saveRecipe();
                RecipeActivity.setUpRecipeList();
                finish();*/
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
        ArrayList<RecipeItem> list = AppData.getInstance().getRecipeItems();

        ArrayList<RecipeItem> adapterList = new ArrayList<>();

        for (RecipeItem item : list) {
            adapterList.add(item);
        }

        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerAdapter = new RecipeItemListAdapter(adapterList, true);
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

    int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
