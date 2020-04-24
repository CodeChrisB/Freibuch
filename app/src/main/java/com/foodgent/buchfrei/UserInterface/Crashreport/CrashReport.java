package com.foodgent.buchfrei.UserInterface.Crashreport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.Logic.FirebaseManager;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;


public class CrashReport extends AppCompatActivity {
    private FirebaseManager fm = new FirebaseManager();
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_report);
        AndroidThreeTen.init(this);
        LocalDate locl = LocalDate.now();
        intent = getIntent();


        //there shouldnt be any crash potential but we cant afford a crash here
        try {
            TextView crashInfo = findViewById(R.id.cr_name);
            final String info = getString(R.string.crashName) + "\nDatum ." + locl.toString() + "\nAndroid Version : " + Build.VERSION.RELEASE;
            crashInfo.setText(info);

            Button send = findViewById(R.id.cr_send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 17/04/2020 Firebase send Error Report

                    //get intent strings
                    String message, cause, stacktrace;
                    message = intent.getStringExtra("message");
                    cause = intent.getStringExtra("cause");
                    stacktrace = intent.getStringExtra("stacktrace");
                    String codeLine = intent.getStringExtra("codeline");

                    //send errror per firebasemanager
                    fm.sendErrorReport(message, cause, stacktrace, codeLine);
                    toMain();
                }
            });

            Button dontSend = findViewById(R.id.cr_dontSend);
            dontSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toMain();
                }
            });


        } catch (Exception e) {
            //just go to the Main if something crashes inside here
            toMain();
        }
    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
