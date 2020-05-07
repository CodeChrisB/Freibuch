package com.foodgent.buchfrei.UserInterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.Logic.FragmentChanger;
import com.foodgent.buchfrei.Logic.NonSwipeableViewPager;
import com.foodgent.buchfrei.Logic.NotificationService;
import com.foodgent.buchfrei.Logic.SectionStatePagerAdapter;
import com.foodgent.buchfrei.UserInterface.Item.AddItemActivity;
import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
import com.foodgent.buchfrei.UserInterface.Recipe.AddCooking;
import com.foodgent.buchfrei.UserInterface.SettingPage.SettingsActivityModern;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {


    private static MainActivity instance;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private static ViewPager mViewPager;
    private static FragmentChanger fragmentChanger;
    private RecyclerView mRecyclerView;


    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    public static void setViewPager(int fragmentNumber) {
        fragmentChanger.change(fragmentNumber, mViewPager);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);
        instance = this;
        mRecyclerView = findViewById(R.id.listView);


        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        //with AppData.getInstance now every class in this whole
        //project can use AppData and use all of its funtions.#AppData.getInstance().loadData();


        AppData.getInstance().loadData();


        AppData.getInstance().saveAppData();


        NotificationService notificationService = new NotificationService(getContext());

        notificationService.notify("Foogent Title", "Hallo ich bin kuehli");
        notificationService.notify("Foogent", "Hallo ich bin kuehli.");




        //region FragmentChange
        Button btnNavFrag1 = findViewById(R.id.btnNavFrag1);
        Button btnNavFrag2 = findViewById(R.id.btnNavFrag2);
        Button btnNavFrag3 = findViewById(R.id.btnNavFrag3);


        //(Button btnNavFrag1, Button btnNavFrag2, Button btnNavFrag3, MainActivity activity) {
        fragmentChanger = new FragmentChanger(btnNavFrag1, btnNavFrag2, btnNavFrag3, instance, mViewPager);
        //mRecyclerView = findViewById(R.id.listView);
        //endregion



        //region setup the adapter
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mViewPager = new NonSwipeableViewPager(this);
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
                // Intent intent = new Intent(); // crashing the app to test the handler
                Intent intent = new Intent(MainActivity.this, SettingsActivityModern.class);
                startActivity(intent);
            }
        });
        //endregion


        //region Image addButton
        Button addButton;
        addButton = findViewById(R.id.button_add);
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
                setViewPager(0);
            }
        });

        btnNavFrag2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                setViewPager(1);
            }
        });

        btnNavFrag3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                setViewPager(2);
            }
        });


        //setup the standard list view
        ItemActivity.setUpItemListView();


        if (AppData.getInstance().isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


    }

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);

        super.onStart();

    }

    private void getCorrectAddPage(int currentPage) {
        Intent intent;

        switch (currentPage) {
            case 0:
                //additem
               /* AlertDialog item = ItemActivity.getAddItemAlertDialog();
                item.show();*/
                startActivity(new Intent(getContext(), AddItemActivity.class));

                break;


            case 1:
                intent = new Intent(this, AddCooking.class);
                startActivity(intent);
                break;

            case 2:
                String s = AppData.getInstance().getFormattedShoppingList();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
                startActivity(Intent.createChooser(sharingIntent, "Einkaufsliste teilen..."));
                break;
        }
    }

    public Context getContext() {
        return this;
    }




}
