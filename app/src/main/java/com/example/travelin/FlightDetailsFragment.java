package com.example.travelin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class FlightDetailsFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TextView title, flightDate, flightStatus, flight_Num, airline_Name, delay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        int flightNum =  getArguments().getInt("FlightNum");
        String airlineName = getArguments().getString("AirlineName");


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Flights-List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String readAirlineName = snapshot.child("airline").child("name").getValue(String.class);
                    String readFlightNum = snapshot.child("flight").child("number").getValue(String.class);
                    if (readAirlineName.equals(airlineName) && Integer.parseInt(readFlightNum) == flightNum) {
                        long departureDelay, arrivalDelay;
                        if (snapshot.child("departure").child("delay").getValue(Long.class) == null) {
                            departureDelay = 0;
                        } else {
                            departureDelay = snapshot.child("departure").child("delay").getValue(Long.class);
                        }
                        if (snapshot.child("arrival").child("delay").getValue(Long.class) == null) {
                            arrivalDelay = 0;
                        } else {
                            arrivalDelay = snapshot.child("arrival").child("delay").getValue(Long.class);
                        }
                        calculateDelay(departureDelay, arrivalDelay);

                        String flightDateData = "Flight Date: " + snapshot.child("flight_date").getValue(String.class);
                        String flightStatusData = "Flight Status: " + snapshot.child("flight_status").getValue(String.class);
                        String titleData = snapshot.child("departure").child("iata").getValue(String.class) + " to " +
                                snapshot.child("arrival").child("iata").getValue(String.class);
                        String airlineNameData = "Airline name: " + airlineName;
                        String flightNumData = "Flight number: " + flightNum;
                        flightDate.setText(flightDateData);
                        flightStatus.setText(flightStatusData);
                        title.setText(titleData);
                        airline_Name.setText(airlineNameData);
                        flight_Num.setText(flightNumData);
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        title = view.findViewById(R.id.flight_title);
        flightDate = view.findViewById(R.id.flight_date);
        flightStatus = view.findViewById(R.id.flight_status);
        airline_Name = view.findViewById(R.id.airline_name);
        flight_Num = view.findViewById(R.id.flight_num);
        delay = view.findViewById(R.id.delay_textView);
        return view;
    }

    /**
     * Calculates whether the risk of delay in a flight is
     * high or low.
     * @param departureDelay delay in the departure of the flight
     * @param arrivalDelay delay in the arrival of the flight
     */
    public void calculateDelay(long departureDelay, long arrivalDelay) {
        final int HIGH_DELAY = 60;
        final int MODERATE_DELAY = 25;
        final int MILD_DELAY = 1;
        long totalDelayTime = departureDelay + arrivalDelay;
        String delayMessage;
        if (totalDelayTime >= HIGH_DELAY) {
            delayMessage = "Risk of Delay: \n\nHIGH";
        } else if (totalDelayTime >= MODERATE_DELAY) {
            delayMessage = "Risk of Delay: \n\nMODERATE";
        } else if (totalDelayTime >= MILD_DELAY) {
            delayMessage = "Risk of Delay: \n\nMILD";
        } else {
            delayMessage = "Risk of Delay: \n\nNONE";
        }
        delay.setText(delayMessage);
    }
}