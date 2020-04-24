package com.foodgent.buchfrei.Logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
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
        askPremission();

        if (ActivityCompat.checkSelfPermission(ScanCodeActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            context = this;
        } else {
            //if the user dont want to use the camera just go back.
            finish();
        }

    }



    @Override
    public void handleResult(Result result) {

        String s = result.getText();
        ActivityValues.getInstance().setBarcode(s);
        Toast.makeText(context,"Barcodes is : " + s, Toast.LENGTH_LONG).show();
        ItemActivity.setAddItemAlertDialog();
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

    private void askPremission() {
        int camera = 1;
        if (ContextCompat.checkSelfPermission(ScanCodeActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, camera);
        }
    }
}
