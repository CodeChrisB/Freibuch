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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Gesture.OnSwipeTouchListener;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.Logic.FragmentChanger;
import com.foodgent.buchfrei.Logic.SectionStatePagerAdapter;
import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
import com.foodgent.buchfrei.UserInterface.SettingPage.SettingsActivityModern;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {


    private static MainActivity instance;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private static ViewPager mViewPager;
    private static FragmentChanger fragmentChanger;
    private RecyclerView mRecyclerView;
    private MainAction mainAction;
    private OnSwipeTouchListener onSwipeTouchListener;


    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }


    public static ViewPager getmViewPager() {
        return mViewPager;
    }

    public MainAction getMainAction() {
        return mainAction;
    }

    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);
        instance = this;

        mRecyclerView = findViewById(R.id.listView);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));
        AppData.getInstance().loadData();

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
        mViewPager = new ViewPager(this);
        mViewPager.setAdapter(mSectionStatePagerAdapter);
        mViewPager.bringToFront();
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
                Intent intent = mainAction.getCorrectAddPage(fragmentChanger.getCurrentPage());
                mainAction.startAddIntent(intent);
            }
        });

        //endregion
        //region Navbar
        btnNavFrag1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                mainAction.setViewPager(0);
            }
        });

        btnNavFrag2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                mainAction.setViewPager(1);
            }
        });

        btnNavFrag3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to method called
                mainAction.setViewPager(2);
            }
        });


        //setup the standard list view
        ItemActivity.setUpItemListView();

        //if you want to get to a specific page on the main Activty you can
        //just set an action to the intent and set the ViewPager here
        String action = getIntent().getStringExtra("action");
        mainAction = new MainAction(getInstance(), fragmentChanger);
        mainAction.setFragment(action);

        MainAction.setGestureArea();


        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.gestureArea));

    }

    public Context getContext() {
        return this;
    }
}
