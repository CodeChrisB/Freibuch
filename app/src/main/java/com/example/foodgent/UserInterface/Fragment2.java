package com.example.foodgent.UserInterface;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.ItemListAdapter;
import com.example.foodgent.Entity.RecipeListAdapter;
import com.example.fragment.R;

import java.util.ArrayList;


public class Fragment2 extends Fragment {

    private static final String TAG = "Fragment1";


    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;

    static public void setUpRecipeList() {
        Fragment1.setNull();
        Fragment3.setNull();
        ArrayList<Recipe> list = AppData.getInstance().getRecipes();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        RecipeListAdapter adapter = new RecipeListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    static void setNull() {
        if (context != null) {
            ArrayList<Item> list = new ArrayList<>();

            mListView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            ItemListAdapter adapter = new ItemListAdapter(list);
            mListView.setLayoutManager(mLayoutManager);
            mListView.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);


        Log.d(TAG, "onCreateView: started");
        mListView = view.findViewById(R.id.listView_recipes);
        context = getContext();
        setUpRecipeList();


        return view;

    }


}

