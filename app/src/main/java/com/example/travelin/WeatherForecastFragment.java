package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WeatherForecastFragment extends Fragment {
    RecyclerView recyclerView;
    String[] daysOfWeek, temp;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewWeatherForecast);
        daysOfWeek = getResources().getStringArray(R.array.daysOfWeek);
        System.out.println("++++++++++++++++++++++++" + daysOfWeek[0]);
        temp = getResources().getStringArray(R.array.temperature);

        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), daysOfWeek, temp);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(myRecyclerViewAdapter);
        return view;
    }


}