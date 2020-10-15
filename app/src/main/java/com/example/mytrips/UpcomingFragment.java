package com.example.mytrips;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


public class UpcomingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.upcoming_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UpcomingData[] upcomingData =new UpcomingData[]{
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
                new UpcomingData("25-6-2030","23:22","Collage","Upcoming","Alex","Cairo"),
        };
        UpcomingAdapter upcomingAdapter =new UpcomingAdapter(upcomingData);
        recyclerView.setAdapter(upcomingAdapter);
        return view;
    }

}