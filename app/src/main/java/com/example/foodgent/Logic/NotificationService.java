package com.example.foodgent.Logic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.foodgent.UserInterface.MainActivity;
import com.example.fragment.R;

public class NotificationService extends ContextWrapper {


    private static final String CHANNEL_NAME = "Main Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notification description";
    private static final String CHANNEL_ID = "MyNotificationChannel";
    private static NotificationManager notificationManager;
    private Context base;

    public NotificationService(Context base) {
        super(base);
        this.base = base;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    public void Notify(String title, String content) {


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);
        notificationChannel.canShowBadge();
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getNotificationManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) base.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    // now lets create notification
    public void notify(String title, String message) {


        if (Build.VERSION.SDK_INT >= 26) {
            //only api 26 above
            NotificationCompat.Builder builder = new NotificationCompat.Builder(base, CHANNEL_ID);
            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(title);
            builder.setSmallIcon(R.drawable.kuehli);
            builder.setContentText(message);
            builder.setSound(notificationUri);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            getNotificationManager().notify(9001, builder.build());
        } else {
            //only api 26 down
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.getInstance().getContext(), "1");
            builder.setSmallIcon(R.drawable.splashlogo_calm);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.getInstance().getContext());
            notificationManagerCompat.notify(1, builder.build());

        }


    }

}