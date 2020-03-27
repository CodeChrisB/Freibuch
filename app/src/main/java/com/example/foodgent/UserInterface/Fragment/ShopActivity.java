package com.example.foodgent.UserInterface.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

import java.util.ArrayList;


public class ShopActivity extends Fragment {

    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;
    private EditText shoppingEntry;
    private View addBackground;
    private View addForeground;
    private TextView closeAdd;
    static View view;

    static public void setUpShoppingList() {

        ItemActivity.setNull();
        RecipeActivity.setNull();
        ArrayList<ShoppingEntry> list = AppData.getInstance().getShoppingEntries();


        if (mListView != null) {
            mListView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            ShopListAdapter adapter = new ShopListAdapter(list);
            mListView.setLayoutManager(mLayoutManager);
            mListView.setAdapter(adapter);
        }
    }

    public static void setNull() {
        if (context != null) {
            ArrayList<Item> list = new ArrayList<>();

            mListView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            ItemListAdapter adapter = new ItemListAdapter(list);
            mListView.setLayoutManager(mLayoutManager);
            mListView.setAdapter(adapter);
        }
    }

    private static ItemActivity instance = null;

    private void removeSelected() {
        AppData.getInstance().removeSelectedShoppingEntries();
        setUpShoppingList();
        AppData.getInstance().saveShopEntries();
    }

    private void showAddFields() {
        addBackground.setVisibility(View.VISIBLE);
        addBackground.bringToFront();

        addForeground.setVisibility(View.VISIBLE);
        addForeground.bringToFront();

        closeAdd.setVisibility(View.VISIBLE);
        closeAdd.bringToFront();
        closeAdd.setMinimumHeight(closeAdd.getWidth());

        shoppingEntry.setVisibility(View.VISIBLE);
        shoppingEntry.bringToFront();


    }


    private void closeAdd() {
        closeKeyboard();
        addBackground.setVisibility(View.INVISIBLE);
        addForeground.setVisibility(View.INVISIBLE);
        closeAdd.setVisibility(View.INVISIBLE);
        shoppingEntry.setText("");
        shoppingEntry.setVisibility(View.INVISIBLE);

    }

    private void add() {
        String entry = shoppingEntry.getText().toString();

        if (entry.length() > 50) {
            Toast.makeText(MainActivity.getInstance().getContext(), "Proudktbezeichnung zu lang.", Toast.LENGTH_LONG).show();
            return;
        }

        if (entry.length() > 0) {


            boolean added = AppData.getInstance().addShoppingEntry(new ShoppingEntry(entry));
            if (added) {
                AppData.getInstance().saveShopEntries();
                setUpShoppingList();
                shoppingEntry.setText("");
                closeKeyboard();
            } else {
                Toast.makeText(context, "Die Standard Liste ist voll, hol dir die FoodGent Premium", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void closeKeyboard() {
        View view = MainActivity.getInstance().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) MainActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        //normaler weise steht hier der code um das fragment zu schließen
        //jedoch wollen wir das nicht


    }



    public static ItemActivity getInstance() {
        if (instance == null)
            instance = new ItemActivity();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);
        ShopActivity.view = view;
        addBackground = view.findViewById(R.id.view_addBackground);
        addForeground = view.findViewById(R.id.view_addForeground);
        mListView = view.findViewById(R.id.listView_shopping);
        closeAdd = view.findViewById(R.id.view_closeAdd);
        closeAdd.setText("➡");


        closeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAdd();
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        context = getContext();
        setUpShoppingList();

        shoppingEntry = view.findViewById(R.id.editText_addShopEntry);
        final View addEntry = view.findViewById(R.id.view_showAddShopEntry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        shoppingEntry.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    add();
                }
                return false;
            }
        });


        //region Prevent Fragment Close
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        //endregion The callback can be enabled or disabled here or in handleOnBackPressed()


        View showAdd = view.findViewById(R.id.view_showAddShopEntry);
        showAdd.bringToFront();
        showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFields();
            }
        });

        View addForeground = view.findViewById(R.id.view_addPreviewForeground);
        addForeground.bringToFront();
        addForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFields();
            }
        });

        TextView plus = view.findViewById(R.id.textView_addButtonPlus);
        plus.bringToFront();
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFields();
            }
        });


        View deleteForeground = view.findViewById(R.id.view_deleteForeground);
        deleteForeground.bringToFront();
        deleteForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelected();
            }
        });

        TextView deleteText = view.findViewById(R.id.textView_deleteButtonX);
        deleteText.bringToFront();
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelected();
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    closeAdd();
                    return true;
                }
                return false;
            }
        });




        return view;

    }

}