package com.example.drivesafe;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginbtn, createbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        createbtn = findViewById(R.id.createbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = username.getText().toString();
                String pass = password.getText().toString();
                User user = new User(null, null, userEmail, pass);

                new LoginTask().execute(user);
            }
        });

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private class LoginTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            User user = users[0];
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            try {
                Call<Void> call = apiService.loginUser(user);
                Response<Void> response = call.execute();
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

            @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                // Save email to SharedPreferences bc it needs to be used in HazardReporting.java later
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", username.getText().toString());
                editor.apply();
                // Log the saved email
                Log.d("MainActivity", "Saved email: " + username.getText().toString());

                Intent intent = new Intent(MainActivity.this, ActiveScreen.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
