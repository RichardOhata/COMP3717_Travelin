package com.example.travelin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        bundle = new Bundle();
    }

    /**
     * Switches between LoginPageFragment and SignUpPageFragment.
     *
     * @param result if true switch to LoginPage
     *                 else SignUpPage
     */
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

    /**
     * Switches to FlightInput Activity.
     */
    public void switchActivity() {
        Intent intent = new Intent(this, LandingPage.class);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
    }

    /**
     * Adds new user into Firebase Database.
     *
     * @param username username of the new user
     * @param email email of the new user
     * @param password password of the new user
     */
    public void addUser(String username, String email, String password) {
        String id = databaseReference.push().getKey();
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        assert id != null;
        Task<Void> setValueTask = databaseReference.child("Users").child(id).setValue(user);

        // If user successfully added, switch to LoginPage Fragment
        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(LoginPage.this, "Account created", Toast.LENGTH_LONG).show();
                switchFragments(false);
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginPage.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Logins user, by matching username and password
     * from the firebase database.
     *
     * @param username username to match
     * @param password password to match
     */
    public void login(String username, String password) {
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean match = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        match = true;
                        Toast.makeText(LoginPage.this, "Logged in", Toast.LENGTH_LONG).show();
                        bundle.putString("Username", user.getUsername());
                        switchActivity();
                    }
                }
                if (!match) {
                    Toast.makeText(LoginPage.this, "No such user exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}