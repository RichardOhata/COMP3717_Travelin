package com.example.travelin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;

public class WeatherCurrent extends AppCompatActivity {
    TextView city;
    TextView weatherTemp;
    TextView weatherCondition;
    TextView weatherSummary;
    TextView weatherDetails;
    ImageView weatherIcon;
    GridLayout currentWeatherLayout;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_current);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentWeatherLayout = (GridLayout) findViewById(R.id.currentWeatherLayout);
        city = findViewById(R.id.city);
        weatherTemp = findViewById(R.id.weatherTemp);
        weatherCondition = findViewById(R.id.weatherCondition);

        Date currentTime = Calendar.getInstance().getTime();

        if(currentTime.getHours() <= 16){
            currentWeatherLayout.setBackgroundResource(R.drawable.morning);
        } else if (currentTime.getHours() > 16 && currentTime.getHours() < 20) {
            currentWeatherLayout.setBackgroundResource(R.drawable.evening);
            city.setTextColor(Color.parseColor("#FFFFFF"));
            weatherTemp.setTextColor(Color.parseColor("#FFFFFF"));
            weatherCondition.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (currentTime.getHours() >= 20) {
            currentWeatherLayout.setBackgroundResource(R.drawable.night);
            city.setTextColor(Color.parseColor("#FFFFFF"));
            weatherTemp.setTextColor(Color.parseColor("#FFFFFF"));
            weatherCondition.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            currentWeatherLayout.setBackgroundResource(R.drawable.morning);
        }

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

        weatherSummary = findViewById(R.id.checkWeatherSummary);
        weatherDetails = findViewById(R.id.weatherDetails);
        weatherIcon = findViewById(R.id.weatherIcon);
        String tempURL = url + "?lat=" + currentLatitude + "&lon=" + currentLongtitude + "&appid=" + appid;


        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(tempURL);
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(WeatherCurrent.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String cityName = response.getString("name");
                        city.setText(cityName);

                        JSONObject jsonObjectMain = response.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        String weatherTempMsg = df.format(temp) + " degree Celsius";
                        weatherTemp.setText(weatherTempMsg);

                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        weatherCondition.setText(toTitleCase(description));
                        if (description.toLowerCase().contains("sun")) {
                            weatherIcon.setImageResource(R.drawable.sun_weather_icon_150657);
                        } else if (description.toLowerCase().contains("cloud")) {
                            weatherIcon.setImageResource(R.drawable.cloudy_weather_icon_150660);
                        } else if (description.toLowerCase().contains("rain")) {
                            weatherIcon.setImageResource(R.drawable.rain_weather_icon_150662);
                        } else if (description.toLowerCase().contains("snow")) {
                            weatherIcon.setImageResource(R.drawable.snowy_weather_icon_150655);
                        } else {
                            weatherIcon.setImageResource(R.drawable.sunny_weather_icon_150663);
                        }
                        JSONObject jsonObjectWind = response.getJSONObject("wind");
                        double windSpeed = jsonObjectWind.getDouble("speed");
                        String windSpeedMsg = howFastIsTheWindBlowing(windSpeed) + "\n" + "Wind Speed: " + windSpeed + "m/s";
                        weatherSummary.setText(windSpeedMsg);

                        JSONObject jsonObjectWeatherDetails = response.getJSONObject("main");
                        double feels_like = jsonObjectWeatherDetails.getDouble("feels_like") - 273.15;
                        double temp_min = jsonObjectWeatherDetails.getDouble("temp_min") - 273.15;
                        double temp_max = jsonObjectWeatherDetails.getDouble("temp_max") - 273.15;
                        double pressure = jsonObjectWeatherDetails.getDouble("pressure");
                        double humidity = jsonObjectWeatherDetails.getDouble("humidity");
                        String weatherDetailsMsg = "Feels like: " + df.format(feels_like) + " degree Celsius\n\nMax temperature: " + df.format(temp_max) + " degree Celsius\n\n" +
                                "Min temperature: " + df.format(temp_min) + " degree Celsius\n\n" + "Pressure: " + pressure + "\n\nHumidity: " + humidity;
                        weatherDetails.setText(weatherDetailsMsg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(WeatherCurrent.this, error.toString(), Toast.LENGTH_SHORT).show();
                }

            });
            queue.add(request);
            return null;

        }
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String howFastIsTheWindBlowing(double windspeed) {
        String windDescription = "Wind Speed";
        if (windspeed < 1.5) {
            windDescription = "Light Air";
        } else if (windspeed > 1.5 && windspeed < 3) {
            windDescription = "Light Breeze";
        } else if (windspeed > 3 && windspeed < 5) {
            windDescription = "Gentle breeze";
        } else if (windspeed > 5 && windspeed < 8) {
            windDescription = "Moderate breeze";
        } else if (windspeed > 8 && windspeed < 10.5) {
            windDescription = "Fresh breeze";
        } else if (windspeed > 10.5 && windspeed < 13.5) {
            windDescription = "Strong breeze";
        } else if (windspeed > 13.5 && windspeed < 16.5) {
            windDescription = "Moderate gale";
        } else if (windspeed > 16.5 && windspeed < 20) {
            windDescription = "Fresh gale";
        } else if (windspeed > 20 && windspeed < 23.5) {
            windDescription = "Strong gale";
        } else if (windspeed > 23.5 && windspeed < 27.5) {
            windDescription = "Whole gale";
        } else if (windspeed > 27.5 && windspeed < 31.5) {
            windDescription = "Storm";
        } else if (windspeed > 31.5) {
            windDescription = "Hurricane";
        }
        return windDescription;
    }
}