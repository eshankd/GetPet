package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navBar = findViewById(R.id.bottom_navbar);

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
    }
}