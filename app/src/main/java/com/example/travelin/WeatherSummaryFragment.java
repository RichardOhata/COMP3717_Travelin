package com.example.travelin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherSummaryFragment extends Fragment {
    TextView weatherSummary;
    DecimalFormat df = new DecimalFormat("#.##");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherSummaryFragment newInstance(String param1, String param2) {
        WeatherSummaryFragment fragment = new WeatherSummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Bundle bundle = this.getArguments();
//        Double windSpeed = bundle.getDouble("windSpeed");
//        weatherSummary = (TextView) getView().findViewById(R.id.checkWeatherSummary);
//        weatherSummary.setText("Wind Speed: " + df.format(windSpeed));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_summary, container, false);
    }
}