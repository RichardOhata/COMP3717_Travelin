package com.example.travelin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherDetails extends AppCompatActivity {
    TextView city;
    TextView weatherTemp;
    TextView weatherCondition;
//    double windSpeedWeatherSum;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean fineLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                                Boolean coarseLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,false);
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    // Precise location access granted.
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    // Only approximate location access granted.
                                } else {
                                    // No location access granted.
                                }
                            }
                    );
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        double currentLatitude = lastKnownLocation.getLatitude();
        double currentLongtitude = lastKnownLocation.getLongitude();
        System.out.println("curLat: " + currentLatitude);
        System.out.println("curLong: "+ currentLongtitude);


        city = findViewById(R.id.city);
        weatherTemp = findViewById(R.id.weatherTemp);
        weatherCondition = findViewById(R.id.weatherCondition);
        String tempURL = url + "?lat=" + currentLatitude + "&lon=" + currentLongtitude + "&appid=" + appid;

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(tempURL);
//
//        Bundle bundle = new Bundle();
//        bundle.putDouble("windSpeed", windSpeedWeatherSum);
//        System.out.println("HOO: " + windSpeedWeatherSum);

        Fragment weatherSummary = new WeatherSummaryFragment();
//        weatherSummary.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.weatherSummary, weatherSummary);
        fragmentTransaction.commit();

        Fragment weatherForecast = new WeatherForecastFragment();
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.weatherForecast, weatherForecast);
        fragmentTransaction1.commit();

    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(WeatherDetails.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String cityName = response.getString("name");
                        city.setText(cityName);

                        JSONObject jsonObjectMain = response.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        weatherTemp.setText(df.format(temp) + " degree Celsius");

                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        weatherCondition.setText(toTitleCase(description));

//                        JSONObject jsonObjectWind = response.getJSONObject("wind");
//                        double windSpeed = jsonObjectWind.getDouble("speed");
//                        windSpeedWeatherSum = windSpeed;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(WeatherDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}