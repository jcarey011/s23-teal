package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class ActiveScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_screen);

        MaterialButton reportHazardBtn = (MaterialButton) findViewById(R.id.reportHazardBtn);
        MaterialButton startTripBtn = findViewById(R.id.startTripBtn);

        startTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActiveScreen.this, DriveMode.class));
            }
        });

        reportHazardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActiveScreen.this, HazardReporting.class));
            }
        });
    }
}