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

public class AdoptFoster extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_adopt_foster);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        //Do something
                        break;
                    case "Explore":
                        //Do something
                        break;
                    case "Adopt":
                        //Do something
                        break;
                    case "Notifications":
                        //Do something
                        break;
                    case "Profile":
                        //Do something
                        break;
                }
                return true;
            }
        });
        adoptFoster();
        findAHome();
    }

    private void adoptFoster(){
        Button adoptFoster = findViewById(R.id.adoptfoster);
        adoptFoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }

    private void findAHome(){
        Button findahome = findViewById(R.id.findahome);
        findahome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        auth.signOut();
        startActivity(new Intent(AdoptFoster.this,MainActivity.class));
        Toast.makeText(AdoptFoster.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
    }


}