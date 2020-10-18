package com.example.mytrips;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddNotesFragment extends Fragment {
    List<String> todoList;
    ArrayAdapter<String> arrayAdapter,adapter;
    EditText etNewNote;
    ListView listView;
    Button btnAddNote,btnSave;
    DatabaseReference myRef;
    TripInfo tripInfo;
    List<String> notesList;
    List<String> joined;
    String tripId,name,startPoint,endPoint,repeat,tripType,date,time;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_notes, container, false);
        todoList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.notes_list,todoList);

        joined=new ArrayList<>();

        listView=view.findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        etNewNote=view.findViewById(R.id.etNewNote);
        btnSave=view.findViewById(R.id.btnSave);
        btnAddNote=view.findViewById(R.id.btnAddNote);

        tripInfo=new TripInfo();

        myRef= FirebaseDatabase.getInstance().getReference().child("TripInfo");

        final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Bundle bundle=this.getArguments();
        tripId=bundle.getString("TripId");
        name=bundle.getString("Name");
        startPoint=bundle.getString("StartPoint");
        endPoint=bundle.getString("EndPoint");
        repeat=bundle.getString("Repetition");
        tripType=bundle.getString("TripType");
        date=bundle.getString("Date");
        time=bundle.getString("Time");


        notesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.notes_list, notesList);
        myRef.child(tripId).child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    notesList.clear();
                    for (DataSnapshot dss:snapshot.getChildren()){
                        String notes=dss.getValue(String.class);
                        notesList.add(notes);
                        adapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etNewNote.getText().toString())){
                    Toast.makeText(getActivity(),"This is empty note..Try Again!!", Toast.LENGTH_SHORT).show();
                }else {
                    todoList.add(etNewNote.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                    etNewNote.setText("");

                }


            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joined.addAll(notesList);
                joined.addAll(todoList);
                tripInfo.setName(name);
                tripInfo.setStartPoint(startPoint);
                tripInfo.setEndPoint(endPoint);
                tripInfo.setDate(date);
                tripInfo.setTime(time);
                tripInfo.setTripType(tripType);
                tripInfo.setRepetition(repeat);
                tripInfo.setStatus("Upcoming");
                tripInfo.setNotes(joined);
                tripInfo.setuId(uId);
                tripInfo.setTripId(tripId);
                myRef.child(tripId).setValue(tripInfo);
                Toast.makeText(getActivity(),"Notes added successfully",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingFragment()).commit();
            }
        });


        return view;
    }
}