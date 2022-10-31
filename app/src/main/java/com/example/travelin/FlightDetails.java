package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import java.util.concurrent.atomic.AtomicBoolean;

public class FlightDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AtomicBoolean isFragment1 = new AtomicBoolean(true);
        Fragment flightDetails1 = new FlightDetailsFragment();
        Fragment flightDetails2 = new FlightDetailsFragment2();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flightDetailsFragmentContainerView, flightDetails1);
        fragmentTransaction.commit();

        Button btnFirst = findViewById(R.id.more_details_btn);
        btnFirst.setOnClickListener(view -> {
            if (isFragment1.get()) {
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.flightDetailsFragmentContainerView, flightDetails2);
                fragmentTransaction1.commit();
               isFragment1.set(false);
            } else {
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.replace(R.id.flightDetailsFragmentContainerView, flightDetails1);
                fragmentTransaction2.commit();
                isFragment1.set(true);
            }

        });


    }
}