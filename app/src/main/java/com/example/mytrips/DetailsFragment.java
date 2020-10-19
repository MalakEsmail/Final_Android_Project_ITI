package com.example.mytrips;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    TextView tvTripName, tvStartPoint, tvEndPoint, tvRepetition, tvTripType;
    Button btnSave, btnCancel;
    List<String> notesList;
    ArrayAdapter<String> arrayAdapter;
    ListView list;
    TextView tvDate, tvTime;
    DatabaseReference myRef;
    TripInfo tripInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        tripInfo = new TripInfo();
        Bundle bundle = this.getArguments();
        tvTripName = view.findViewById(R.id.tvTripName);
        tvStartPoint = view.findViewById(R.id.tvStartPoint);
        tvEndPoint = view.findViewById(R.id.tvEndPoint);
        tvRepetition = view.findViewById(R.id.tvRepetition);
        tvTripType = view.findViewById(R.id.tvTripType);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        list = view.findViewById(R.id.list);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);

        final String id = bundle.getString("TripId");
        tvTripName.setText(bundle.getString("Name"));
        tvStartPoint.setText(bundle.getString("StartPoint"));
        tvEndPoint.setText(bundle.getString("EndPoint"));
        tvDate.setText(bundle.getString("Date"));
        tvTime.setText(bundle.getString("Time"));
        tvRepetition.setText(bundle.getString("Repetition"));
        tvTripType.setText(bundle.getString("TripType"));


        myRef = FirebaseDatabase.getInstance().getReference().child("TripInfo");
        notesList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.notes_list, notesList);
        list.setAdapter(arrayAdapter);
        myRef.child(id).child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    notesList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        String notes = dss.getValue(String.class);
                        notesList.add(notes);
                        arrayAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //btn Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingFragment()).commit();
            }
        });
        return view;
    }
}