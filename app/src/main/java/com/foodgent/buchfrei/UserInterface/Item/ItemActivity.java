package com.foodgent.buchfrei.UserInterface.Item;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Entities.Barcode;
import com.foodgent.buchfrei.AppData.Entities.BarcodeItem;
import com.foodgent.buchfrei.AppData.Entities.Item;
import com.foodgent.buchfrei.AppData.Entities.RecipeItem;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Entity.ItemListAdapter;
import com.foodgent.buchfrei.Logic.ActivityValues;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.Logic.ScanCodeActivity;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.foodgent.buchfrei.UserInterface.Recipe.RecipeActivity;
import com.foodgent.buchfrei.UserInterface.Shopping.ShopActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ItemActivity extends Fragment {

    private static final String TAG = "Fragment1";


    private static RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView mRecyclerview;
    private static Context context;
    private boolean appStart = true;
    private static View alertItemView;
    private static View view;
    private static ItemActivity instance = null;

    public static void setNull() {

        if (context != null) {
            ArrayList<Item> list = new ArrayList<>();

            mRecyclerview.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(context);
            ItemListAdapter adapter = new ItemListAdapter(list);
            mRecyclerview.setLayoutManager(mLayoutManager);
            mRecyclerview.setAdapter(adapter);
        }
    }

    public static void setAddItemAlertDialog() {
        MainActivity main = MainActivity.getInstance();

        Button button = main.findViewById(R.id.button_add);

        button.performClick();
    }

    static public void setUpItemListView() {
        RecipeActivity.setNull();
        ShopActivity.setNull();

        mRecyclerview = view.findViewById(R.id.fragItem_listView);
        ArrayList<Item> list = AppData.getInstance().getItems();

        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        ItemListAdapter adapter = new ItemListAdapter(list);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(adapter);
    }



    public static AlertDialog getAddItemAlertDialog() {
        final AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.getInstance().getContext());
        alertItemView = MainActivity.getInstance().getLayoutInflater().inflate(R.layout.addpage_item, null);
        helpDialog.setView(alertItemView);
        final AlertDialog help = helpDialog.create();

        final TextView itemName = alertItemView.findViewById(R.id.editText_itemName);
        final TextView itemAmount = alertItemView.findViewById(R.id.editText_itemAmount);
        final TextView itemDesc = alertItemView.findViewById(R.id.editText_addItemDescription);
        final TextView barcode = alertItemView.findViewById(R.id.textView_barcode);


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

        final Calendar myCalendar = Calendar.getInstance();

        final EditText dateTimePick = alertItemView.findViewById(R.id.editText_datepick);

        dateTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();

                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.getInstance(), new DatePickerDialog.OnDateSetListener() {
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


                    Date date = null;

                    SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy");
                    try {
                        date = format.parse(dateTimePick.getText().toString());
                        System.out.println(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Item item = new Item(itemName.getText().toString(), itemDesc.getText().toString(), date, amount, "");
                    AppData app = AppData.getInstance();
                    app.addItem(item);
                    app.saveItems();
                    app.addRecipeItem(new RecipeItem(0, item.getName(), item.getUnit()));
                    app.saveRecipeItems();
                    app.loadData();
                    ItemActivity.setUpItemListView();

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

    public void notificationcall(String title, String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "1");
        builder.setSmallIcon(R.drawable.splashlogo_calm);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(1, builder.build());


    }



    public static ItemActivity getInstance() {
        if (instance == null)
            instance = new ItemActivity();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getActivity()));
        instance = this;
        ItemActivity.view = view;

        try {

        } catch (Exception e) {

        }
        notificationcall("Test Notification", "This is a Test");
        Log.d(TAG, "onCreateView: started");
        if (appStart) {
            context = getContext();
        } else {

            mRecyclerview = view.findViewById(R.id.fragItem_listView);
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


        TextView icon = view.findViewById(R.id.textView_itemDeleteIcon);
        View deleteBg = view.findViewById(R.id.view_ItemDeleteBg);
        View deleteFg = view.findViewById(R.id.view_itemDeleteFg);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelected();
            }
        });
        deleteBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelected();
            }
        });

        deleteFg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelected();
            }
        });
        appStart = false;
        return view;

    }

    private void removeSelected() {
        AppData.getInstance().removeSelectedItems();
        setUpItemListView();
        AppData.getInstance().saveItems();
    }


}


