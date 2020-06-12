package com.foodgent.buchfrei.UserInterface.SettingPage;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Gesture;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.UserInterface.MainAction;

public class GestureActivity extends AppCompatActivity {

    Switch up;
    Switch down;
    Switch side;
    private AppData app = AppData.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_gesture);

        up = findViewById(R.id.setting_swipeU);
        down = findViewById(R.id.setting_swipeBack);
        side = findViewById(R.id.setting_swipeLR);
        Gesture gesture = app.getGesture();

        //set the correct states of the switches.
        up.setChecked(gesture.isUp());
        down.setChecked(gesture.isDown());
        side.setChecked(gesture.isSide());

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gesture g = app.getGesture();
                g.setUp(up.isChecked());
                app.setGesture(g);
                app.saveAppData();
                MainAction.setGestureArea();
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gesture g = app.getGesture();
                g.setDown(down.isChecked());
                app.setGesture(g);
                app.saveAppData();
                MainAction.setGestureArea();
            }
        });

        side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gesture g = app.getGesture();
                g.setSide(side.isChecked());
                app.setGesture(g);
                app.saveAppData();
                MainAction.setGestureArea();
            }
        });

    }
}
