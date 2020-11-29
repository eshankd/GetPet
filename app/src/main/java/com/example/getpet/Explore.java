package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Explore extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.explore));

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Explore.this, Storyboard.class));
                    break;
                case "Explore":
                    //Do something
                    break;
                case "Adopt":
                    startActivity(new Intent(Explore.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Explore.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Explore.this, Profile.class));
                    break;
            }
            return true;
        });
    }

}