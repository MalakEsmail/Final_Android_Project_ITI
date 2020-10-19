package com.example.mytrips;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    DatabaseReference tripRef;
    String uid;
    Query userTripQuery;

    HistoryData historyData;
    ArrayList<HistoryData> listOfHistoryData;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.history_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listOfHistoryData = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tripRef = FirebaseDatabase.getInstance().getReference().child("TripInfo");
        userTripQuery =
                tripRef.orderByChild("uId").equalTo(uid);
        userTripQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        historyData = data.getValue(HistoryData.class);
                        if ((historyData.getStatus()).equals("Done")||(historyData.getStatus()).equals("Canceled")) {
                            listOfHistoryData.add(historyData);

                        }
                    }
                    historyAdapter = new HistoryAdapter(listOfHistoryData, getContext());
                    recyclerView.setAdapter(historyAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      return view;
    }
}