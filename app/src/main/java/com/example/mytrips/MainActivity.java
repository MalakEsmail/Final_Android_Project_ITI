package com.example.mytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytrips.reminder.ReminderBroadCast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    Button btnRegester, btnSignForm;
    EditText userName, pass;
    CheckBox checkpass;
    FirebaseAuth firebaseAuth;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.etUserName);
        pass = findViewById(R.id.etemail);

        btnRegester = findViewById(R.id.btnRegester);
        btnSignForm = findViewById(R.id.btnRegestrationForm);
        checkpass = findViewById(R.id.checkpass2);
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
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();

        }

    }

    public void validate() {
        String email = userName.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "All Fields are required !!", Toast.LENGTH_SHORT).show();

        } else {
            signIn(email, password);
        }
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Signed In Successfully ..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}