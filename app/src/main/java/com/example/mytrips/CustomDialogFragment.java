package com.example.mytrips;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;


public class CustomDialogFragment extends DialogFragment {

    private static final String TAG = "CUSTOM";

    TextView name, notes;
    Button start, cancel, later;
    String tripName, tripNotes, startPoint, endPoint;


    public CustomDialogFragment() {
        // Required empty public constructor
    }

    public CustomDialogFragment(String tripName, String tripNotes, String startPoint, String endPoint) {
        this.tripName = tripName;
        this.tripNotes = tripNotes;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
        name = view.findViewById(R.id.trip_name_tv_id);
        notes = view.findViewById(R.id.trip_notes_tv_id);
        start = view.findViewById(R.id.start_button_id);
        cancel = view.findViewById(R.id.cancel_button_id);
        later = view.findViewById(R.id.later_button_id);

        name.setText(tripName);
        notes.setText(tripNotes);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTrack(startPoint, endPoint);
                getDialog().dismiss();
            }
        });
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo create notification that can restart dialog from again
                Toast.makeText(getContext(), "later Clicked", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change trip status to canceled
                Toast.makeText(getContext(), "cancel Clicked", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
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

}