package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Storyboard extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);

        navBar = findViewById(R.id.bottom_navbar);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        break;
                    case "Explore":
                        startActivity(new Intent(Storyboard.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(Storyboard.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(Storyboard.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(Storyboard.this, Profile.class));
                        break;
                }
                return true;
            }
        });
    }
}