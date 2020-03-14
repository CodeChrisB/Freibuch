package com.example.foodgent.UserInterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fragment.R;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


        ConstraintLayout background = findViewById(R.id.constraintLayout_splashbackground);

        //setRandomColorBackground(background);
        ImageView logo = findViewById(R.id.imageView_splashlLogo);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.bounce);
        logo.startAnimation(rotate);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);


                finish();
            }
            //set the Splashscreen length in milliseconds
        }, 750);
    }

    private Color setRandomColorBackground(ConstraintLayout background) {

        int r = randomRGBValue();
        int g = randomRGBValue();
        int b = randomRGBValue();
        background.setBackgroundColor(Color.rgb(r, g, b));
        return null;
    }

    private int randomRGBValue() {
        return (int) (Math.random() * ((255 - 0) + 1));
    }
}
