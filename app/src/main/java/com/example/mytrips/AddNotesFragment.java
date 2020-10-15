package com.example.mytrips;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddNotesFragment extends Fragment {
    List<String> todoList;
    ArrayAdapter<String> arrayAdapter;
    EditText etNewNote;
    ListView listView;
    TextView tvNote;
    Button btnAddNote;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_notes, container, false);
        todoList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.notes_list,todoList);
        listView=view.findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        tvNote=view.findViewById(R.id.tvNote);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view;
                textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
        etNewNote=view.findViewById(R.id.etNewNote);
        btnAddNote=view.findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoList.add(etNewNote.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                etNewNote.setText("");
            }
        });

        return view;
    }
}