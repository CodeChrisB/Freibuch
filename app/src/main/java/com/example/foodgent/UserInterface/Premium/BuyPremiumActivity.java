package com.example.foodgent.UserInterface.Premium;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.AppData.Logic.AppData;
import com.example.fragment.R;

import java.util.ArrayList;

public class BuyPremiumActivity extends AppCompatActivity {


    ArrayList<TextView> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_buy);

        Button buyPremium = findViewById(R.id.button_buyPremium);
        final TextView premiumInfo = findViewById(R.id.textView_premium_info);

        if (AppData.getInstance().isPremium())
            premiumInfo.setText("Deine Premium Features :");

        buyPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setPremium(!AppData.getInstance().isPremium());
                AppData.getInstance().saveSettings();

                String text = (AppData.getInstance().isPremium()) ? "Deine Premium Features :" : "Hol dir Premium";
                premiumInfo.setText(text);
            }
        });

    }


}
