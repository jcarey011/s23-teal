package com.example.drivesafe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpActivity extends AppCompatActivity {

    EditText firstname, lastname, email, apassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        apassword = findViewById(R.id.apassword);
        register = findViewById(R.id.registerbtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstname.getText().toString();
                String last = lastname.getText().toString();
                String userEmail = email.getText().toString();
                String pass = apassword.getText().toString();
                User user = new User(first, last, userEmail, pass);

                new SignUpTask().execute(user);
            }
        });
    }

    private class SignUpTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... users) {
            User user = users[0];
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

            try {
                Call<Void> call = apiService.registerUser(user);
                Response<Void> response = call.execute();
                Log.d("SignUpActivity", "API Response Code: " + response.code());
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SignUpActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
