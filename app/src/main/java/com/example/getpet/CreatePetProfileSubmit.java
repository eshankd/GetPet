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
    private String TAG = "CreatePetProfileSubmit";
    private EditText descriptionIn;

    int dayIn, monthIn, yearIn;
    String transferredName;
    String transferredBreed;
    String transferredGender;
    private String userid;
    private User user = User.getInstance();

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

        navBar.setOnNavigationItemSelectedListener(item -> {
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
        });


        mCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dayIn = dayOfMonth;
            monthIn = month;
            yearIn = year;
        });


        submitForm();
    }

    private void submitForm() {

        Button submit = findViewById(R.id.submitPetProfile);
        Toast.makeText(CreatePetProfileSubmit.this, "test", Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionIn = findViewById(R.id.description);

                final String description = descriptionIn.getText().toString();

                transferredName = getIntent().getStringExtra("name");
                transferredBreed = getIntent().getStringExtra("breed");
                transferredGender = getIntent().getStringExtra("gender");




                userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                DocumentReference docref = fStore.collection("Dogs").document(userid);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", transferredName);
                user.put("ID", "D009");
                user.put("Gender", transferredGender);
                user.put("Age", 0);
                user.put("Breed", transferredBreed);
                user.put("Description", descriptionIn);

                docref.set(user).addOnSuccessListener(aVoid ->
                        Log.d(TAG, "Profile Created Success"))
                        .addOnFailureListener(e -> Log.w(TAG, "Profile not created"));


                startActivity(new Intent(CreatePetProfileSubmit.this, AdoptFoster.class));

            }
        });
    }
}









