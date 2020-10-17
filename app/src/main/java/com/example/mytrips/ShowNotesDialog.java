package com.example.mytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowNotesDialog extends DialogFragment {
    ListView showNotesList;
    ArrayAdapter<String> showNotesArrayAdapter;
    List<String> notesList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setTitle("My Dialog Title");
        View view =inflater.inflate(R.layout.show_notes_dialog,container,false);
        showNotesList=view.findViewById(R.id.show_notes_list);
        notesList =new ArrayList<>();
        notesList.add("this is note one");
        notesList.add("this is note two");
     //   getDialog().setTitle("Notes");

        showNotesArrayAdapter =new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,notesList);
        showNotesList.setAdapter(showNotesArrayAdapter);
        return view;
    }
}
