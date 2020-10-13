package com.example.mytrips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class AddFragment extends Fragment {
    EditText etTripName,etStartPoint,etEndPoint;
    Spinner repetitionSpinner,tripTypeSpinner;
    Button btnCalendar,btnAlarm,btnAdd;
    TextView tvCalendar,tvAlarm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add, container, false);
        etTripName=view.findViewById(R.id.etTripName);
        etStartPoint=view.findViewById(R.id.etStartPoint);
        etEndPoint=view.findViewById(R.id.etEndPoint);
        repetitionSpinner=view.findViewById(R.id.repetitionSpinner);
        tripTypeSpinner=view.findViewById(R.id.tripTypeSpinner);
        btnAlarm=view.findViewById(R.id.btnAlarm);
        btnCalendar=view.findViewById(R.id.btnCalendar);
        btnAdd=view.findViewById(R.id.btnAdd);
        tvCalendar=view.findViewById(R.id.tvCalendar);
        tvAlarm=view.findViewById(R.id.tvAlarm);


        //Calendar btn
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        tvCalendar.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        //Alarm btn
        Calendar c=Calendar.getInstance();
        final int hour=calendar.get(c.HOUR_OF_DAY);
        final int minute=calendar.get(c.MINUTE);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time=hour+":"+minute;
                        tvAlarm.setText(time);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });
        return view;
    }
}