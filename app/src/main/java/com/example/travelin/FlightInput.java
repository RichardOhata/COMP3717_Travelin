package com.example.travelin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FlightInput extends AppCompatActivity {
    Bundle bundle;
    EditText flightNum, airline_name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_flight_input);

        Button submitBtn = findViewById(R.id.submitBtn);
        flightNum = findViewById(R.id.flight_num_editText);
        airline_name = findViewById(R.id.airline_name_editText);

        submitBtn.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(flightNum.getText().toString()) && !TextUtils.isEmpty(airline_name.getText().toString())) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                databaseReference.child("Flights-List").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean match = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String readAirlineName = snapshot.child("airline").child("name").getValue(String.class);
                            String readFlightNum = snapshot.child("flight").child("number").getValue(String.class);
                            if (flightNum.getText().toString().equals(readFlightNum) && airline_name.getText().toString().equals(readAirlineName)) {
                                match = true;
                            }
                        }
                        if (!match) {
                            Toast.makeText(FlightInput.this, "No such flight exists", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = getIntent();
                            bundle = intent.getBundleExtra("Bundle");
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            String id = databaseReference.push().getKey();
                            Flight flight = new Flight(Integer.parseInt(flightNum.getText().toString()), airline_name.getText().toString(), bundle.getString("Username"));
                            assert id != null;
                            Task<Void> setValueTask = databaseReference.child("Flights").child(id).setValue(flight);
                            Toast.makeText(FlightInput.this, "Flight has been added", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            } else {
                Toast.makeText(FlightInput.this, "Enter flight number and airline name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}