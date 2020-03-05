package com.example.foodgent.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.AppData.Logic.AppData;
import com.example.foodgent.R;
import com.firebase.client.Firebase;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private   Firebase Ref;
    private static final String TAG = "Second";
    String[] settings = new String[3];
    private Switch dark;
    private Switch light;
    private Switch changeTextSize;
    private Context context;

    //removes the slide animation, when opening this activity
    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(TAG, "OnCreate: Started");
        context = this;

        updateVersionInfo();

        final TextView textView = findViewById(R.id.textView_titleSettings);
        textView.bringToFront();

        //region Hide Notificationbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //endregion

        //region Help Button

        final Button helpButton = findViewById(R.id.button_help);
        helpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(SecondActivity.this);
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

                Button helpNext = helpView.findViewById(R.id.button_deleteNext);


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


        //region Feedback

        final Button feedbackButton = findViewById(R.id.button_sendFeedback);

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder helpDialog = new AlertDialog.Builder(SecondActivity.this);
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

                     if(username.length()>0&&feedback.length()>10){
                         //a feedback with less than 10 characters is useless.
                         // TODO: 07/02/2020 FIREBASE STUFF


                         help.cancel();
                     }else{
                         Toast.makeText(context,"Bitte den Feedbackbogen ausfüllen!",Toast.LENGTH_LONG).show();
                     }

                    }
                });


                help.show();
            }

        });
        //endregion


        //region initial switches
        dark = findViewById(R.id.switch_dark);      // #Index 0 : Value 0
        light = findViewById(R.id.switch_light);    // #Index 0 : Value 1
        changeTextSize = findViewById(R.id.switch_text);       // #Index 1 : Value 0 = small  Value 1 = big
        //endregion


        //region initial setttings


        //if one field does not get a value use the standard value 0
        activateSettings();
        //endregion


        //region Delete All Data

        final Button deleteButton = findViewById(R.id.button_deleteAll);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final AlertDialog.Builder helpDialog = new AlertDialog.Builder(SecondActivity.this);
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


        //endregion

        //region Premium

        final Button premiumActivator = findViewById(R.id.button_premiumActivator);
        final TextView premiumInfo = findViewById(R.id.textView_premiumInfo);
        premiumActivator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.getInstance().setPremium(!AppData.getInstance().isPremium());
                AppData.getInstance().saveAppData();
                if (AppData.getInstance().isPremium()) {
                    Toast.makeText(MainActivity.getInstance().getContext(), "Premium aktiviert", Toast.LENGTH_LONG).show();
                    premiumActivator.setBackgroundColor(getResources().getColor(R.color.premiumActiveBg));
                    premiumActivator.setTypeface(Typeface.DEFAULT_BOLD);
                    premiumInfo.setText(getString(R.string.premium_boughtInfo));
                } else {
                    Toast.makeText(MainActivity.getInstance().getContext(), "Premium deaktiviert", Toast.LENGTH_LONG).show();
                    premiumActivator.setBackgroundColor(getResources().getColor(R.color.lightGrey));
                    premiumActivator.setTypeface(Typeface.DEFAULT);
                    premiumInfo.setText(getString(R.string.premium_info));
                }
                updateVersionInfo();
            }
        });


        //change the look of items that are effected by premium by activity load
        if (AppData.getInstance().isPremium()) {
            premiumActivator.setBackgroundColor(getResources().getColor(R.color.premiumActiveBg));
            premiumActivator.setTypeface(Typeface.DEFAULT_BOLD);
            premiumInfo.setText(getString(R.string.premium_boughtInfo));
        }



        //endregion


        //region Versioninfo


        //endregion


        Button navButton = findViewById(R.id.button_navSecond);
        navButton.bringToFront();
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.super.onBackPressed();
            }
        });


        ImageView ccb = findViewById(R.id.imageView_ccb);
        ImageView rFreis = findViewById(R.id.imageview_rFreis);
        ccb.bringToFront();
        rFreis.bringToFront();

        ccb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(getString(R.string.chrisGithub));
            }
        });

        rFreis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(getString(R.string.robertGithub));
            }
        });


        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                light.setChecked(!isChecked);//these 2 switches cant be the same at any given time
                settings[0] = getString(R.string.setting_darkMode);
            }
        });

        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dark.setChecked(!isChecked);//these 2 switches cant be the same at any given time
                settings[0] = getString(R.string.setting_lightMode);
            }
        });

        changeTextSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //small text
                    settings[1] = getString(R.string.setting_smallText);
                } else {
                    //big text
                    settings[1] = getString(R.string.setting_bigText);
                }
            }
        });


    }

    private void activateSettings() {

    }


    private void openLink(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private void updateVersionInfo() {
        Resources res = getResources();
        final String[] helpText = res.getStringArray(R.array.versionInfo);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String s : helpText) {
            sb.append(s);
            if (count == 0 && AppData.getInstance().isPremium())
                sb.append(" (Premium Version)");
            sb.append(System.getProperty("line.separator"));
            count++;
        }
        TextView versionInfo = findViewById(R.id.textView_versionInfo);
        versionInfo.setText(sb.toString());
    }


}
