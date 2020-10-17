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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {

    Button btnRegester, btnSignForm;
    EditText userName, pass;
    CheckBox checkpass;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_log_in, container, false);
        userName = view.findViewById(R.id.etUserName);
        pass = view.findViewById(R.id.etemail);

        btnRegester = view.findViewById(R.id.btnRegester);
        btnSignForm = view.findViewById(R.id.btnRegestrationForm);
        checkpass = view.findViewById(R.id.checkpass2);
        firebaseAuth = FirebaseAuth.getInstance();

        checkpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {

                    // show password
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Log.i("checker", "true");
                } else {
                    Log.i("checker", "false");

                    // hide password
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        btnSignForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        btnRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new RegistrationFragment()).commit();
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();

        }
        return view;

    }

    public void validate() {
        String email = userName.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(getActivity(), "All Fields are required !!", Toast.LENGTH_SHORT).show();

        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Signed In Successfully ..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                } else {
                    Toast.makeText(getActivity(), "Try Again !!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}