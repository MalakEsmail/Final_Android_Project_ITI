package com.example.mytrips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class AddFragment extends Fragment {
    EditText etTripName, etStartPoint, etEndPoint;
    Spinner repetitionSpinner, tripTypeSpinner;
    Button btnCalendar, btnAlarm, btnAdd;
    TextView tvCalendar, tvAlarm;
    TripInfo tripInfo;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etTripName = view.findViewById(R.id.etTripName);
        etStartPoint = view.findViewById(R.id.etStartPoint);
        etEndPoint = view.findViewById(R.id.etEndPoint);
        repetitionSpinner = view.findViewById(R.id.repetitionSpinner);
        tripTypeSpinner = view.findViewById(R.id.tripTypeSpinner);
        btnAlarm = view.findViewById(R.id.btnAlarm);
        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnAdd = view.findViewById(R.id.btnAdd);
        tvCalendar = view.findViewById(R.id.tvCalendar);
        tvAlarm = view.findViewById(R.id.tvAlarm);

        tripInfo=new TripInfo();
        myRef= FirebaseDatabase.getInstance().getReference().child("TripInfo");

        //Calendar btn
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvCalendar.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        //Alarm btn
        Calendar c = Calendar.getInstance();
        final int hour = calendar.get(c.HOUR_OF_DAY);
        final int minute = calendar.get(c.MINUTE);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        tvAlarm.setText(time);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        //btnAdd
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()==true){
                tripInfo.setName(etTripName.getText().toString().trim());
                tripInfo.setStartPoint(etStartPoint.getText().toString().trim());
                tripInfo.setEndPoint(etEndPoint.getText().toString().trim());
                tripInfo.setDate(tvCalendar.getText().toString().trim());
                tripInfo.setTime(tvAlarm.getText().toString().trim());
                tripInfo.setTripType(tripTypeSpinner.getSelectedItem().toString().trim());
                tripInfo.setRepetition(repetitionSpinner.getSelectedItem().toString().trim());
                myRef.child(etTripName.getText().toString()).setValue(tripInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       Toast.makeText(getActivity(),"Trip Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"Try Again", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                    Toast.makeText(getActivity(),"All Fields are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
    public boolean validate(){
        boolean valid=true;
        if (TextUtils.isEmpty(etTripName.getText().toString())
                ||TextUtils.isEmpty(etStartPoint.getText().toString())
                ||TextUtils.isEmpty(etEndPoint.getText().toString())
                ||TextUtils.isEmpty(tvAlarm.getText().toString())
                ||TextUtils.isEmpty(tvCalendar.getText().toString())){
            valid=false;
        }
        return valid;
    }

}