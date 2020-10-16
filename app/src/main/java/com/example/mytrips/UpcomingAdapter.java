package com.example.mytrips;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    UpcomingData[] upcomingData;
    Context context;
    public UpcomingAdapter(UpcomingData[] upcomingData,Context context) {
        this.upcomingData = upcomingData;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.dateTxt.setText(upcomingData[position].getDate());
        holder.timeTxt.setText(upcomingData[position].getTime());
        holder.tripNameTxt.setText(upcomingData[position].getTripName());
        holder.stateTxt.setText(upcomingData[position].getState());
        holder.startTxt.setText(upcomingData[position].getStartPoint());
        holder.endTxt.setText(upcomingData[position].getEndPoint());

    }

    @Override
    public int getItemCount() {
        return upcomingData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  PopupMenu.OnMenuItemClickListener {
        TextView dateTxt, timeTxt, tripNameTxt, stateTxt, startTxt, endTxt;
        ImageButton tripMenu ,showNotes;
        Button startTrip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.upcoming_date);
            timeTxt = itemView.findViewById(R.id.upcoming_time);
            tripNameTxt = itemView.findViewById(R.id.upcoming_name);
            stateTxt = itemView.findViewById(R.id.upcoming_state);
            startTxt = itemView.findViewById(R.id.upcoming_start);
            endTxt = itemView.findViewById(R.id.upcoming_end);
            tripMenu = itemView.findViewById(R.id.trip_menu);
            showNotes = itemView.findViewById(R.id.show_notes);
            startTrip = itemView.findViewById(R.id.start_trip);
            showNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager manager =((AppCompatActivity)context).getSupportFragmentManager();
                    ShowNotesDialog showNotesDialog = new ShowNotesDialog();
                    showNotesDialog.show(manager ,null);
                }
            });
            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Start", Toast.LENGTH_LONG).show();
                }
            });
            tripMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });


        }


        private void showPopupMenu(View view) {

            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.trip_menu);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {

                case R.id.notes:
                    Toast.makeText(itemView.getContext(), "notes", Toast.LENGTH_LONG).show();
                    break;
                case R.id.edit_trip:
                    Toast.makeText(itemView.getContext(), "edit", Toast.LENGTH_LONG).show();
                    break;
                case R.id.delete_trip:
                    AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(itemView.getContext());
                    deleteBuilder.setMessage("Are you sure you want to delete")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                    break;
                case R.id.cancel_trip:
                    AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(itemView.getContext());
                    cancelBuilder.setMessage("Are you sure you want to cancel")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(itemView.getContext(), "cancel", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                    break;
            }
            return true;
        }
    }


}
