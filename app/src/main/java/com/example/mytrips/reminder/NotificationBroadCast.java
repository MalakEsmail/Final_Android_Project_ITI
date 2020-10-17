package com.example.mytrips.reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mytrips.R;

public class NotificationBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ReminderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);

        NotificationCompat.Action action = new NotificationCompat.Action(null, "get Alert !", pendingIntent);
        NotificationCompat.Builder builder;

        builder = new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.drawable.ic_baseline_notification)
                .setContentTitle("Your Trip Reminder")
                .setContentText("Are You Ready To Start your Trip !")
                .addAction(action)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(220, builder.build());

    }
}
