package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FlightDetailsGallery extends Fragment {
    RecyclerView recyclerView;
    Flight[] flights;
    Flight[] flightsListAsArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Flight> flightsList = (ArrayList<Flight>) getArguments().getSerializable("Flights");
        flightsListAsArray = new Flight[flightsList.size()];
        flightsListAsArray = flightsList.toArray(flightsListAsArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_details_gallery, container, false);
        recyclerView = view.findViewById(R.id.flightRecyclerView);
        flights = flightsListAsArray;
        MyFlightRecyclerViewAdapter myFlightRecyclerViewAdapter = new MyFlightRecyclerViewAdapter(getActivity(), flights);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(myFlightRecyclerViewAdapter);

        return view;
    }
}