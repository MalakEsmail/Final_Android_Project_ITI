package com.example.mytrips;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditFragment extends Fragment {
    EditText etTripName,etStartPoint,etEndPoint;
    Spinner repetitionSpinner,tripTypeSpinner;
    Button btnSave,btnCancel;
    List<String> notesList;
    ArrayAdapter<String> arrayAdapter;
    ListView list;
    TextView tvDate,tvTime;
    DatabaseReference myRef;
    TripInfo tripInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_edit, container, false);

        tripInfo=new TripInfo();
        Bundle bundle=this.getArguments();
        etTripName=view.findViewById(R.id.etTripName);
        etStartPoint=view.findViewById(R.id.etStartPoint);
        etEndPoint=view.findViewById(R.id.etEndPoint);
        repetitionSpinner=view.findViewById(R.id.repetitionSpinner);
        tripTypeSpinner=view.findViewById(R.id.tripTypeSpinner);
        tvDate=view.findViewById(R.id.tvDate);
        tvTime=view.findViewById(R.id.tvTime);
        list=view.findViewById(R.id.list);
        btnSave=view.findViewById(R.id.btnSave);
        btnCancel=view.findViewById(R.id.btnCancel);

        final String id=bundle.getString("TripId");
        etTripName.setText(bundle.getString("Name"));
        etStartPoint.setText(bundle.getString("StartPoint"));
        etEndPoint.setText(bundle.getString("EndPoint"));
        tvDate.setText(bundle.getString("Date"));
        tvTime.setText(bundle.getString("Time"));
        tripInfo.setStatus("Upcoming");
        myRef=FirebaseDatabase.getInstance().getReference().child("TripInfo");
        notesList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.notes_list, notesList);
        list.setAdapter(arrayAdapter);
        myRef.child(id).child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    notesList.clear();
                    for (DataSnapshot dss:snapshot.getChildren()){
                        String notes=dss.getValue(String.class);
                        notesList.add(notes);
                        arrayAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //Calendar btn
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        tvDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        //Alarm btn
        Calendar c=Calendar.getInstance();
        final int hour=calendar.get(c.HOUR_OF_DAY);
        final int minute=calendar.get(c.MINUTE);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time=hour+":"+minute;
                        tvTime.setText(time);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });

        //btn Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(getActivity());
                deleteBuilder.setMessage("Are you sure you want to edit this trip")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                tripInfo.setName(etTripName.getText().toString().trim());
                                tripInfo.setStartPoint(etStartPoint.getText().toString().trim());
                                tripInfo.setEndPoint(etEndPoint.getText().toString().trim());
                                tripInfo.setDate(tvDate.getText().toString().trim());
                                tripInfo.setTime(tvTime.getText().toString().trim());
                                tripInfo.setTripType(tripTypeSpinner.getSelectedItem().toString().trim());
                                tripInfo.setRepetition(repetitionSpinner.getSelectedItem().toString().trim());
                                tripInfo.setNotes(notesList);
                                tripInfo.setStatus("Upcoming");
                                tripInfo.setuId(uId);
                                tripInfo.setTripId(id);
                                myRef.child(id).setValue(tripInfo);
                                Toast.makeText(getActivity(),"Trip edit successfully",Toast.LENGTH_LONG).show();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingFragment()).commit();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });

        //btn Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(getActivity());
                deleteBuilder.setMessage("Are you sure you want to cancel and don't save any change")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingFragment()).commit();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
        return view;
    }

}