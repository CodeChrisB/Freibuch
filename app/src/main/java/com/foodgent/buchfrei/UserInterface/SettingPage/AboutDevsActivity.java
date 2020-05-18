package com.foodgent.buchfrei.UserInterface.SettingPage;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;

public class AboutDevsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_devs);


        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion

        String chrisPart = "•Project Leader\n•Team Managment\n•Design\n•Plannung\n•Design\n•Daten Speicherung\n";
        TextView codeChrisText = findViewById(R.id.textView_codeChrisPart);
        codeChrisText.setText(chrisPart);


        String robertPart = "•Barcode Scanner\n Standard Rezepte";
        TextView robertText = findViewById(R.id.textView_robertPart);
        robertText.setText(robertPart);


        Button devBack = findViewById(R.id.button_devBack);
        devBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
