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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
    private ListView mListView;
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

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        mListView = findViewById(R.id.listView);

        //with AppData.getInstance now every class in this whole
        //project can use AppData and use all of its funtions.
        appData = AppData.getInstance();

        //region FragmentChanger
        Button btnNavFrag1 = findViewById(R.id.btnNavFrag1);
        Button btnNavFrag2 = findViewById(R.id.btnNavFrag2);
        Button btnNavFrag3 = findViewById(R.id.btnNavFrag3);
        TextView topBarName = findViewById(R.id.textView_topBarName);
        FloatingActionButton fab = findViewById(R.id.button_shopingEntryAdd);
        fab.setVisibility(View.INVISIBLE);

        fragmentChanger = new FragmentChanger(btnNavFrag1, btnNavFrag2, btnNavFrag3, topBarName, this, fab);
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



    private void getCorrectAddPage(int currentPage) {
        Intent intent = null;
        switch (currentPage) {
            case 0:

                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.this);
                alertItemView = getLayoutInflater().inflate(R.layout.alert_additem, null);

                final TextView barcodeShower = alertItemView.findViewById(R.id.textView_barcode);

                Button helpNext = alertItemView.findViewById(R.id.button_barCodeScan);
                helpNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View helpView) {
                        Intent i = new Intent(getApplicationContext(), ScanCodeActivity.class);
                        startActivityForResult(i, 0);
                        String s = ActivityValues.getInstance().getBarcode();
                        setBarcode();
                    }
                });


                // d


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
                setUpItemListView(mListView);
                break;

            case 1:
                setUpCookingListView(mListView);
                break;

            case 2:
                setUpShopListView(mListView);
                break;
        }
    }

    private void setUpShopListView(ListView mListView) {
        ArrayList<ShoppingEntry> list = new ArrayList<>();

        //just for filling the list for now
        for (int i = 0; i < 50000; i++) {
            list.add(new ShoppingEntry("My Note : " + i));
        }


        ShopListAdapter adapter = new ShopListAdapter(this, R.layout.listview_shopping, list);
        mListView.setAdapter(adapter);
    }

    private void setUpCookingListView(ListView mListView) {
        // TODO: 05/02/2020  change the ShopingEntry to a CookingEntry, recipe or what ever

        ArrayList<ShoppingEntry> list = new ArrayList<>();

        ShopListAdapter adapter = new ShopListAdapter(this, R.layout.listview_shopping, list);
        mListView.setAdapter(adapter);
    }

    private void setUpItemListView(ListView mListView) {
        // TODO: 05/02/2020  change the ShopingEntry to a Items or what ever

        ArrayList<ShoppingEntry> list = new ArrayList<>();


        ShopListAdapter adapter = new ShopListAdapter(this, R.layout.listview_shopping, list);
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

    static public void setBarcode() {
        TextView t = alertItemView.findViewById(R.id.textView_barcode);
        t.setText(ActivityValues.getInstance().getBarcode());

    }
}
