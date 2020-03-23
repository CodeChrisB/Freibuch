package com.example.foodgent.UserInterface;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.Logic.FragmentChanger;
import com.example.foodgent.Logic.TextViewHandler;
import com.example.foodgent.UserInterface.Fragment.ItemActivity;
import com.example.foodgent.UserInterface.Fragment.RecipeActivity;
import com.example.foodgent.UserInterface.Fragment.ShopActivity;
import com.example.fragment.R;

import java.util.ArrayList;


public class SettingsActivityModern extends AppCompatActivity {

    public static Activity setting;
    Switch darkmode;
    Switch textSize;
    Switch notification;
    View deleteAll;
    View language;
    View feedback;
    View help;
    View developer;
    View premiumBuy;
    View premiumManageBarcode;
    View premiumSpecial;

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modern_settings);

        SettingsActivityModern.setting = this;
        activateSettings();


        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion

        initAll();


        //region switches
        darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setDarkMode(darkmode.isChecked());
                AppData.getInstance().saveSettings();
                activateSettings();
                //Main
                MainActivity.activateSettings();
                //Item
                ItemActivity.setUpItemListView();
                ItemActivity.activateSettings();

                //Shop
                ShopActivity.activateSettings();
                ShopActivity.setUpShoppingList();


            }


        });

        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setBigText(textSize.isChecked());
                AppData.getInstance().saveSettings();
                activateSettings();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setNotification(notification.isChecked());
                AppData.getInstance().saveSettings();
                activateSettings();
            }
        });

        //endregion

        //region deleteAll
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(SettingsActivityModern.this);
                final View deleteView = getLayoutInflater().inflate(R.layout.dialog_delete_allll, null);

                helpDialog.setView(deleteView);
                final AlertDialog help = helpDialog.create();


                Resources res = getResources();
                final String[] helpText = res.getStringArray(R.array.deleteText);
                final ArrayList<String> list = new ArrayList<>();
                for (String s : helpText) {
                    list.add(s);
                }

                final TextView helpTextView = deleteView.findViewById(R.id.textView_helpText);
                helpTextView.setText(list.get(0));

                final Button helpNext = deleteView.findViewById(R.id.button_deleteNext);


                helpNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.size() > 1) {
                            list.remove(0);
                            helpTextView.setText(list.get(0));

                            if (list.size() == 1) {
                                helpNext.setText("Ja");
                                helpNext.setBackgroundColor(getResources().getColor(R.color.colorRed));
                            }
                        } else {
                            AppData.getInstance().DeleteAppData();
                            AppData.getInstance().saveAppData();
                            ItemActivity.setNull();
                            RecipeActivity.setNull();
                            ShopActivity.setNull();
                            help.cancel();
                        }
                    }
                });


                final Button buttonStop = deleteView.findViewById(R.id.button_deleteStop);
                buttonStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        help.cancel();
                    }
                });

                help.show();
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //endregion

        //region feedback

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder helpDialog = new AlertDialog.Builder(SettingsActivityModern.this);
                final View helpView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);

                helpDialog.setView(helpView);
                final AlertDialog help = helpDialog.create();


                Button sendFeedback = helpView.findViewById(R.id.button_sendFeedback);

                sendFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here we have to send the username with the feedback to firebase

                        TextView eUserFeedback = helpView.findViewById(R.id.editText_itemName);
                        TextView eUsername = helpView.findViewById(R.id.textView_feedbackName);
                        String username = eUsername.getText().toString();
                        String feedback = eUserFeedback.getText().toString();

                        if (username.length() > 0 && feedback.length() > 10) {
                            //a feedback with less than 10 characters is useless.
                            // TODO: 07/02/2020 FIREBASE STUFF


                            help.cancel();
                        } else {
                            Toast.makeText(MainActivity.getInstance().getContext(), "Bitte den Feedbackbogen ausfüllen!", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                help.show();
            }
        });

        //endregion


        //region help
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(SettingsActivityModern.this);
                View helpView = getLayoutInflater().inflate(R.layout.dialog_help, null);


                helpDialog.setView(helpView);
                final AlertDialog help = helpDialog.create();

                Resources res = getResources();
                final String[] helpText = res.getStringArray(R.array.planets_array);
                final ArrayList<String> list = new ArrayList<>();
                for (String s : helpText) {
                    list.add(s);
                }

                final TextView helpTextView = helpView.findViewById(R.id.textView_helpText);
                helpTextView.setText(list.get(0));

                final Button helpNext = helpView.findViewById(R.id.button_deleteNext);


                helpNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.size() > 1) {
                            list.remove(0);
                            helpTextView.setText(list.get(0));
                        } else {
                            help.cancel();
                        }
                    }
                });


                help.show();

            }
        });

        //endregion

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        premiumBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        premiumManageBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        premiumSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initAll() {
        darkmode = findViewById(R.id.setting_switchDarkMode);
        darkmode.setChecked(AppData.getInstance().isDarkMode());

        textSize = findViewById(R.id.setting_switch_changeTextSize);
        textSize.setChecked(AppData.getInstance().isBigText());

        notification = findViewById(R.id.setting_notification);
        notification.setChecked(AppData.getInstance().isNotificationOn());

        deleteAll = findViewById(R.id.setting_viewDeleteData);
        language = findViewById(R.id.setting_view_language);
        feedback = findViewById(R.id.setting_view_feedback);
        help = findViewById(R.id.setting_view_help);
        developer = findViewById(R.id.setting_viewDeveloper);
        premiumBuy = findViewById(R.id.setting_view_premium);
        premiumManageBarcode = findViewById(R.id.setting_view_premium_barcodes);
        premiumSpecial = findViewById(R.id.setting_view_premium_special);
    }


    private void activateSettings() {
        ConstraintLayout layout = findViewById(R.id.setting_layout);
        layout = findViewById(R.id.setting_layout);
        Toolbar toolbar = findViewById(R.id.setting_roundtopBarPart);
        View toolbarBottom = findViewById(R.id.setting_roundtopBarPart2);
        View bigBg = findViewById(R.id.setting_bigBackground);
        View smallBg = findViewById(R.id.setting_smallBackground);

        if (AppData.getInstance().isDarkMode()) {
            bigBg.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.darkSettingBackground));
            smallBg.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.darkSettingBackground));
            layout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));
            toolbar.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.darkThemePrimary));
            toolbarBottom.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.darkThemePrimary));
            TextViewHandler.getInstance().setSettingsDarkmode(true);


        } else {
            bigBg.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightSettingBackground));
            smallBg.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightSettingBackground));
            layout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorBackground));
            toolbar.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightThemePrimary));
            toolbarBottom.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightThemePrimary));
            int code = Color.BLACK;
            TextViewHandler.getInstance().setSettingsDarkmode(false);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            int currentPage = FragmentChanger.fragmentChanger.getCurrentPage();

            switch (currentPage) {
                case 0:
                    ItemActivity.setNull();
                    ItemActivity.setUpItemListView();
                    break;
                case 1:
                    RecipeActivity.setNull();
                    RecipeActivity.setUpRecipeList();
                    break;
                case 2:
                    ShopActivity.setNull();
                    ShopActivity.setUpShoppingList();
                    break;

            }


            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
