package com.foodgent.buchfrei.UserInterface.Premium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.util.ArrayList;

public class BuyPremiumActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{


    ArrayList<TextView> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_buy);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        billingProcessor = new BillingProcessor(this,null,this);

        Button buyPremium = findViewById(R.id.button_buyPremium);
        final TextView premiumInfo = findViewById(R.id.textView_premium_info);

        if (AppData.getInstance().isPremium())
            premiumInfo.setText("Deine Premium Features :");

        buyPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                billingProcessor.purchase(BuyPremiumActivity.this,"fg_premium C");
                AppData.getInstance().setPremium(!AppData.getInstance().isPremium());
                AppData.getInstance().saveSettings();

                String text = (AppData.getInstance().isPremium()) ? "Deine Premium Features :" : "Hol dir Premium";
                premiumInfo.setText(text);
            }
        });
    }

    /*******************************************************************************************************************
     * Billing Section do not touch these methods!
     ********************************************************************************************************************/

    BillingProcessor billingProcessor;
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

        Toast.makeText(this, "Du hast FoodGent Premium gekauft!", Toast.LENGTH_SHORT).show();

    }  

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Irgendwas ist falsch gelaufen :(", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!billingProcessor.handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        if(billingProcessor!= null){
            billingProcessor.release();
        }
        super.onDestroy();
    }
}
