package com.foodgent.buchfrei.UserInterface.SettingPage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import androidx.appcompat.app.AppCompatDelegate;

import com.example.fragment.R;
import com.foodgent.buchfrei.AppData.Logic.AppData;
import com.foodgent.buchfrei.Logic.AppCrashHandler;
import com.foodgent.buchfrei.Logic.FragmentChanger;
import com.foodgent.buchfrei.UserInterface.Item.ItemActivity;
import com.foodgent.buchfrei.UserInterface.MainActivity;
import com.foodgent.buchfrei.UserInterface.Premium.BarcodeManage;
import com.foodgent.buchfrei.UserInterface.Premium.BuyPremiumActivity;
import com.foodgent.buchfrei.UserInterface.Premium.SpecialSettingsActivity;
import com.foodgent.buchfrei.UserInterface.Recipe.RecipeActivity;
import com.foodgent.buchfrei.UserInterface.Shopping.ShopActivity;

import java.util.ArrayList;


public class SettingsActivityModern extends AppCompatActivity {

    public static Activity setting;
    private static SettingsActivityModern instance;
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

    public static SettingsActivityModern getInstance() {
        if (instance == null)
            instance = new SettingsActivityModern();

        return instance;
    }


    private void initAll() {
        darkmode = findViewById(R.id.setting_switchDarkMode);

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modern_settings);
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));

        SettingsActivityModern.setting = this;
        initAll();

            darkmode.setChecked(AppData.getInstance().isDarkMode());
        notification.setChecked(AppData.getInstance().isNotificationOn());
        textSize.setChecked(AppData.getInstance().isBigText());

        //region Set window fullscreen, remove title bar, force landscape orientation,prevent view get pushed by Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //endregion




        //region switches
        darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean mode = AppData.getInstance().isDarkMode();

                //if the mode change button is pressed for the first time he will give the last value back not the curent value


                //If dark go back else set darkmode
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }


                AppData.getInstance().setDarkMode(!mode);
                AppData.getInstance().saveSettings();
                startActivity(new Intent(getApplicationContext(), SettingsActivityModern.class));
                finish();
            }
        });


        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setBigText(textSize.isChecked());
                AppData.getInstance().saveSettings();

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppData.getInstance().setNotification(notification.isChecked());
                AppData.getInstance().saveSettings();

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
                Intent intent = new Intent(SettingsActivityModern.this, AboutDevsActivity.class);
                startActivity(intent);
            }
        });

        premiumBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivityModern.this, BuyPremiumActivity.class);
                startActivity(intent);
            }
        });

        premiumManageBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppData.getInstance().isPremium()) {
                    Intent intent = new Intent(SettingsActivityModern.this, BarcodeManage.class);
                    startActivity(intent);
                }
            }
        });

        premiumSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppData.getInstance().isPremium()) {
                    Intent intent = new Intent(SettingsActivityModern.this, SpecialSettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void setColorScheme() {

    }


}
