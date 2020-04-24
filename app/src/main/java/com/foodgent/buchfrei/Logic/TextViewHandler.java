package com.foodgent.buchfrei.Logic;

import android.graphics.Color;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment.R;
import com.foodgent.buchfrei.UserInterface.SettingPage.SettingsActivityModern;

import java.util.ArrayList;
import java.util.List;

public class TextViewHandler extends AppCompatActivity {


    private static TextViewHandler instance = null;
    List<TextView> settings = new ArrayList<>();

    //ItemActivity

    //RecipeActivity

    //ShopActivity

    //MainActivity

    //SettomgsActivityModern


    public TextViewHandler() {

    }

    public static TextViewHandler getInstance() {
        if (instance == null) {
            instance = new TextViewHandler();
        }
        return instance;
    }

    private void initSettings() {
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textDarkMode)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textDelete)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textSize)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textlanguage)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textFeedback)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textNotification)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textHelp)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textDeveloper)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textpremium1)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textPremium2)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textPremium3)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textHeaderSetting)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textHeaderPersonalize)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textHeaderMore)));
        settings.add(makeView((TextView) SettingsActivityModern.setting.findViewById(R.id.setting_textHeaderPremium)));
    }

    private TextView makeView(TextView textView) {
        return textView;
    }

    public void setSettingsDarkmode(boolean bool) {
        initSettings();
        if (bool) {
            for (TextView text : settings) {
                text.setTextColor(Color.WHITE);
            }
        } else {
            for (TextView text : settings) {
                text.setTextColor(Color.BLACK);
            }
        }
    }
}
