package com.example.mytrips;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UpcomingFragment extends Fragment {
    DatabaseReference tripRef;
    String uid;
    Query userTripQuery;

    UpcomingData oneUpcomingData;
    ArrayList<UpcomingData> listOfUpcomingData;
    UpcomingAdapter upcomingAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
         recyclerView = view.findViewById(R.id.upcoming_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listOfUpcomingData = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tripRef = FirebaseDatabase.getInstance().getReference().child("TripInfo");
        userTripQuery =
                tripRef.orderByChild("uId").equalTo(uid);


        userTripQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                         oneUpcomingData = data.getValue(UpcomingData.class);
                         listOfUpcomingData.add(oneUpcomingData);
                     }
                    upcomingAdapter = new UpcomingAdapter(listOfUpcomingData, getContext());
                    recyclerView.setAdapter(upcomingAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

     return view;
    }

}