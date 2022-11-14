package com.example.travelin;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class FlightDetailsFragment extends Fragment {
    private final String url = "https://app.goflightlabs.com/flights?access_key";
    private final String appid = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiY2UyNjVlNjAzZTBhYjdkMjEwOWU4MWE1MTU5Y2VkNWNhYTAxZTlkZGRjNDcxNzVmZmE2ODRjOGM0MjQ4NjIzZWViNDYyZDI3ZWUyY2E5N2QiLCJpYXQiOjE2Njc4NDQwMDQsIm5iZiI6MTY2Nzg0NDAwNCwiZXhwIjoxNjk5MzgwMDA0LCJzdWIiOiIxNzMyNCIsInNjb3BlcyI6W119.Si0XteCDaR2RNZExlf3431TDq0L8gwSyppuGVhIDJpi5Lm_YWVimrbVKHGJiDtIcXmaEdteApum3zYFs8X8WIQ";
    TextView title, flightDate, flightStatus, scheduledDeparture, scheduledArrival;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        int flightNum =  getArguments().getInt("FlightNum");
        String airlineName = getArguments().getString("AirlineName");

        // Get flight information from API
        String tempURL = url + "=" + appid + "&flight_number=" + flightNum + "&airline_name=" + airlineName;
        AsyncTaskRunner runner = new AsyncTaskRunner();
        try {
            String str_result = runner.execute(tempURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        title = view.findViewById(R.id.flight_title);
        flightDate = view.findViewById(R.id.flight_date);
        flightStatus = view.findViewById(R.id.flight_status);
        return view;
    }

    /**
     * Gets the data for the flight through API
     *
     */
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        JSONObject flightDeparture = jsonObject.getJSONObject("departure");
                        JSONObject flightArrival = jsonObject.getJSONObject("arrival");
                        String departureTimeZone = flightDeparture.getString("scheduled");
                        String arrivalTimeZone = flightDeparture.getString("scheduled");


                        String date = "Flight Date: " + jsonObject.getString("flight_date");
                        flightDate.setText(date);
                        String status = "Flight Status: " + jsonObject.getString("flight_status");
                        flightStatus.setText(status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}