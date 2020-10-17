package com.example.mytrips;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegistrationFragment extends Fragment {

    Button btnRegistrationForm;
    CheckBox checkPass2;
    EditText etEmail, etUser, etPassword;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registration, container, false);
        etPassword = view.findViewById(R.id.etPassword);
        checkPass2 = view.findViewById(R.id.checkpass2);
        etUser =view.findViewById(R.id.etUserName);
        etEmail = view.findViewById(R.id.etemail);
        btnRegistrationForm = view.findViewById(R.id.btnRegestrationForm);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        checkPass2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {

                    // show password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Log.i("checker", "true");
                } else {
                    Log.i("checker", "false");

                    // hide password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        btnRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        return view;

    }


    public void validate() {

        if (TextUtils.isEmpty(etUser.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {

            Toast.makeText(getActivity(), "All Fields are required !!", Toast.LENGTH_SHORT).show();

        } else {
            if(etPassword.getText().toString().length() <6){
                Toast.makeText(getActivity(), "password Should be 6+ !! ", Toast.LENGTH_SHORT).show();
            }else{
                createUserAccount();

            }

        }


    }

    private void createUserAccount() {
        //get user data
        final String name = etUser.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        //  create user account
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // save user to db
                    String uid = auth.getCurrentUser().getUid();
                    saveUserData(uid, name, email, password);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Try Again !!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveUserData(String uid, String name, String email, String password) {

        //1 -save data
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("password", password);

        reference.child("users").child(uid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    etEmail.setText("");
                    etUser.setText("");
                    etPassword.setText("");
                    Toast.makeText(getActivity(), "Account Created Successfully ..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "Try again !! ", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}