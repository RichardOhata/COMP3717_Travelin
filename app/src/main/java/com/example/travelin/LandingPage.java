package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {
Button checkFlights;
Button checkWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkFlights = findViewById(R.id.flightsSeeMoreBtn);
        checkWeather = findViewById(R.id.weatherSeeMoreBtn);
        checkFlights.setOnClickListener(view -> {
            Intent intent = new Intent(this, FlightDetails.class);
            startActivity(intent);
        });
        checkWeather.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeatherDetails.class);
            startActivity(intent);
        });
    }
}