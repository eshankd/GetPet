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
                        //Do something
                        break;
                    case "Profile":
                        startActivity(new Intent(DogsList.this, Profile.class));
                        break;
                }
                return true;
            }
        });


//        name=findViewById(R.id.name);
//        age=findViewById(R.id.age);
//        gender=findViewById(R.id.gender);
//        breed=findViewById(R.id.breed);
//        btn=findViewById(R.id.btnload);
//
//       btn.setOnClickListener(new View.OnClickListener(){
//
//           public void onClick(View view){
//               reff= FirebaseDatabase.getInstance()
//                       .getReference()
//                       .child("Member")
//                       .child("1");
//               reff.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                       String name = dataSnapshot.child("name").getValue().toString();
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError error) {
//
//                   }
//               });
//
//
//                                  }
//       });


    }
}