package com.foodgent.buchfrei.UserInterface.Recipe;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Recipe;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Entity.RecyclerViewAdapter;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
import com.foodgent.buchfrei.UserInterface.Shopping.ShopActivity;

import java.util.ArrayList;


public class RecipeActivity extends Fragment {

    private static final String TAG = "Fragment1";


    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;

    static public void setUpRecipeList() {
        ItemActivity.setNull();
        ShopActivity.setNull();
        ArrayList<Recipe> list = AppData.getInstance().getRecipes();

        mListView.setHasFixedSize(true);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, list);
        mListView.setLayoutManager(new GridLayoutManager(context, 3));
        mListView.setAdapter(adapter);
    }

    public static void setNull() {
        if (context != null) {
            ArrayList<Recipe> list = new ArrayList<>();

            mListView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, list);
            mListView.setLayoutManager(mLayoutManager);
            mListView.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getActivity()));

        Log.d(TAG, "onCreateView: started");
        mListView = view.findViewById(R.id.listView_recipes);
        context = getContext();
        setUpRecipeList();


        //region Prevent Fragment Close
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        //endregion The callback can be enabled or disabled here or in handleOnBackPressed()


        return view;

    }


}

