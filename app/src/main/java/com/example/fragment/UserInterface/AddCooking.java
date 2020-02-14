package com.example.fragment.UserInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;

public class AddCooking extends AppCompatActivity {

    private static final String TAG = "Add Items";


    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0,0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage_cooking);
        Log.d(TAG,"OnCreate: Started");

        Button navButton = findViewById(R.id.button_navSecond);
        navButton.bringToFront();
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCooking.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
