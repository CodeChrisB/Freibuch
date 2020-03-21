package com.example.foodgent.UserInterface;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.firebase.client.Firebase;

public class SettingsActivityModern extends AppCompatActivity {
    private static final String TAG = "Second";
    String[] settings = new String[3];
    private Firebase Ref;
    private Switch dark;
    private Switch light;
    private Switch changeTextSize;
    private Context context;

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modern_settings);


        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion


    }


}
