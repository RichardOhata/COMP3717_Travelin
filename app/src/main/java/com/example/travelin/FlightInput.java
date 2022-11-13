package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

public class FlightInput extends AppCompatActivity {
    Bundle bundle;
    EditText flightNum, airline_name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String url = "https://app.goflightlabs.com/flights?access_key";
    private final String appid = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiY2UyNjVlNjAzZTBhYjdkMjEwOWU4MWE1MTU5Y2VkNWNhYTAxZTlkZGRjNDcxNzVmZmE2ODRjOGM0MjQ4NjIzZWViNDYyZDI3ZWUyY2E5N2QiLCJpYXQiOjE2Njc4NDQwMDQsIm5iZiI6MTY2Nzg0NDAwNCwiZXhwIjoxNjk5MzgwMDA0LCJzdWIiOiIxNzMyNCIsInNjb3BlcyI6W119.Si0XteCDaR2RNZExlf3431TDq0L8gwSyppuGVhIDJpi5Lm_YWVimrbVKHGJiDtIcXmaEdteApum3zYFs8X8WIQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_flight_input);

        Button submitBtn = findViewById(R.id.submitBtn);
        flightNum = findViewById(R.id.flight_num_editText);
        airline_name = findViewById(R.id.airline_name_editText);
        submitBtn.setOnClickListener(view -> {
            String tempURL = url + "=" + appid + "&flight_number=" + flightNum.getText().toString() + "&airline_name=" + airline_name.getText().toString();
            if (!TextUtils.isEmpty(flightNum.getText().toString()) && !TextUtils.isEmpty(airline_name.getText().toString())) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(tempURL);
                this.finish();
            } else {
                Toast.makeText(FlightInput.this, "Enter flight number and airline name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Gets the user input from the editTexts and checks in the FlightLabs API for
     * matching flights. If found, store user input in Firebase.
     */
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(FlightInput.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0], null, response -> {
                try {
                    response.getJSONObject(0);
                    Intent intent = getIntent();
                    bundle = intent.getBundleExtra("Bundle");
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();
                    String id = databaseReference.push().getKey();
                    Flight flight = new Flight(Integer.parseInt(flightNum.getText().toString()), airline_name.getText().toString(), bundle.getString("Username"));
                    assert id != null;
                    Task<Void> setValueTask = databaseReference.child("Flights").child(id).setValue(flight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FlightInput.this, "Flight does not exist", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }
}