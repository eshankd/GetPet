package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
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

import org.w3c.dom.Document;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class DogsList extends AppCompatActivity {

    BottomNavigationView navBar;


    private FirebaseFirestore fStore;


    private TextView name;
    private TextView age;
    private TextView breed;
    private TextView gender;

    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;

    private String TAG = "DogsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        next();
        loadDogs();

        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
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

        age = findViewById(R.id.dogAge);
        breed = findViewById(R.id.dogBreed);
        name = findViewById(R.id.dogName);
        gender = findViewById(R.id.dogGender);


        transferredBreed = getIntent().getStringExtra("breed");
        transferredAge = getIntent().getIntExtra("age",0);
        transferredGender = getIntent().getStringExtra("gender");

//        age.setText(transferredAge);
//        breed.setText(transferredBreed);
//        gender.setText(transferredGender);

    }

    private void next() {
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DogsList.this, petprofileview.class));
            }
        });
    }


    private void loadDogs() {

        Button loadDogsBTN = findViewById(R.id.btnload);

        loadDogsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("Dogs")
//                        .whereEqualTo("Age",transferredAge)
                        .whereEqualTo("Breed",transferredBreed)
                        .whereEqualTo("Gender",transferredGender)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int count = queryDocumentSnapshots.size();
                                Log.d(TAG,Integer.toString(count));
                                gender.setText(Integer.toString(count));
                                for(DocumentSnapshot snapDoc : queryDocumentSnapshots){
                                    name.setText(snapDoc.getString("Name"));
                                    Log.d(TAG,  snapDoc.getString("Name"));
                                }
//                                String[][] dogOptions = new String[count][4];
//                                int i =0;
//                                for (DocumentSnapshot docSnap : queryDocumentSnapshots) {
//                                    dogOptions[i][0] = docSnap.getString("Name");
//                                    dogOptions[i][1] = docSnap.getString("Age");
//                                    dogOptions[i][2] = docSnap.getString("Breed");
//                                    dogOptions[i][3] = docSnap.getString("Gender");
//                                    name.setText(dogOptions[i][0]);
//                                    age.setText(dogOptions[i][1]);
//                                    breed.setText(dogOptions[i][2]);
//                                   // gender.setText(dogOptions[i][3]);
//                                    i++;
//                                }
                            }
                        });
            }
        });
    }
}

