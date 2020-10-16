package com.example.mytrips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HistoryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.history_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HistoryData[] historyData =new HistoryData[]{
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
                new HistoryData("Collage","Done","15-10-2020","4:00","Alexandria","KafrAlshiekh"),
        };
        HistoryAdapter historyAdapter =new HistoryAdapter(historyData);
        recyclerView.setAdapter(historyAdapter);
        return view;
    }
}