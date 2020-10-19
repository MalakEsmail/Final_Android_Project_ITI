package com.example.mytrips.reminder;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mytrips.R;

public class Dialog extends FragmentActivity {
    String tripName, tripId, startPoint, endPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("tripName")) {
               tripName = extras.getString("tripName");
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

            CustomDialogFragment customDialogFragment = new CustomDialogFragment(tripId, "Your trip : "+tripName, startPoint, endPoint);
            customDialogFragment.show(getSupportFragmentManager(), "CUSTOM");

        }



    }
}