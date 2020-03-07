package com.example.timesup_final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DeadlineAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel")
                .setSmallIcon(R.drawable.times_up_pic)
                .setContentTitle(extras.getString("TITLE"))
                .setContentText(extras.getString("DESC"))
                .setContentInfo(context.getString(R.string.app_name))
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{2000, 2000, 2000, 2000, 2000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(extras.getString("TITLE").length()
                + extras.getString("DESC").length(), builder.build());
    }
}
