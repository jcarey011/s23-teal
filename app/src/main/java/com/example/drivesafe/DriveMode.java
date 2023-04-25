package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriveMode extends AppCompatActivity {

    private Button reportHazardBtn;
    private Button endTripBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_mode);

        reportHazardBtn = findViewById(R.id.reportHazardBtn);
        reportHazardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriveMode.this, HazardReporting.class);
                startActivity(intent);
            }
        });

        endTripBtn = findViewById(R.id.endTripBtn);
        endTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}