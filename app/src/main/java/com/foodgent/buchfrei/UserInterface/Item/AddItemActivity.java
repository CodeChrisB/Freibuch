package com.foodgent.buchfrei.UserInterface.Item;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Barcode;
import com.foodgent.buchfrei.AppData.Entities.BarcodeItem;
import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Entities.RecipeItem;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.ActivityValues;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.Logic.ScanCodeActivity;
import com.foodgent.buchfrei.UserInterface.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddItemActivity extends AppCompatActivity {

    private AddItemActivity addItemActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage_item);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        addItemActivity = this;


        final TextView itemName = findViewById(R.id.editText_itemName);
        final TextView itemAmount = findViewById(R.id.editText_itemAmount);
        final TextView itemDesc = findViewById(R.id.editText_addItemDescription);
        final TextView barcode = findViewById(R.id.textView_barcode);


        final Spinner unit = findViewById(R.id.spinner_itemUnit);


        //set barcode if one was scanned.
        if (ActivityValues.getInstance().getBarcode().length() > 0) {

            String sb = ActivityValues.getInstance().getBarcode();
            barcode.setText(sb);
            BarcodeItem bi = AppData.getInstance().searchForItem(sb);
            if (bi != null) {
                barcode.setText(ActivityValues.getInstance().getBarcode());
                itemName.setText(bi.getName());
                itemDesc.setText(bi.getDescription());
            } else {
                //new Barcode
            }
        }


        final EditText dateTimePick = findViewById(R.id.editText_datepick);
        dateTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();

                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(addItemActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + ":" + month + ":" + year;
                        dateTimePick.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        Button barCodeScannerButton = findViewById(R.id.button_barCodeScan);
        barCodeScannerButton.bringToFront();
        barCodeScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View helpView) {

                //Paywall Barcodescanner is only for paid Useres
                if (!AppData.getInstance().isPremium()) {
                    Toast.makeText(MainActivity.getInstance().getContext(), "Du hast gerade ein Premium feature gefunden,gehe in die Einstellungen um Foodgent Premium zu kaufen", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(MainActivity.getInstance().getApplicationContext(), ScanCodeActivity.class);
                    MainActivity.getInstance().startActivityForResult(i, 0);
                }
            }
        });


        Button addItem = findViewById(R.id.button_additem);
        final TextView barcodeShower = findViewById(R.id.textView_barcode);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Barcode
                if (!(barcodeShower.getText().toString().equals("Lebensmittel"))) {
                    //check if it is a new barcode
                    BarcodeItem insertItem = AppData.getInstance().searchForItem(ActivityValues.getInstance().getBarcode());
                    if (insertItem == null) {
                        //NEW BARCODE
                        String barcode = ActivityValues.getInstance().getBarcode();
                        BarcodeItem barcodeItem = new BarcodeItem(itemName.getText().toString(), itemDesc.getText().toString());
                        AppData.getInstance().addBarcode(new Barcode(barcode, barcodeItem));
                        AppData.getInstance().saveBarcode();
                    } else {
                        //Barcode is already known
                        itemName.setText(insertItem.getName());
                        itemDesc.setText(insertItem.getDescription());
                    }
                }


                if (!(itemName.getText().toString().equals(""))) {
                    //if the item has a name in the textfield
                    int amount = 1;

                    if (!(itemAmount.getText().toString().equals("")))
                        amount = Integer.parseInt((itemAmount.getText().toString()));


                    Date date = null;

                    SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
                    try {
                        date = format.parse(dateTimePick.getText().toString());
                        System.out.println(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Item item = new Item(itemName.getText().toString(), itemDesc.getText().toString(), date, amount, unit.getSelectedItem().toString());
                    AppData app = AppData.getInstance();
                    app.addItem(item);
                    app.saveItems();
                    app.addRecipeItem(new RecipeItem(0, item.getName(), item.getUnit()));
                    app.saveRecipeItems();
                    app.loadData();
                    ItemActivity.setUpItemListView();

                    //remove barcode from Activity values before dialog close
                    ActivityValues.getInstance().setBarcode("");
                    Intent intent = new Intent(MainActivity.getInstance(), MainActivity.class);
                    MainActivity.getInstance().startActivity(intent);

                } else {
                    //no name was given for the item
                    Toast.makeText(MainActivity.getInstance().getContext(), "Gebe bitte einen Namen ein.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}


