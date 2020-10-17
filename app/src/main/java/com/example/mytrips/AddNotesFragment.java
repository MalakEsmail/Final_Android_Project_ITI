package com.example.mytrips;

import android.graphics.Paint;
import android.os.Bundle;

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

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AddNotesFragment extends Fragment {
    List<String> todoList;
    ArrayAdapter<String> arrayAdapter;
    EditText etNewNote;
    ListView listView;
    Button btnAddNote,btnSave;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_notes, container, false);
        todoList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.notes_list,todoList);
        listView=view.findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        etNewNote=view.findViewById(R.id.etNewNote);
        btnSave=view.findViewById(R.id.btnSave);
        btnAddNote=view.findViewById(R.id.btnAddNote);
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


        return view;
    }
}