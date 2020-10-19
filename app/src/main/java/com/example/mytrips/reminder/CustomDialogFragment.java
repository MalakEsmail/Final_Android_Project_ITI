package com.example.mytrips.reminder;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.mytrips.R;
import com.example.mytrips.reminder.NotificationBroadCast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CustomDialogFragment extends DialogFragment {
    private static final String TAG = "CUSTOM";

    TextView name;
    Button start, cancel, later;
    String tripName,  startPoint, endPoint,tripId;
    MediaPlayer mediaPlayer;
    NotificationManager notificationManager;
    DatabaseReference ref;


    public CustomDialogFragment() {
        // Required empty public constructor
    }

    public CustomDialogFragment(String tripId , String tripName, String startPoint, String endPoint) {
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tripId = tripId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);
        mediaPlayer = MediaPlayer.create(getContext(), Settings.System.DEFAULT_RINGTONE_URI);

        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(220);


        mediaPlayer.start();
        createNotificationChannel();
        name = view.findViewById(R.id.trip_name_tv_id);
        start = view.findViewById(R.id.start_button_id);
        cancel = view.findViewById(R.id.cancel_button_id);
        later = view.findViewById(R.id.later_button_id);

        name.setText(tripName);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status", "Done");
                ref.child(tripId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "You Started your Trip", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                displayTrack(startPoint, endPoint);
                getDialog().cancel();
                getActivity().finish();

                mediaPlayer.stop();

            }
        });
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), NotificationBroadCast.class);
                getActivity().sendBroadcast(intent);

                Toast.makeText(getContext(), "later Clicked", Toast.LENGTH_SHORT).show();
                getDialog().cancel();
                getActivity().finish();
                mediaPlayer.stop();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status", "Canceled");
                ref.child(tripId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "You Trip is Canceled ..", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                getDialog().cancel();
                getActivity().finish();

                mediaPlayer.stop();


            }
        });


        return view;
    }

    private void displayTrack(String startPoint, String endPoint) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + startPoint + "/" + endPoint);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            CharSequence sequence = "ReminderChannel";
            String description = "Channel For Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("notify", sequence, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
