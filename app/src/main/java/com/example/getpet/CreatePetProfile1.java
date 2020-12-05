package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CreatePetProfile1 extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private String TAG = "CreatePetProfile1";
    String gender;
    EditText petName,petBreed;
    RadioGroup radioGender;
    Button nextButton;
    String typeChosen,breedChosen;
    Spinner typeSpinnerIn,breedSpinnerIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile1);


        petName = findViewById(R.id.petNameIn);
        radioGender = findViewById(R.id.radioGender);
        nextButton = findViewById(R.id.nextButton);
        typeSpinnerIn  = findViewById(R.id.typeSpinnerIn);
        breedSpinnerIn = findViewById(R.id.breedSpinnerIn);
        typeSpinnerIn = findViewById(R.id.typeSpinnerIn);

        auth = FirebaseAuth.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(CreatePetProfile1.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(CreatePetProfile1.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(CreatePetProfile1.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(CreatePetProfile1.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(CreatePetProfile1.this, Profile.class));
                    break;
            }
            return true;
        });

        String type[] = {"Dog", "Cat", "Bird"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinnerIn.setAdapter(typeAdapter);

        typeSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    typeChosen = type[position];

                    ArrayAdapter<CharSequence> dogBreedAdapter = ArrayAdapter.createFromResource(CreatePetProfile1.this,R.array.dogbreeds, android.R.layout.simple_spinner_item);
                    dogBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinnerIn.setAdapter(dogBreedAdapter);
                    breedSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                else if (position == 1) {

                    ArrayAdapter<CharSequence> catBreedAdapter = ArrayAdapter.createFromResource(CreatePetProfile1.this,R.array.catbreeds, android.R.layout.simple_spinner_item);
                    catBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinnerIn.setAdapter(catBreedAdapter);
                    breedSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextForm();
    }

    public void nextForm(){

        petName = findViewById(R.id.petNameIn);
        radioGender = findViewById(R.id.radioGender);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = petName.getText().toString();

                if (radioGender.getCheckedRadioButtonId() == R.id.MaleRadio)
                    gender = "Male";
                else if (radioGender.getCheckedRadioButtonId() == R.id.FemaleRadio)
                    gender = "Female";
                else
                {
                    Log.d("debug", "else");
                }

                Intent i = new Intent(CreatePetProfile1.this, CreatePetProfileSubmit.class);

                i.putExtra("name", name);
                i.putExtra("gender", gender);
                i.putExtra("breed", breedChosen);
                i.putExtra("type",typeChosen);
                startActivity(i);

            }
        });

    }

}