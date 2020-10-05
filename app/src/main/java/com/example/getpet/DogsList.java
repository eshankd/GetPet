package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DogsList extends AppCompatActivity {

//    TextView  name,age,gender,breed;
//    Button btn;
//    DatabaseReference reff;
BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        navBar = findViewById(R.id.bottom_navbar);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(DogsList.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(DogsList.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(DogsList.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(DogsList.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(DogsList.this, Profile.class));
                        break;
                }
                return true;


            }
        });

        Next();
    }

    private void Next(){
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DogsList.this, ARView.class));
            }
        });
    }
}