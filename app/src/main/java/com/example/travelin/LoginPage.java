package com.example.travelin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public void switchFragments(boolean result) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (result) {
                SignUpPageFragment signInPageFragment = new SignUpPageFragment();
                fragmentTransaction.replace(R.id.loginPageFragmentContainerView, signInPageFragment);
        } else {
                LoginPageFragment loginPageFragment = new LoginPageFragment();
                fragmentTransaction.replace(R.id.loginPageFragmentContainerView, loginPageFragment);

        }
        fragmentTransaction.commit();
    }

    public void switchActivity() {
        Intent intent = new Intent(this, FlightInput.class);
        startActivity(intent);
    }
}