package com.example.drivesafeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText aPassword;
    EditText cPassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        aPassword = findViewById(R.id.apassword);
        cPassword = findViewById(R.id.cpassword);

       register =  findViewById(R.id.registerbtn);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(firstName)){
            Toast t = Toast.makeText(this, "First name required to register", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(lastName)) {
            lastName.setError("Last name required to register");
        }

        if(isEmail(email) == false) {
            email.setError("Enter valid email address");
        }

        if(aPassword != cPassword){
            cPassword.setError("Passwords do not match");
        }
    }
}