package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Comments extends AppCompatActivity {


    BottomNavigationView navBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.explore));

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Comments.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Comments.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Comments.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Comments.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Comments.this, Profile.class));
                    break;
            }
            return true;
        });


    }
}