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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
   // HistoryData[] historyData;
   ArrayList<HistoryData> historyData;
    DatabaseReference ref;
    String tripId;


    Context context;

    public HistoryAdapter(ArrayList<HistoryData> historyData, Context context) {
        this.historyData = historyData;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        holder.tripNameTxtHistory.setText(historyData.get(position).getName());
        holder.stateTxtHistory.setText(historyData.get(position).getStatus());
        holder.dateTxtHistory.setText(historyData.get(position).getDate());
        holder.timeTxtHistory.setText(historyData.get(position).getTime());
        holder.startTxtHistory.setText(historyData.get(position).getStartPoint());
        holder.endTxtHistory.setText(historyData.get(position).getEndPoint());
        boolean isExpanded = historyData.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder. showHistoryNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tripId = historyData.get(position).getTripId();
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ShowNotesDialog showNotesDialog = new ShowNotesDialog(tripId);
                showNotesDialog.show(manager, null);
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(view.getContext());
                cancelBuilder.setMessage("Are you sure you want to Delete")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tripId = historyData.get(position).getTripId();
                                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");
                                ref.child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            ref.child(tripId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(context, "Trip deleted successfully..", Toast.LENGTH_LONG).show();

                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Try again !!", Toast.LENGTH_LONG).show();

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

    @Override
    public int getItemCount() {
        return historyData.size();
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
                    HistoryData historyData1 = historyData.get(getAdapterPosition());
                    historyData1.setExpanded(!historyData1.isExpanded());
                    notifyDataSetChanged();
                }
            });


        }

    }
}
