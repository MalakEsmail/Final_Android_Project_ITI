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
import android.os.Bundle;
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

import com.example.mytrips.reminder.ReminderBroadCast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    // UpcomingData[] upcomingData;
    ArrayList<UpcomingData> upcomingData;
    Context context;
    DatabaseReference ref, myRef;
    String tripId;
    String startTripId;
    List<String> displayNotes;

    public UpcomingAdapter(ArrayList<UpcomingData> upcomingData, Context context) {
        //  this.upcomingData = upcomingData;
        this.upcomingData = upcomingData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);
        displayNotes = new ArrayList<String>();
        ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //  if (upcomingData.get(position).getDateAndTimeInMillis() == String.valueOf(System.currentTimeMillis())) {

        //  Intent intent = new Intent(context, ReminderBroadCast.class);
        // intent.putExtra("start", upcomingData.get(0).getStartPoint());
        // Toast.makeText(context, ""+upcomingData.get(position).getDateAndTimeInMillis(), Toast.LENGTH_SHORT).show();
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (60 * 1000)/*Long.valueOf(upcomingData.get(position).getDateAndTimeInMillis())*/, pendingIntent);

        //}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReminderBroadCast.class);
                intent.setAction("com.example.mytrips");
                intent.putExtra("name", upcomingData.get(position).getName());
                intent.putExtra("start", upcomingData.get(position).getStartPoint());
                intent.putExtra("end", upcomingData.get(position).getEndPoint());
                intent.putExtra("tripId", upcomingData.get(position).getTripId());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (5 * 1000)/*Long.valueOf(upcomingData.get(position).getDateAndTimeInMillis())*/, pendingIntent);

            }
        });
        holder.dateTxt.setText(upcomingData.get(position).getDate());
        holder.timeTxt.setText(upcomingData.get(position).getTime());
        holder.tripNameTxt.setText(upcomingData.get(position).getName());
        holder.stateTxt.setText(upcomingData.get(position).getStatus());
        holder.startTxt.setText(upcomingData.get(position).getStartPoint());
        holder.endTxt.setText(upcomingData.get(position).getEndPoint());
        holder.showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tripId = upcomingData.get(position).getTripId();
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ShowNotesDialog showNotesDialog = new ShowNotesDialog(tripId);
                showNotesDialog.show(manager, null);

            }
        });
        holder.startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTripId = upcomingData.get(position).getTripId();
                String startPoint = upcomingData.get(position).getStartPoint();
                String endPoint = upcomingData.get(position).getEndPoint();

//                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("status", "Done");
                ref.child(startTripId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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
                                myRef = FirebaseDatabase.getInstance().getReference().child("TripInfo").child(upcomingData.get(position).getTripId()).child("notes");
                                Bundle b = new Bundle();
                                b.putString("TripId", upcomingData.get(position).getTripId());
                                b.putString("Name", upcomingData.get(position).getName());
                                b.putString("StartPoint", upcomingData.get(position).getStartPoint());
                                b.putString("EndPoint", upcomingData.get(position).getEndPoint());
                                b.putString("Date", upcomingData.get(position).getDate());
                                b.putString("Time", upcomingData.get(position).getTime());
                                b.putString("Repetition", upcomingData.get(position).getRepetition());
                                b.putString("TripType", upcomingData.get(position).getTripType());
                                AddNotesFragment addNotesFragment = new AddNotesFragment();
                                addNotesFragment.setArguments(b);
                                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addNotesFragment).addToBackStack("").commit();
                                break;
                            case R.id.details_trip:
                                myRef = FirebaseDatabase.getInstance().getReference().child("TripInfo").child(upcomingData.get(position).getTripId()).child("notes");
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("TripId", upcomingData.get(position).getTripId());
                                bundle2.putString("Name", upcomingData.get(position).getName());
                                bundle2.putString("StartPoint", upcomingData.get(position).getStartPoint());
                                bundle2.putString("EndPoint", upcomingData.get(position).getEndPoint());
                                bundle2.putString("Date", upcomingData.get(position).getDate());
                                bundle2.putString("Time", upcomingData.get(position).getTime());
                                bundle2.putString("Repetition", upcomingData.get(position).getRepetition());
                                bundle2.putString("TripType", upcomingData.get(position).getTripType());
                                //todo sth wrong here
//                                DetailsFragment detailsFragment=new DetailsFragment();
//                                detailsFragment.setArguments(bundle2);
                                //  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailsFragment).addToBackStack("").commit();
                                break;
                            case R.id.edit_trip:
                                myRef = FirebaseDatabase.getInstance().getReference().child("TripInfo").child(upcomingData.get(position).getTripId()).child("notes");
                                Bundle bundle = new Bundle();
                                bundle.putString("TripId", upcomingData.get(position).getTripId());
                                bundle.putString("Name", upcomingData.get(position).getName());
                                bundle.putString("StartPoint", upcomingData.get(position).getStartPoint());
                                bundle.putString("EndPoint", upcomingData.get(position).getEndPoint());
                                bundle.putString("Date", upcomingData.get(position).getDate());
                                bundle.putString("Time", upcomingData.get(position).getTime());
                                bundle.putString("Repetition", upcomingData.get(position).getRepetition());
                                bundle.putString("TripType", upcomingData.get(position).getTripType());
                                EditFragment editFragment = new EditFragment();
                                editFragment.setArguments(bundle);
                                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editFragment).addToBackStack("").commit();
                                break;
                            case R.id.delete_trip:

                                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(context);
                                deleteBuilder.setMessage("Are you sure you want to delete")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                tripId = upcomingData.get(position).getTripId();
//                                                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");
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
//                                                ref = FirebaseDatabase.getInstance().getReference().child("TripInfo");

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("status", "Canceled");
                                                ref.child(upcomingData.get(position).getTripId()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "You Trip is Canceled ..", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });                                            }
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
