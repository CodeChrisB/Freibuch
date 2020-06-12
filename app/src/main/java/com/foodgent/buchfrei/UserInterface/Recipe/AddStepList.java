package com.foodgent.buchfrei.UserInterface.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Entity.RecipeItemListAdapter;
import com.foodgent.buchfrei.Entity.StepListAdapter;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AddStepList extends AppCompatActivity {

    private static final String TAG = "Add Items";
    public static RecyclerView mRecyclerview;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    RecipeItemListAdapter reyclerAdapter;
    ArrayList<String> list = new ArrayList<>();
    private boolean isFav = false;
    private boolean changeRecipe;
    private String oldName;

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage_recipelist);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        if (getIntent().getBooleanExtra("change", false)) {
            changeRecipe = true;
            oldName = getIntent().getStringExtra("old");
        }
        //get the intent in the target activity
        Intent intent = getIntent();

        Gson gson = new Gson();
        String json = intent.getStringExtra("recipe");

        final Recipe recipe = gson.fromJson(json, Recipe.class);


        Log.d(TAG, "OnCreate: Started");

        mListView = findViewById(R.id.recyclerView_recipeItem);
        setUpListView();

        final EditText step = findViewById(R.id.stepList_currentStep);


        Button button = findViewById(R.id.stepList_addStep);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add(step.getText().toString());
                setUpListView();
                //empty the textview for next step
                step.setText("");
            }
        });

        final Button addRecipe = findViewById(R.id.button_addRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (changeRecipe) {
                    AppData.getInstance().removeRecipe(oldName);
                }

                recipe.setSteps(list);
                AppData.getInstance().addRecipe(recipe);
                AppData.getInstance().saveRecipe();
                RecipeActivity.setUpRecipeList();//update the recipe list

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    public void setUpListView() {


        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        StepListAdapter adapter = new StepListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }
}
