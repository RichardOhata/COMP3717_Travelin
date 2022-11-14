package com.example.travelin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;



public class FlightDetails extends AppCompatActivity {
    Bundle bundle, fragmentBundle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<Flight> flightsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getFlights();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Gets all flights associated with signed in user
     * from Firebase and then displays it in a gallery fragment.
     *
     */
    public void getFlights() {
        // Gets username in session
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("Bundle");
        String username = bundle.getString("Username");

        // Get flights in database that have the same username
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Flights").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flight flight = snapshot.getValue(Flight.class);
                    assert flight != null;
                    if (username.equals(flight.getUsername())) {
                    flightsList.add(flight);
                    }
                }
                fragmentBundle = new Bundle();
                fragmentBundle.putSerializable("Flights", flightsList);

                // Creates and directs user to FlightDetailsGalleryFragment
                Fragment gallery = new FlightDetailsGalleryFragment();
                gallery.setArguments(fragmentBundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ctnFragment, gallery);
                fragmentTransaction.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}