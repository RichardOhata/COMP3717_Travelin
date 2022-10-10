package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class FlightInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_input);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FlightDetails.class);
            startActivity(intent);
        });
    }
}