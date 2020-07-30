package com.chancellor.degreemap.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.views.MainActivity.MainActivity;

public class AlertReceiver extends BroadcastReceiver {

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Notification ID.
    private static int NOTIFICATION_ID;
    String title;
    String type, status;
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, PRIMARY_CHANNEL_ID);

        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NOTIFICATION_ID = intent.getIntExtra("notification_id", -1);
        title = intent.getStringExtra("title");
        status = intent.getStringExtra("status");
        type = intent.getStringExtra("type");

        Intent contentIntent = new Intent(context, MainActivity.class);


        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText("Your " + type + " - " + title + " is " + status + " today.")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = PRIMARY_CHANNEL_ID;
            String description = "Course and Assessment Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
