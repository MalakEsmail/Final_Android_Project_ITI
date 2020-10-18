package com.example.mytrips;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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

import java.util.HashMap;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    // UpcomingData[] upcomingData;
    ArrayList<UpcomingData> upcomingData;
    Context context;
    DatabaseReference ref;
    String tripId;
    String startTripId;

    public UpcomingAdapter(ArrayList<UpcomingData> upcomingData, Context context) {
        //  this.upcomingData = upcomingData;
        this.upcomingData = upcomingData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.dateTxt.setText(upcomingData.get(position).getDate());
        holder.timeTxt.setText(upcomingData.get(position).getTime());
        holder.tripNameTxt.setText(upcomingData.get(position).getName());
        holder.stateTxt.setText(upcomingData.get(position).getStatus());
        holder.startTxt.setText(upcomingData.get(position).getStartPoint());
        holder.endTxt.setText(upcomingData.get(position).getEndPoint());
        holder.startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTripId = upcomingData.get(position).getTripId();
                String startPoint = upcomingData.get(position).getStartPoint();
                String endPoint = upcomingData.get(position).getEndPoint();

                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status", "Done");
                ref.child(startTripId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                          Toast.makeText(context, "You Started your Trip", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                displayTrack(startPoint, endPoint);

            }
        });
        holder.tripMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(R.menu.trip_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.notes:
                                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddNotesFragment()).addToBackStack("").commit();
                                Toast.makeText(context, "notes", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.edit_trip:
                                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditFragment()).addToBackStack("").commit();
                                Toast.makeText(context, "edit", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.delete_trip:

                                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(context);
                                deleteBuilder.setMessage("Are you sure you want to delete")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                    tripId = upcomingData.get(position).getTripId();
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
                                break;
                            case R.id.cancel_trip:
                                AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(context);
                                cancelBuilder.setMessage("Are you sure you want to cancel")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();
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
                });
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return upcomingData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTxt, timeTxt, tripNameTxt, stateTxt, startTxt, endTxt;
        ImageButton tripMenu, showNotes;
        Button startTrip;

        public ViewHolder(@NonNull final View itemView) {
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

                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    ShowNotesDialog showNotesDialog = new ShowNotesDialog();
                    showNotesDialog.show(manager, null);

                }
            });

        }

    }

    private void displayTrack(String startPoint, String endPoint) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + startPoint + "/" + endPoint);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
    }
}
