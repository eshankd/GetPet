package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CreatePetProfileSubmit extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile_submit);


        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_pet_profile_submit);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(CreatePetProfileSubmit.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(CreatePetProfileSubmit.this, Explore.class));
                        break;
                    case "Adopt":
                        break;
                    case "Notifications":
                        startActivity(new Intent(CreatePetProfileSubmit.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(CreatePetProfileSubmit.this, Profile.class));
                        break;
                }
                return true;
            }
        });

        submit();
    }

    private void submit() {
        Button submit = findViewById(R.id.submitPetProfile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePetProfileSubmit.this, petprofileview.class));
            }
        });
    }




    }
