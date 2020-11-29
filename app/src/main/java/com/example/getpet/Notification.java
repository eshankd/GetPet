package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Notification extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.notifications));

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Notification.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Notification.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Notification.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    //Do something
                    break;
                case "Profile":
                    startActivity(new Intent(Notification.this, Profile.class));
                    break;
            }
            return true;
        });


    }
}