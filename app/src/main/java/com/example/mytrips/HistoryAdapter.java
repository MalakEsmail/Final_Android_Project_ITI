package com.example.mytrips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    HistoryData[] historyData;
    Context context;

    public HistoryAdapter(HistoryData[] historyData ,Context context) {
        this.historyData = historyData;
        this.context=context;
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

    public class HistoryViewHolder extends RecyclerView.ViewHolder  {
        TextView dateTxtHistory, timeTxtHistory, tripNameTxtHistory, stateTxtHistory, startTxtHistory, endTxtHistory;
        ConstraintLayout expandableLayout;
        LinearLayout linearLayout;
        Button cancel ;
        ImageButton showHistoryNotes;

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
            cancel=itemView.findViewById(R.id.cancel_history_trip);
            showHistoryNotes=itemView.findViewById(R.id.show_history_notes);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryData historyData1 = historyData[getAdapterPosition()];
                    historyData1.setExpanded(!historyData1.isExpanded());
                    notifyDataSetChanged();
                }
            });
            showHistoryNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //FragmentManager manager =((AppCompatActivity)context).getSupportFragmentManager();
                    //ShowNotesDialog showNotesDialog = new ShowNotesDialog();
                    //showNotesDialog.show(manager ,null);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(view.getContext());
                    cancelBuilder.setMessage("Are you sure you want to cancel")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(view.getContext(), "cancel", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });
        }

    }
}
