package com.example.mytrips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    HistoryData[] historyData;

    public HistoryAdapter(HistoryData[] historyData) {
        this.historyData = historyData;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.tripNameTxtHistory.setText(historyData[position].getTripNameHistory());
        holder.stateTxtHistory.setText(historyData[position].getStateHistory());
        holder.dateTxtHistory.setText(historyData[position].getDateHistory());
        holder.timeTxtHistory.setText(historyData[position].getTimeHistory());
        holder.startTxtHistory.setText(historyData[position].getStartPointHistory());
        holder.endTxtHistory.setText(historyData[position].getEndPointHistory());
        boolean isExpanded = historyData[position].isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return historyData.length;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateTxtHistory, timeTxtHistory, tripNameTxtHistory, stateTxtHistory, startTxtHistory, endTxtHistory;
        ConstraintLayout expandableLayout;
        LinearLayout linearLayout;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tripNameTxtHistory = itemView.findViewById(R.id.history_name);
            stateTxtHistory = itemView.findViewById(R.id.history_state);
            dateTxtHistory = itemView.findViewById(R.id.history_date);
            timeTxtHistory = itemView.findViewById(R.id.history_time);
            startTxtHistory = itemView.findViewById(R.id.history_start);
            endTxtHistory = itemView.findViewById(R.id.history_end);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            HistoryData historyData1 = historyData[getAdapterPosition()];
            historyData1.setExpanded(!historyData1.isExpanded());
            notifyDataSetChanged();
        }
    }
}
