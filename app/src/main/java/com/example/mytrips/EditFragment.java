package com.example.mytrips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class EditFragment extends Fragment {
    EditText etTripName,etStartPoint,etEndPoint;
    Spinner repetitionSpinner,tripTypeSpinner;
    Button btnSave;
    TextView tvDate,tvTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_edit, container, false);
        etTripName=view.findViewById(R.id.etTripName);
        etStartPoint=view.findViewById(R.id.etStartPoint);
        etEndPoint=view.findViewById(R.id.etEndPoint);
        repetitionSpinner=view.findViewById(R.id.repetitionSpinner);
        tripTypeSpinner=view.findViewById(R.id.tripTypeSpinner);
        tvDate=view.findViewById(R.id.tvDate);
        tvTime=view.findViewById(R.id.tvTime);


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
        return view;
    }
}