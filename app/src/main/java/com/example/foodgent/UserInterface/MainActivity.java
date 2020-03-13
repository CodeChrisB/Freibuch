package com.example.foodgent.UserInterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.BarcodeItem;
import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Entities.Recipe;
import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.ItemListAdapter;
import com.example.foodgent.Entity.RecipeListAdapter;
import com.example.foodgent.Entity.ShopListAdapter;
import com.example.foodgent.Logic.ActivityValues;
import com.example.foodgent.Logic.FragmentChanger;
import com.example.foodgent.Logic.NonSwipeableViewPager;
import com.example.foodgent.Logic.ScanCodeActivity;
import com.example.foodgent.Logic.SectionStatePagerAdapter;
import com.example.fragment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private int LAUNCH_SECOND_ACTIVITY = 1;
    private String barcodeData;
    private static final String TAG = "MainActivity";
    private static View alertItemView;
    private static MainActivity instance;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;
    private FragmentChanger fragmentChanger;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;



    private static ActivityValues values;

    public static MainActivity getInstance() {
        return instance;
    }

    public Context getContext() {
        return this;
    }

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }


    static public void setBarcode() {
        TextView t = alertItemView.findViewById(R.id.textView_barcode);
        t.setText(("Barcode " + ActivityValues.getInstance().getBarcode()));

        String item = ActivityValues.getInstance().getBarcode();
        BarcodeItem bi = AppData.getInstance().searchForItem(item);

        if (bi != null) {
            EditText name = alertItemView.findViewById(R.id.editText_itemName);
            name.setText(bi.getName());
            EditText desc = alertItemView.findViewById(R.id.editText_addItemDescription);
            desc.setText(bi.getDescription());
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        instance = this;
        mRecyclerView = findViewById(R.id.listView);


        //with AppData.getInstance now every class in this whole
        //project can use AppData and use all of its funtions.#AppData.getInstance().loadData();
        AppData.getInstance().loadData();
        AppData.getInstance().saveAppData();


        //region FragmentChange
        Button btnNavFrag1 = findViewById(R.id.btnNavFrag1);
        Button btnNavFrag2 = findViewById(R.id.btnNavFrag2);
        Button btnNavFrag3 = findViewById(R.id.btnNavFrag3);
        TextView topBarName = findViewById(R.id.textView_topBarName);
        FloatingActionButton fab = findViewById(R.id.button_shopingEntryAdd);
        fab.setVisibility(View.INVISIBLE);


        //(Button btnNavFrag1, Button btnNavFrag2, Button btnNavFrag3, MainActivity activity) {
        fragmentChanger = new FragmentChanger(btnNavFrag1, btnNavFrag2, btnNavFrag3, instance);
        //mRecyclerView = findViewById(R.id.listView);
        //endregion

        //setup the standard list view
        Fragment1.setUpItemListView();


        //region setup the adapter
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mViewPager = new NonSwipeableViewPager(this);
        //endregion

        //region setup the pagers
        setUpPager(mViewPager);
        //endregion

        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion

        //region Nav Button OnClick
        Button button = findViewById(R.id.button_navSecond);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        //endregion

        //region Addbutton choose which activity, open it, get data , save it

        //endregion

        //region Image addButton
        Button addButton;
        addButton = findViewById(R.id.button_add);
        addButton.setHeight(addButton.getHeight() / 2);

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCorrectAddPage(fragmentChanger.getCurrentPage());
            }
        });

        //endregion
        //region Navbar
        btnNavFrag1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                ((MainActivity.this)).setViewPager(0);
            }
        });

        btnNavFrag2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                ((MainActivity.this)).setViewPager(1);
            }
        });

        btnNavFrag3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                ((MainActivity.this)).setViewPager(2);
            }
        });

        //endregion

        //region ShoppingEntries Button

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText_shoppingAdd);
                if (et.getText().length() > 0) {
                    ShoppingEntry se = new ShoppingEntry(et.getText().toString());

                    if (AppData.getInstance().addShoppingEntry(se)) {
                        //can the item be added? or are there to many items?
                        AppData.getInstance().saveAppData();
                        setUpShopListView(mRecyclerView);

                    } else {
                        //not enough space
                        Toast.makeText(getContext(), "Ohh nein die Liste ist voll, hole dir FoodGent Premium um unbegrenzt Einträge zu machen", Toast.LENGTH_LONG).show();
                    }
                    et.setText("");
                }

                //closes the Keyboard after usage
                et.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        FloatingActionButton deleteShopEntries = findViewById(R.id.button_shopingEntryDelete);
        deleteShopEntries.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.getInstance().removeAllShoppingEntries();
                AppData.getInstance().saveAppData();
                setUpShopListView(mRecyclerView);
            }
        });


        //endregion

        //region updateListView
        getCorrectListView(fragmentChanger.getCurrentPage());
        //endregion

    }

    private void setUpPager(ViewPager viewPager) {
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "1");
        adapter.addFragment(new Fragment2(), "2");
        adapter.addFragment(new Fragment3(), "3");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        fragmentChanger.change(fragmentNumber, mViewPager);
    }

    private void getCorrectListView(int currentFragmentNumber) {


        switch (currentFragmentNumber) {
            case 0:
                setUpItemListView(mRecyclerView);
                break;

            case 1:
                setUpCookingListView(mRecyclerView);
                break;

            case 2:
                setUpShopListView(mRecyclerView);
                break;
        }
    }


    private void setUpShopListView(RecyclerView mListView) {

        ArrayList<ShoppingEntry> list = AppData.getInstance().getShoppingEntries();

        mLayoutManager = new LinearLayoutManager(getContext());
        mListView.setHasFixedSize(true);
        ShopListAdapter adapter = new ShopListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);

    }

    private void setUpCookingListView(RecyclerView mListView) {
        ArrayList<Recipe> list = AppData.getInstance().getRecipes();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        RecipeListAdapter adapter = new RecipeListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    private void setUpItemListView(RecyclerView mListView) {

        ArrayList<Item> list = AppData.getInstance().getItems();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        ItemListAdapter adapter = new ItemListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    private void getCorrectAddPage(int currentPage) {
        Intent intent;

        switch (currentPage) {
            case 0:
                //additem
                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.this);
                alertItemView = getLayoutInflater().inflate(R.layout.alert_additem, null);
                helpDialog.setView(alertItemView);
                final AlertDialog help = helpDialog.create();

                final TextView itemName = alertItemView.findViewById(R.id.editText_itemName);
                final TextView itemAmount = alertItemView.findViewById(R.id.editText_itemAmount);
                final TextView itemDesc = alertItemView.findViewById(R.id.editText_addItemDescription);
                final TextView barcode = alertItemView.findViewById(R.id.textView_barcode);



                Button barCodeScannerButton = alertItemView.findViewById(R.id.button_barCodeScan);
                barCodeScannerButton.bringToFront();
                barCodeScannerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View helpView) {

                        //Paywall Barcodescanner is only for paid Useres
                        if (!AppData.getInstance().isPremium()) {
                            Toast.makeText(getContext(), "Du hast gerade ein Premium feature gefunden,gehe in die Einstellungen um Foodgent Premium zu kaufen", Toast.LENGTH_LONG).show();
                        } else {
                            Intent i = new Intent(getApplicationContext(), ScanCodeActivity.class);
                            startActivityForResult(i, 0);
                        }
                    }
                });


                Button addItem = alertItemView.findViewById(R.id.button_additem);
                final TextView barcodeShower = alertItemView.findViewById(R.id.textView_barcode);

                addItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Barcode
                        if (!(barcodeShower.getText().toString().equals("Lebensmittel"))) {
                            //check if it is a new barcode
                            BarcodeItem insertItem = AppData.getInstance().searchForItem(ActivityValues.getInstance().getBarcode());
                            if (insertItem == null) {
                                //NEW BARCODE
                                String barcode = ActivityValues.getInstance().getBarcode();
                                BarcodeItem barcodeItem = new BarcodeItem(itemName.getText().toString(), itemDesc.getText().toString());
                                AppData.getInstance().addBarcode(new Barcode(barcode, barcodeItem));
                                AppData.getInstance().saveBarcode();
                            } else {
                                //Barcode is already known
                                itemName.setText(insertItem.getName());
                                itemDesc.setText(insertItem.getDescription());
                            }
                        }


                        if (!(itemName.getText().toString().equals(""))) {
                            //if the item has a name in the textfield
                            int amount = 1;

                            if (!(itemAmount.getText().toString().equals("")))
                                amount = Integer.parseInt((itemAmount.getText().toString()));
                            Item item = new Item(itemName.getText().toString(), itemDesc.getText().toString(), null, amount);
                            AppData.getInstance().addItem(item);
                            AppData.getInstance().saveItems();
                            Fragment1.setUpItemListView();

                            //remove barcode from Activity values before dialog close
                            ActivityValues.getInstance().setBarcode("");
                            help.dismiss();


                        } else {
                            //no name was given for the item
                            Toast.makeText(getContext(), "Error- Item Name", Toast.LENGTH_LONG).show();
                        }
                    }
                });


                help.show();
                //intent= new Intent(MainActivity.this,AddItem.class);
                break;


            case 1:
                intent = new Intent(this, AddCooking.class);
                startActivity(intent);
                break;

            case 2:
                //no Dialog just show the Add TextView on the down right hand corner
                //we are going to have a share button here
                if (AppData.getInstance().getShoppingEntries().size() > 0) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, AppData.getInstance().getFormattedShoppingList());
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);
                }
                break;
        }


        //startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    private void setAddPageDifference(int page) {
        Guideline listViewEnd = findViewById(R.id.guideline_listViewBottom);

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) listViewEnd.getLayoutParams();



        EditText shoppingAdd = findViewById(R.id.editText_shoppingAdd);
        FloatingActionButton fab = findViewById(R.id.button_shopingEntryDelete);
        Button addButton = findViewById(R.id.button_add);

        if (page == 2) {
            fab.setVisibility(View.VISIBLE);
            shoppingAdd.setVisibility(View.VISIBLE);

            addButton.setBackgroundResource(android.R.drawable.ic_menu_share);
            addButton.setHeight((addButton.getHeight()) / 2);
            addButton.setWidth(addButton.getHeight());
            lp.guidePercent = 0.82f;

        } else {
            addButton.setBackgroundResource(android.R.drawable.ic_input_add);
            shoppingAdd.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.INVISIBLE);
            lp.guidePercent = 1f;
        }

        listViewEnd.setLayoutParams(lp);
    }

    public void updateLists() {
        setUpShopListView(mRecyclerView);
        setUpCookingListView(mRecyclerView);
        setUpItemListView(mRecyclerView);
    }


}
