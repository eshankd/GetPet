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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CreatePetProfile extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseAuth auth;
    RadioButton radioButton;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

        radioGroup =findViewById(R.id.radioGender);

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
        next();
    }

    private void next() {

        EditText petName = findViewById(R.id.namePet);
        RadioGroup radioGender = findViewById(R.id.radioGender);
        Button next = findViewById(R.id.nextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String petNameInput = petName.getText().toString();
                 boolean petGenderInput;
                 Toast.makeText(CreatePetProfile.this, Integer.toString(radioGender.getCheckedRadioButtonId()), Toast.LENGTH_SHORT).show();
                 if (radioGender.getCheckedRadioButtonId() == R.id.Male)
                    petGenderInput = true;
                 else if (radioGender.getCheckedRadioButtonId() == R.id.Female)
                     petGenderInput = false;
                 else
                 {
                     //reset fields//
                 }


                Intent i = new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class);
                 i.putExtra("petNameInput", petNameInput);
//                 i.putExtra("petGenderInput", petGenderInput);


                startActivity(new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class));
            }
        });
    }
}
