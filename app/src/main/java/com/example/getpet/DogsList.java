package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DogsList extends AppCompatActivity {

    BottomNavigationView navBar;

    private FirebaseFirestore fStore;

    private String name;
    private String age;
    private String breed;
    private String gender;

    private TextView nameOUT;
    private TextView ageOUT;
    private TextView breedOUT;
    private TextView genderOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);

        nameOUT = findViewById(R.id.dogName);
        ageOUT = findViewById(R.id.dogAge);
        breedOUT = findViewById(R.id.dogBreed);
        genderOUT = findViewById(R.id.dogGender);

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

        loadDogs();

    }

    private void loadDogs(){
        Button loadDogsBTN = findViewById(R.id.btnload);
        loadDogsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Dogs").addSnapshotListener(new EventListener<QuerySnapshot>(){
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot doc : value){
                            name = doc.getString("Name");
                            age = doc.getString("Age");
                            breed = doc.getString("Breed");
                            gender = doc.getString("Gender");

                            nameOUT.setText(name);
                            ageOUT.setText(age);
                            breedOUT.setText(breed);
                            genderOUT.setText(gender);

                            break;
                        }

                    }
                });
            }
        });
    }
}