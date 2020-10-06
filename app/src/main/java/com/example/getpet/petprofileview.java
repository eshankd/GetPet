package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.grpc.okhttp.internal.Platform;

public class petprofileview extends AppCompatActivity {

    BottomNavigationView navBar;

    private String transferredName;
    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;

    private TextView petName;
    private TextView petAge;
    private TextView petBreed;
    private TextView petGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileview);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petProfilePetAge);
        petBreed = findViewById(R.id.petProfilePetBreed);
        petGender = findViewById(R.id.petProfilePetGender);
//        petDesc = findViewById(R.id.petDescription);

        transferredName = getIntent().getStringExtra("petName");
        transferredAge = getIntent().getIntExtra("petAge", 0);
        transferredBreed = getIntent().getStringExtra("petBreed");
        transferredGender = getIntent().getStringExtra("petGender");

        petName.setText(transferredName);
        petAge.setText(Integer.toString(transferredAge));
        petBreed.setText(transferredBreed);
        petGender.setText(transferredGender);


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(petprofileview.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(petprofileview.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(petprofileview.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(petprofileview.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(petprofileview.this, Profile.class));
                        break;
                }
                return true;
            }
        });

        virtuallyAdopt();
    }

    private void virtuallyAdopt(){
        Button virtuallyadopt = findViewById(R.id.virtuallyadopt);
        virtuallyadopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(petprofileview.this,ARView.class));
            }
        });
    }
}