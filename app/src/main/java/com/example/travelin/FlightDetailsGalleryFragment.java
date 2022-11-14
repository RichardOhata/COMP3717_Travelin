package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FlightDetailsGalleryFragment extends Fragment implements FlightItemClickListener{
    RecyclerView recyclerView;
    Flight[] flightsArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        ArrayList<Flight> flightsList = (ArrayList<Flight>) getArguments().getSerializable("Flights");
        flightsArray = new Flight[flightsList.size()];
        flightsArray = flightsList.toArray(flightsArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_details_gallery, container, false);
        recyclerView = view.findViewById(R.id.flightRecyclerView);
        MyFlightRecyclerViewAdapter myFlightRecyclerViewAdapter = new MyFlightRecyclerViewAdapter(getActivity(), flightsArray);
        myFlightRecyclerViewAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(myFlightRecyclerViewAdapter);

        return view;
    }

    /**
     * If user clicks on a flight, directs them to a fragment of the flight
     * showing details about that flight.
     *
     * @param view view
     * @param position position of element in the gallery
     */
    @Override
    public void onClick(View view, int position) {
            FlightDetailsFragment flightDetailsFragment = new FlightDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("FlightNum", flightsArray[position].get_flight_num());
            bundle.putString("AirlineName", flightsArray[position].get_airline_name());
            flightDetailsFragment.setArguments(bundle);
            // Directs user to FlightDetailsFragment
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ctnFragment, flightDetailsFragment).commit();
    }
}