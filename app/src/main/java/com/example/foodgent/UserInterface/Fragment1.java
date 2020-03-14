package com.example.foodgent.UserInterface;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgent.AppData.Entities.Barcode;
import com.example.foodgent.AppData.Entities.BarcodeItem;
import com.example.foodgent.AppData.Entities.Item;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Entity.ItemListAdapter;
import com.example.foodgent.Logic.ActivityValues;
import com.example.foodgent.Logic.ScanCodeActivity;
import com.example.fragment.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    private static final String TAG = "Fragment1";


    private static RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mListView;
    private static Context context;
    private boolean appStart = true;

    static public void setUpItemListView() {
        Fragment2.setNull();
        Fragment3.setNull();

        mListView = MainActivity.getInstance().findViewById(R.id.listView_items);
        ArrayList<Item> list = AppData.getInstance().getItems();

        mListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        ItemListAdapter adapter = new ItemListAdapter(list);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    static void setNull() {
        if (context != null) {
            ArrayList<Item> list = new ArrayList<>();

            mListView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            ItemListAdapter adapter = new ItemListAdapter(list);
            mListView.setLayoutManager(mLayoutManager);
            mListView.setAdapter(adapter);
        }
    }

    public static void setAddItemAlertDialog() {
        MainActivity main = MainActivity.getInstance();

        Button button = main.findViewById(R.id.button_add);

        button.performClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);


            Log.d(TAG, "onCreateView: started");
        if (appStart) {
            context = getContext();
        } else {
            mListView = view.findViewById(R.id.listView_items);
            setUpItemListView();
        }


        //region Prevent Fragment Close
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        //endregion The callback can be enabled or disabled here or in handleOnBackPressed()


        appStart = false;
            return view;

    }


    public static AlertDialog getAddItemAlertDialog() {

        final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.getInstance().getContext());
        View alertItemView = MainActivity.getInstance().getLayoutInflater().inflate(R.layout.alert_additem, null);
        helpDialog.setView(alertItemView);
        final AlertDialog help = helpDialog.create();

        final TextView itemName = alertItemView.findViewById(R.id.editText_itemName);
        final TextView itemAmount = alertItemView.findViewById(R.id.editText_itemAmount);
        final TextView itemDesc = alertItemView.findViewById(R.id.editText_addItemDescription);
        final TextView barcode = alertItemView.findViewById(R.id.textView_barcode);

        if (ActivityValues.getInstance().getBarcode().length() > 0) {

            String sb = ActivityValues.getInstance().getBarcode();
            BarcodeItem bi = AppData.getInstance().searchForItem(sb);

            barcode.setText(ActivityValues.getInstance().getBarcode());

            itemName.setText(bi.getName());

            itemDesc.setText(bi.getDescription());
        }

        Button barCodeScannerButton = alertItemView.findViewById(R.id.button_barCodeScan);
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


        Button addItem = alertItemView.findViewById(R.id.button_additem);
        final TextView barcodeShower = alertItemView.findViewById(R.id.textView_barcode);

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
                    Item item = new Item(itemName.getText().toString(), itemDesc.getText().toString(), null, amount);
                    AppData.getInstance().addItem(item);
                    AppData.getInstance().saveItems();
                    Fragment1.setUpItemListView();

                    //remove barcode from Activity values before dialog close
                    ActivityValues.getInstance().setBarcode("");
                    help.dismiss();
                    Intent intent = new Intent(MainActivity.getInstance(), MainActivity.class);
                    MainActivity.getInstance().startActivity(intent);

                } else {
                    //no name was given for the item
                    Toast.makeText(MainActivity.getInstance().getContext(), "Gebe bitte einen Namen ein.", Toast.LENGTH_LONG).show();
                }
            }
        });


        return help;
        //intent= new Intent(MainActivity.this,AddItem.class);
    }

}
