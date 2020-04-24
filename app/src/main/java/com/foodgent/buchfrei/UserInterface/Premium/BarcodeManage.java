package com.foodgent.buchfrei.UserInterface.Premium;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.Logic.AppCrashHandler;

public class BarcodeManage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_barcode);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));
    }
}
