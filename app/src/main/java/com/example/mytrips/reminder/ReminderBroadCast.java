package com.example.mytrips.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, Dialog.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


         }
}
