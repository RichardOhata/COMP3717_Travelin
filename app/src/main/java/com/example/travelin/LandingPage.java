package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {
Button checkFlights;
Button checkCurrentWeather;
Button checkAnywhereWeather;
Button inputFlight;
TextView userGreeting;
Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkFlights = findViewById(R.id.flightsSeeMoreBtn);
        checkCurrentWeather = findViewById(R.id.weatherCurrentBtn);
        checkAnywhereWeather = findViewById(R.id.weatherAnywhereBtn);
        inputFlight = findViewById(R.id.inputFlight_btn);
        userGreeting = findViewById(R.id.heyThere);
        displayUserGreeting();
        checkFlights.setOnClickListener(view -> {
            Intent intent = new Intent(this, FlightDetails.class);
            intent.putExtra("Bundle", bundle);
            startActivity(intent);
        });
        checkCurrentWeather.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeatherCurrent.class);
            startActivity(intent);
        });
        checkAnywhereWeather.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeatherDetails.class);
            startActivity(intent);
        });
        inputFlight.setOnClickListener(view -> {
            Intent intent = new Intent(this, FlightInput.class);
            intent.putExtra("Bundle", bundle);
            startActivity(intent);
        });
    }

    /**
     * Displays user's username as a greeting message.
     */
    public void displayUserGreeting() {
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("Bundle");
        String userGreetingMsg = "Hey There, " + bundle.getString("Username") + "!";
        userGreeting.setText(userGreetingMsg);
    }
}