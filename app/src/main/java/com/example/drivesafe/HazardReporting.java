package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class HazardReporting extends AppCompatActivity {

    private String driverId;
    private EditText streetEditText, cityEditText, stateEditText, zipEditText;
    private Spinner hazardSpinner;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_reporting);

        hazardSpinner = findViewById(R.id.dropdown);
        submitBtn = findViewById(R.id.submitBtn);
        streetEditText = findViewById(R.id.street);
        cityEditText = findViewById(R.id.city);
        stateEditText = findViewById(R.id.state);
        zipEditText = findViewById(R.id.zip);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        driverId = sharedPreferences.getString("email", "");
        // Log the retrieved email
        Log.d("HazardReporting", "Retrieved email: " + driverId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hazard_types_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hazardSpinner.setAdapter(adapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hazardType = hazardSpinner.getSelectedItem().toString();
                String photoFilename = ""; // Add photo filename handling
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String location = streetEditText.getText().toString() + ", " + cityEditText.getText().toString() + ", " + stateEditText.getText().toString() + " " + zipEditText.getText().toString();

                HazardReport hazardReport = new HazardReport(driverId, hazardType, photoFilename, currentDate, currentTime, location);
                new ReportHazardTask().execute(hazardReport);
            }
        });
    }
    private class ReportHazardTask extends AsyncTask<HazardReport, Void, Boolean> {
        @Override
        protected Boolean doInBackground(HazardReport... hazardReports) {
            HazardReport hazardReport = hazardReports[0];

            // Log the hazard report
            Log.d("ReportHazardTask", "Hazard report: " + hazardReport.toString());

            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            try {
                Call<Void> call = apiService.reportHazard(hazardReport);
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
                Toast.makeText(HazardReporting.this, "Hazard reported successfully", Toast.LENGTH_SHORT).show();
                // Redirect to another activity or reset input fields
            } else {
                Toast.makeText(HazardReporting.this, "Error reporting hazard", Toast.LENGTH_SHORT).show();
            }
        }
    }
}