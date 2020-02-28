package com.example.fragment.UserInterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment.AppData.Entities.Barcode;
import com.example.fragment.AppData.Entities.Item;
import com.example.fragment.AppData.Entities.ShoppingEntry;
import com.example.fragment.AppData.Logic.AppData;
import com.example.fragment.Entity.ShopListAdapter;
import com.example.fragment.Logic.ActivityValues;
import com.example.fragment.Logic.FragmentChanger;
import com.example.fragment.Logic.NonSwipeableViewPager;
import com.example.fragment.Logic.ScanCodeActivity;
import com.example.fragment.Logic.SectionStatePagerAdapter;
import com.example.fragment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
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
    private AppData appData;


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
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        mRecyclerView = findViewById(R.id.listView);


        Gson gson = new Gson();
        // 1. Java object to JSON file
        try {
            gson.toJson(appData, new FileWriter("appdata.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Java object to JSON string
        String jsonInString = gson.toJson(appData);



        //with AppData.getInstance now every class in this whole
        //project can use AppData and use all of its funtions.
        appData = AppData.getInstance();
        appData.loadData();
        appData.saveAppData();



        //region FragmentChanger
        Button btnNavFrag1 = findViewById(R.id.btnNavFrag1);
        Button btnNavFrag2 = findViewById(R.id.btnNavFrag2);
        Button btnNavFrag3 = findViewById(R.id.btnNavFrag3);
        TextView topBarName = findViewById(R.id.textView_topBarName);
        FloatingActionButton fab = findViewById(R.id.button_shopingEntryAdd);
        fab.setVisibility(View.INVISIBLE);

        fragmentChanger = new FragmentChanger(btnNavFrag1, btnNavFrag2, btnNavFrag3, topBarName, this, fab);
        mRecyclerView = findViewById(R.id.listView);
        //endregion


        //region setup the adapter
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mViewPager = new NonSwipeableViewPager(this);
        //endregion

        //region setup the pagers
        setUpPager(mViewPager);
        //endregion

        //region Set window fullscreen and remove title bar, and force landscape orientation
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
                Toast.makeText(MainActivity.this, "Going to Fragment 1", Toast.LENGTH_LONG).show();
                //navigate to method called
                ((MainActivity.this)).setViewPager(0);
            }
        });

        btnNavFrag2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Going to Fragment 2", Toast.LENGTH_LONG).show();
                //navigate to method called
                ((MainActivity.this)).setViewPager(1);
            }
        });

        btnNavFrag3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Going to Fragment 3", Toast.LENGTH_LONG).show();
                //navigate to method called
                ((MainActivity.this)).setViewPager(2);
            }
        });

        //endregion

        //region ShoppingEntries Button

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addToShopingList();
            }
        });


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

        getCorrectListView(fragmentNumber);
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
        ArrayList<ShoppingEntry> list = new ArrayList<>();

        //just for filling the list for now
        for (int i = 0; i < 50000; i++) {
            list.add(new ShoppingEntry("My Note : " + i));
        }

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        ShopListAdapter adapter = new ShopListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    private void setUpCookingListView(RecyclerView mListView) {
        // TODO: 05/02/2020  change the ShopingEntry to a CookingEntry, recipe or what ever

        ArrayList<ShoppingEntry> list = new ArrayList<>();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        ShopListAdapter adapter = new ShopListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    private void setUpItemListView(RecyclerView mListView) {
        // TODO: 05/02/2020  change the ShopingEntry to a Items or what ever

        ArrayList<ShoppingEntry> list = new ArrayList<>();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        ShopListAdapter adapter = new ShopListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    private void addToShopingList() {
        AlertDialog.Builder altert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.alert_addishopingentry, null);

        Button button = alertView.findViewById(R.id.button_addShopEntry);
        final EditText editText = alertView.findViewById(R.id.editText_shopEntry);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().equals("")) {

                }
            }
        });


        altert.setView(alertView);
        AlertDialog openAlert = altert.create();
        openAlert.show();
    }

    private void getCorrectAddPage(int currentPage) {
        Intent intent = null;
        switch (currentPage) {
            case 0:
                //additem
                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.this);
                alertItemView = getLayoutInflater().inflate(R.layout.alert_additem, null);

                final TextView barcodeShower = alertItemView.findViewById(R.id.textView_barcode);

                Button helpNext = alertItemView.findViewById(R.id.button_barCodeScan);
                helpNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View helpView) {
                        Intent i = new Intent(getApplicationContext(), ScanCodeActivity.class);
                        startActivityForResult(i, 0);
                        setBarcode();
                    }
                });

                Button addItem = alertItemView.findViewById(R.id.button_additem);
                final EditText itemName = alertItemView.findViewById(R.id.editText_itemName);
                final EditText itemAmount = alertItemView.findViewById(R.id.editText_itemAmount);


                final String name = itemName.getText().toString() + "";
                addItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Constructor
                        //public Item(String name, String description, LocalDate dateTime, int amount)

                        //check if the requierd fields are filled
                        if (!(itemAmount.getText().toString().equals("") && itemName.getText().toString().equals(""))) {


                            if (!(barcodeShower.getText().toString().equals("Lebensmittel"))) {
                                //User scanned a barcode now handle it
                                String barcode = ActivityValues.getInstance().getBarcode();
                                appData.addBarcode(new Barcode(barcode, new Item(itemAmount.getText().toString() + "", "NA", null, Integer.parseInt(itemAmount.getText().toString()))));
                            }
                            appData.addItem(new Item(itemAmount.getText().toString() + "", "NA", null, Integer.parseInt(itemAmount.getText().toString())));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Bitte fülle alle Felder mit rotem Stern aus", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                helpDialog.setView(alertItemView);
                AlertDialog help = helpDialog.create();
                help.show();

                //intent= new Intent(MainActivity.this,AddItem.class);


                break;

            case 1:
                AlertDialog.Builder altert1 = new AlertDialog.Builder(MainActivity.this);
                View alertView1 = getLayoutInflater().inflate(R.layout.addpage_cooking, null);


                altert1.setView(alertView1);
                AlertDialog openAlert1 = altert1.create();
                openAlert1.show();

                break;

            case 2:
                intent = new Intent(MainActivity.this, AddShop.class);
                startActivity(intent);
                break;
        }

        //startActivity(intent);
    }
}
