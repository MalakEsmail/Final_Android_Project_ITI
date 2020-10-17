package com.example.mytrips.reminder;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mytrips.R;

public class Dialog extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("test","dialog ");

        CustomDialogFragment customDialogFragment = new CustomDialogFragment("","work", "giza", "mansoura");
        customDialogFragment.show(getSupportFragmentManager(), "CUSTOM");


    }
}