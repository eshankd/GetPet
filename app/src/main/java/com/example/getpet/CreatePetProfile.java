package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
    boolean gender;
    EditText petName;
    EditText petBreed;
    RadioGroup radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile);

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

        nextForm();
    }

    public void nextForm(){

        petName = findViewById(R.id.petNameIn);
        petBreed = findViewById(R.id.petBreedIn);
        radioGender = findViewById(R.id.radioGender);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = petName.getText().toString();

                String breed = petBreed.getText().toString();


                Log.d("this chicken" , "beef");
                Log.d("this chicken" , name);


                if (radioGender.getCheckedRadioButtonId() == R.id.Male)
                    gender = true;

                else if (radioGender.getCheckedRadioButtonId() == R.id.Female)
                    gender = false;
                else
                {

                }

                Intent i = new Intent(CreatePetProfile.this, CreatePetProfileSubmit.class);

                i.putExtra("name", name);
                Toast.makeText(CreatePetProfile.this, "name"+ name, Toast.LENGTH_SHORT).show();
                i.putExtra("gender", gender);
                i.putExtra("breed", breed);
                startActivity(i);


            }
        });

}
}





