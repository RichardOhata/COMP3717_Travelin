package com.example.travelin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class FlightDetails extends AppCompatActivity {
    Bundle bundle, fragmentBundle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String url = "https://app.goflightlabs.com/flights?access_key";
    private final String appid = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiY2UyNjVlNjAzZTBhYjdkMjEwOWU4MWE1MTU5Y2VkNWNhYTAxZTlkZGRjNDcxNzVmZmE2ODRjOGM0MjQ4NjIzZWViNDYyZDI3ZWUyY2E5N2QiLCJpYXQiOjE2Njc4NDQwMDQsIm5iZiI6MTY2Nzg0NDAwNCwiZXhwIjoxNjk5MzgwMDA0LCJzdWIiOiIxNzMyNCIsInNjb3BlcyI6W119.Si0XteCDaR2RNZExlf3431TDq0L8gwSyppuGVhIDJpi5Lm_YWVimrbVKHGJiDtIcXmaEdteApum3zYFs8X8WIQ";
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
     * from Firebase.
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
                // For each Flight, get data from it through API
                for (Flight flight : flightsList) {
                    String tempURL = url + "=" + appid + "&flight_number=" + flight.get_flight_num() + "&airline_name=" + flight.get_airline_name();
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    try {
                        String str_result = runner.execute(tempURL).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                fragmentBundle = new Bundle();
                fragmentBundle.putSerializable("Flights", flightsList);

                Fragment gallery = new FlightDetailsGallery();
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

    /**
     * Gets the data from each flight through API.
     *
     */
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(FlightDetails.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
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