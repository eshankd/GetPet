package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CreatePetProfile extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private String TAG = "CreatePetProfile";
    boolean gender;
    EditText petName;
    EditText petBreed;
    RadioGroup radioGender;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

        petName = findViewById(R.id.petNameIn);
        petBreed = findViewById(R.id.petBreedIn);
        radioGender = findViewById(R.id.radioGender);
        next = findViewById(R.id.nextButton);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_pet_profile);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(CreatePetProfile.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(CreatePetProfile.this, Explore.class));
                        break;
                    case "Adopt":
                        break;
                    case "Notifications":
                        startActivity(new Intent(CreatePetProfile.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(CreatePetProfile.this, Profile.class));
                        break;
                }
                return true;
            }
        });
        nextForm();
    }

    private void nextForm() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CreatePetProfile.this, "PLEASE RUN MY DEAR", Toast.LENGTH_SHORT).show();
                 String name = petName.getText().toString();
                 String breed = petBreed.getText().toString();

                 Toast.makeText(CreatePetProfile.this, Integer.toString(radioGender.getCheckedRadioButtonId()), Toast.LENGTH_SHORT).show();
                 if (radioGender.getCheckedRadioButtonId() == R.id.Male)
                    gender = true;
                 else if (radioGender.getCheckedRadioButtonId() == R.id.Female)
                     gender = false;
                 else
                 {
                     //reset fields//
                 }


                Intent i = new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class);
                 i.putExtra("petNameIn", name);
                 i.putExtra("petGenderIn", gender);
                i.putExtra("petBreedIn", breed);

                Log.d(TAG, "name is " + name + "gender is" + gender + "breed:" +  breed);


                startActivity(new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class));
            }
        });
    }
}
