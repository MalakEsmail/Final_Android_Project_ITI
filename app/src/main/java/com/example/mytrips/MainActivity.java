package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    Button btnRegester, btnSignForm;
    EditText userName, pass;
    CheckBox checkpass;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.etUserName);
        pass = findViewById(R.id.etemail);

        btnRegester = findViewById(R.id.btnRegester);
        btnSignForm = findViewById(R.id.btnRegestrationForm);
        checkpass = findViewById(R.id.checkpass2);

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
    }

    public boolean validate() {
        boolean valid = true;
        if (TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError("Empty field not allowed!");
            valid = false;
        }
        if (TextUtils.isEmpty(pass.getText().toString())) {
            pass.setError("Empty field not allowed!");
            valid = false;
        }
        return valid;
    }
}