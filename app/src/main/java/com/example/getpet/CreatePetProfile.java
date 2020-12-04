package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String gender;
    EditText petName;
    EditText petBreed;
    RadioGroup radioGender;
    Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

        petName = findViewById(R.id.petNameIn);
        petBreed = findViewById(R.id.petBreedIn);
        radioGender = findViewById(R.id.radioGender);
        nextButton = findViewById(R.id.nextButton);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_pet_profile);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
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
        });
    }

    public void nextForm(View view){

        String name = petName.getText().toString();
        String breed = petBreed.getText().toString();

        if (radioGender.getCheckedRadioButtonId() == R.id.Male)
            gender = "Male";
        else if (radioGender.getCheckedRadioButtonId() == R.id.Female)
            gender = "Female";
        else
        {
            Log.d("debug", "else");
        }

        try {
            Thread.sleep(3000);
            // Do some stuff
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent i = new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class);
        i.putExtra("name", name);
        i.putExtra("gender", gender);
        i.putExtra("breed", breed);
        startActivity(i);
    }
}


