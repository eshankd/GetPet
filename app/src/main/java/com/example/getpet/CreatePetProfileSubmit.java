package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreatePetProfileSubmit extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    private User user;
    private CalendarView mCalendarView;
    private String TAG = "CreatePetProfileSubmit";
    private EditText descriptionIn;

    int dayIn, monthIn, yearIn;
    String transferredName;
    String transferredBreed;
    String transferredGender;
    String transferredType;

    Period diff;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile_submit);
        setContentView(R.layout.activity_create_pet_profile_submit);

        fStore = FirebaseFirestore.getInstance();
        user = User.getInstance();

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(CreatePetProfileSubmit.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Profile.class));
                    break;
            }
            return true;
        });


        mCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dayIn = dayOfMonth;
            monthIn = month;
            yearIn = year;

            LocalDate loc = LocalDate.of(yearIn,monthIn,dayIn);
            LocalDate now = LocalDate.now();
            diff = Period.between(loc,now);
        });


        submitForm();
    }

    private void submitForm() {

        Button submit = findViewById(R.id.submitPetProfile);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                descriptionIn = findViewById(R.id.description);

                final String description = descriptionIn.getText().toString();

                transferredName = getIntent().getStringExtra("name");
                transferredBreed = getIntent().getStringExtra("breed");
                transferredGender = getIntent().getStringExtra("gender");
                transferredType = getIntent().getStringExtra("type");


                CollectionReference docref = fStore.collection("Pets");
                Map<String, Object> petProfile = new HashMap<>();
                petProfile.put("Name", transferredName);
                petProfile.put("ID", "D009");
                petProfile.put("Gender", transferredGender);
                petProfile.put("Type",transferredType);
                petProfile.put("Age", diff.getYears());
                petProfile.put("Breed", transferredBreed);
                petProfile.put("Description", description);
                petProfile.put("userEmail", user.getEmail());

                docref.add(petProfile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Profile Created Success");
                        Toast.makeText(CreatePetProfileSubmit.this, transferredName + " is up for adoption!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Profile not created");
                    }
                });


                startActivity(new Intent(CreatePetProfileSubmit.this, AdoptFoster.class));

            }
        });
    }
}









