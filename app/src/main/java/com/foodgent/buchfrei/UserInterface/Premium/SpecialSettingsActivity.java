package com.foodgent.buchfrei.UserInterface.Premium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.Logic.AppCrashHandler;

public class SpecialSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_special_settings);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        View changeShopList = findViewById(R.id.settingP_shoppingListCoustom);

        changeShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangeShopListApperance.class));
            }
        });


    }
}
