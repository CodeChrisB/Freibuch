package com.example.foodgent.UserInterface.Premium;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgent.AppData.Entities.ShoppingEntry;
import com.example.foodgent.AppData.Logic.AppData;
import com.example.fragment.R;
import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.List;

public class ChangeShopListApperance extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_change_shoppinglist);

        final TextView shopHeader = findViewById(R.id.settingP_textView_changeShopHead);
        shopHeader.setText(AppData.getInstance().getShopHeader());

        final TextView shopMarker = findViewById(R.id.settingP_textView_changeListmarker);
        shopMarker.setText(AppData.getInstance().getRowMaker());

        final TextView shopseperator = findViewById(R.id.settingP_textView_changeSeperator);
        shopseperator.setText(AppData.getInstance().getSeperator());

        setUpPreview();

        final Button saveNewHeader = findViewById(R.id.settingP_button_saveNewHeader);
        saveNewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getStringLengthFromTextView(saveNewHeader) > 30)
                    return;
                AppData.getInstance().setShopHeader(shopHeader.getText().toString());
                AppData.getInstance().saveShopEntries();
                setUpPreview();
            }
        });


        final Button saveRowMarker = findViewById(R.id.settingP_button_saveMarker);
        saveRowMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getStringLengthFromTextView(shopMarker) > 6)
                    return;
                AppData.getInstance().setRowMaker(shopMarker.getText().toString());
                AppData.getInstance().saveShopEntries();
                setUpPreview();
            }
        });


        final Button saveSeperator = findViewById(R.id.settingP_button_saveSeperator);
        saveSeperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getStringLengthFromTextView(shopseperator) > 20)
                    return;
                AppData.getInstance().setSeperator(shopseperator.getText().toString());
                AppData.getInstance().saveShopEntries();
                setUpPreview();
            }
        });











        final TextView listPreview = findViewById(R.id.settingP_previewShopList);


    }


    private int getStringLengthFromTextView(TextView textView) {
        String s = textView.getText().toString();
        int emojiCount = EmojiParser.extractEmojis(s).size();
        String noEmojiString = EmojiParser.removeAllEmojis(s);
        int emojiAndStringCount = noEmojiString.length() + emojiCount;
        return emojiAndStringCount;
    }

    public void setUpPreview() {
        final TextView listPreview = findViewById(R.id.settingP_previewShopList);
        //Create Header
        StringBuilder sb = new StringBuilder();
        sb.append(AppData.getInstance().getShopHeader());
        sb.append(System.getProperty("line.separator"));
        sb.append(AppData.getInstance().getSeperator());
        sb.append(System.getProperty("line.separator"));

        String rowMarker = AppData.getInstance().getRowMaker();

        List<ShoppingEntry> list = new ArrayList<>();

        list.add(new ShoppingEntry("Äpfel"));
        list.add(new ShoppingEntry("Brot"));
        list.add(new ShoppingEntry("Bio Milch"));
        list.add(new ShoppingEntry("Kirschkuchen"));
        list.add(new ShoppingEntry("Karotten"));
        list.add(new ShoppingEntry("Limonade"));


        list.add(new ShoppingEntry("Zitrone"));
        list.add(new ShoppingEntry("Pfefferminz Tee"));
        list.add(new ShoppingEntry("Küchenrolle"));
        list.add(new ShoppingEntry("Ketchup"));
        list.add(new ShoppingEntry("Butter"));
        list.add(new ShoppingEntry("Antischocken"));


        //call toString Method for every Entry in the List
        for (ShoppingEntry item : list) {
            String line = rowMarker + item.toString();
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        String listText = sb.toString();


        String text = listText.replace("\n", "&lt;br&gt;");
        listPreview.setText(Html.fromHtml(Html.fromHtml(text).toString()));
    }
}
