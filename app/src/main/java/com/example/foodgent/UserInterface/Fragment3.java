package com.example.foodgent.UserInterface;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.ItemListAdapter;
import com.example.foodgent.Entity.ShopListAdapter;
import com.example.fragment.R;

import java.util.ArrayList;


public class Fragment3 extends Fragment {

    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;

    static public void setUpShoppingList() {

        Fragment1.setNull();
        Fragment2.setNull();
        ArrayList<ShoppingEntry> list = AppData.getInstance().getShoppingEntries();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        ShopListAdapter adapter = new ShopListAdapter(list);
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
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);


        mListView = view.findViewById(R.id.listView_shopping);
        context = getContext();
        setUpShoppingList();

        final EditText shoppingEntry = view.findViewById(R.id.editText_addShopEntry);
        final Button addEntry = view.findViewById(R.id.button_addShoppingEntry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entry = shoppingEntry.getText().toString();
                AppData.getInstance().addShoppingEntry(new ShoppingEntry(entry));
                setUpShoppingList();
            }
        });


        return view;

    }


}
