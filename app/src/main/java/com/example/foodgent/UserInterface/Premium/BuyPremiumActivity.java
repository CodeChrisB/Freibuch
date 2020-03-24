package com.example.foodgent.UserInterface.Premium;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;

import java.util.ArrayList;

public class BuyPremiumActivity extends AppCompatActivity {


    ArrayList<TextView> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_buy);

        TextView feature1 = findViewById(R.id.textView_feature1);
        TextView feature2 = findViewById(R.id.textView_feature2);
        TextView feature3 = findViewById(R.id.textView_feature3);
        TextView feature4 = findViewById(R.id.textView_feature4);
        TextView feature5 = findViewById(R.id.textView_feature5);

        list.add(feature1);
        list.add(feature2);
        list.add(feature3);
        list.add(feature4);
        list.add(feature5);

    }


}
