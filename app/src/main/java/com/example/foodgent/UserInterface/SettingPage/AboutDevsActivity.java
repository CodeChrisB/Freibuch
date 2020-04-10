package com.example.foodgent.UserInterface.SettingPage;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
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


        String codeChrisPart = "•Project Leader\n•Plannung\n•Layouts\n•App Design\n•App Logic\n•Presistence";
        TextView codeChrisText = findViewById(R.id.textView_codeChrisPart);
        String text = codeChrisPart.replace("\n", "&lt;br&gt;");
        codeChrisText.setText(Html.fromHtml(Html.fromHtml(text).toString()));


        String robertPart = "•Internet\n•Barcode Scanner\nFeedback";
        TextView robertText = findViewById(R.id.textView_robertPart);
        text = robertPart.replace("\n", "&lt;br&gt;");
        robertText.setText(Html.fromHtml(Html.fromHtml(text).toString()));


        Button devBack = findViewById(R.id.button_devBack);
        devBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
