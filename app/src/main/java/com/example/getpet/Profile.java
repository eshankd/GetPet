package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.profile));

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(Profile.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(Profile.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(Profile.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(Profile.this, Notification.class));
                        break;
                    case "Profile":
                        //Do Nothing
                        break;
                }
                return true;
            }
        });

        signOut();
    }

    private void signOut(){
        Button signOutBTN = findViewById(R.id.signOut);
        signOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
                Toast.makeText(Profile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}