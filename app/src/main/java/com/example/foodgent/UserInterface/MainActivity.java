package com.example.foodgent.UserInterface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Logic.FragmentChanger;
import com.example.foodgent.Logic.NonSwipeableViewPager;
import com.example.foodgent.Logic.NotificationService;
import com.example.foodgent.Logic.SectionStatePagerAdapter;
import com.example.foodgent.UserInterface.Item.AddItemActivity;
import com.example.foodgent.UserInterface.Item.ItemActivity;
import com.example.foodgent.UserInterface.Recipe.AddCooking;
import com.example.foodgent.UserInterface.SettingPage.SettingsActivityModern;
import com.example.fragment.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {

    public static boolean reload = false;
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

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
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

    public void notificationcall(String title, String content) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.splashlogo_calm)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.splashlogo_calm))
                .setContentTitle(title)
                .setContentText(content);


        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public static void setViewPager(int fragmentNumber) {
        fragmentChanger.change(fragmentNumber, mViewPager);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setTitle("FoodGent Premium");
        setContentView(R.layout.activity_main);
        instance = this;
        mRecyclerView = findViewById(R.id.listView);


        SettingsActivityModern.getInstance().setColorScheme();

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

}
