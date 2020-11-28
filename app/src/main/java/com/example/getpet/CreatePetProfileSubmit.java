package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreatePetProfileSubmit extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private CalendarView mCalendarView;
    private EditText descriptionIn;
    int dayIn, monthIn, yearIn;

    private String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile_submit);



        setContentView(R.layout.activity_create_pet_profile_submit);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

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


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dayIn = dayOfMonth;
                monthIn = month;
                yearIn = year;
            }
        });


        submit();
    }










    private void submit() {
        Button submit = findViewById(R.id.submitPetProfile);
        descriptionIn = findViewById(R.id.description);;

        final String description = descriptionIn.getText().toString();
        final int day = dayIn;;
        final int month = monthIn;
        final int year = yearIn;

         userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
         DocumentReference docref = fStore.collection("Dogs").document(userid);
         Map<String, Object> user = new HashMap<>();
         user.put("Name", petName);
         user.put("ID", petID);
         user.put("Gender", petGender);
         user.put("Age", petAge);
        user.put("Breed", petBreed);
        user.put("Description", petDescription);

         docref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void aVoid) {
             Log.d(TAG, "onSuccess: user Profile is created for " + userid);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Profile not created");
                            }
                        });
                        startActivity(new Intent(CreatePetProfileSubmit.this, PetsList.class));
                    }


        }









