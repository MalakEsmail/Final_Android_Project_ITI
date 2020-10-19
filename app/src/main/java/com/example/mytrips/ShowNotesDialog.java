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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowNotesDialog extends DialogFragment {
    ListView showNotesList;
    List<String> notesList;
    String tripId;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference myRef;

    public ShowNotesDialog(String tripId) {
        this.tripId = tripId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setTitle("My Dialog Title");
        View view =inflater.inflate(R.layout.show_notes_dialog,container,false);
        showNotesList=view.findViewById(R.id.show_notes_list);

        myRef= FirebaseDatabase.getInstance().getReference().child("TripInfo").child(tripId).child("notes");
        notesList = new ArrayList<>();
        arrayAdapter =new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,notesList);
        showNotesList.setAdapter(arrayAdapter);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                if (notesList.isEmpty()){
                    notesList.add("There are no notes");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}
