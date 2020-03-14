package com.example.foodgent.UserInterface;


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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.ItemListAdapter;
import com.example.fragment.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    private static final String TAG = "Fragment1";


    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;
    private boolean appStart = true;

    static public void setUpItemListView() {
        Fragment2.setNull();
        Fragment3.setNull();

        mListView = MainActivity.getInstance().findViewById(R.id.listView_items);
        ArrayList<Item> list = AppData.getInstance().getItems();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        ItemListAdapter adapter = new ItemListAdapter(list);
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
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);


            Log.d(TAG, "onCreateView: started");
        if (appStart) {
            context = getContext();
        } else {
            mListView = view.findViewById(R.id.listView_items);
            setUpItemListView();
        }


        //region Prevent Fragment Close
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        //endregion The callback can be enabled or disabled here or in handleOnBackPressed()


        appStart = false;
            return view;

    }


}
