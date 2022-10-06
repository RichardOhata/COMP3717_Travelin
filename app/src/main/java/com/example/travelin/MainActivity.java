package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signInBtn = findViewById(R.id.sign_in_btn);
        signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FlightInput.class);
            startActivity(intent);
        });
    }
}