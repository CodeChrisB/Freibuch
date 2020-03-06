package com.example.foodgent.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.UserInterface.MainActivity;
import com.google.zxing.Result;

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
        ActivityValues.getInstance().setBarcode(s);
        Toast.makeText(context,"Barcodes is : " + s, Toast.LENGTH_LONG).show();
        MainActivity.setBarcode();
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
