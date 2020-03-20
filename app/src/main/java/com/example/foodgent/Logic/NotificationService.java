package com.example.foodgent.Logic;

import android.os.Build;

public class NotificationService {


    public void Notify(String title, String content) {


        if (Build.VERSION.SDK_INT >= 26) {
            //only api 26 above
        } else {
            //only api 26 down
        }
    }

}
