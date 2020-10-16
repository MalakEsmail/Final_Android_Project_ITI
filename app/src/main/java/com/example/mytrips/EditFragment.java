package com.example.mytrips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditFragment extends Fragment {
    EditText etTripName,etStartPoint,etEndPoint;
    Spinner repetitionSpinner,tripTypeSpinner;
    Button btnSave;
    List<String> notesList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    TextView tvDate,tvTime;
    DatabaseReference ref;
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
        listView=view.findViewById(R.id.list);



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