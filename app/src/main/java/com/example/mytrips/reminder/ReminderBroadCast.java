package com.example.mytrips.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ReminderBroadCast extends BroadcastReceiver {
    String tripName, tripId, startPoint, endPoint;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("name")) {
                tripName = extras.getString("name");
            }
            if (extras.containsKey("tripId")) {
                tripId = extras.getString("tripId");
            }
            if (extras.containsKey("start")) {
                startPoint = extras.getString("start");
            }
            if (extras.containsKey("end")) {
                endPoint = extras.getString("end");
            }
            Intent i = new Intent(context, Dialog.class);
            i.setAction("com.example.mytrips.reminder");
            i.putExtra("tripName", tripName);
            i.putExtra("tripId", tripId);
            i.putExtra("start", startPoint);
            i.putExtra("end", endPoint);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


        }


    }
}
