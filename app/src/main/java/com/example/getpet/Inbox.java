package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inbox extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.adopt));

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(Inbox.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(Inbox.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(Inbox.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(Inbox.this, Notification.class));
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