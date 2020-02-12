package com.example.fragment.Logic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.R;
import com.example.fragment.UserInterface.MainActivity;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.ProductResultParser;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.oned.EAN8Reader;

import java.net.HttpURLConnection;
import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private Intent returnIntent  = new Intent();
    private String data;
    private ZXingScannerView scannerView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        context = this;

    }



    @Override
    public void handleResult(Result result) {

        String s = result.getText();
        int requestCode =1000;
        ActivityValues.getInstance().setBarcode(s);
        Toast.makeText(context,"Barcode is : " + s, Toast.LENGTH_LONG).show();
        finish();
    }

    @NonNull
    @Override
    public String toString() {
        return data;
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent returnIntent = new Intent();

        if(data != null){
            returnIntent.putExtra("result",data);
            setResult(Activity.RESULT_OK,returnIntent);
            onBackPressed();
        } else{
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
