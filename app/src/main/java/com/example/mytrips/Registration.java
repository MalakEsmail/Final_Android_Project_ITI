package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

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

public class Registration extends AppCompatActivity {

    Button btnRegistrationForm;
    CheckBox checkPass2;
    EditText etEmail,etUser, etPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etPassword=findViewById(R.id.etPassword);
        checkPass2=findViewById(R.id.checkpass2);
        etUser=findViewById(R.id.etUserName);
        etEmail=findViewById(R.id.etemail);
        btnRegistrationForm=findViewById(R.id.btnRegestrationForm);

        checkPass2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){

                    // show password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Log.i("checker", "true");
                }

                else{
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

    }
    public boolean validate(){
        boolean valid=true;
        if (TextUtils.isEmpty(etUser.getText().toString())){
            etUser.setError("Empty field not allowed!");
            valid=false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("Empty field not allowed!");
            valid=false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError("Empty field not allowed!");
            valid=false;
        }
        return valid;
    }
}