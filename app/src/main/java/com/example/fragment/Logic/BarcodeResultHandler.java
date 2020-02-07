package com.example.fragment.Logic;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeResultHandler implements ZXingScannerView.ResultHandler  {

    private String data;

    public void handleResult(Result result) {
        data = result.getText();
    }

    public String getData() {
        return data;
    }
}
