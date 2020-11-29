package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FilterBy extends AppCompatActivity {

    BottomNavigationView navBar;
    String typeChosen,breedChosen,genderChosen;
    int ageChosen,AdoptFosterChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(FilterBy.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(FilterBy.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(FilterBy.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(FilterBy.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(FilterBy.this, Profile.class));
                    break;
            }
            return true;
        });

        searchByFilter();
        final Spinner typeSpinner = findViewById(R.id.spinner1);
        final Spinner breedSpinner = findViewById(R.id.spinner2);
        final Spinner ageSpinner = findViewById(R.id.spinner3);
        final Spinner afSpinner = findViewById(R.id.spinner4);
        final Spinner genderSpinner = findViewById(R.id.spinner5);


        final String type[]={"Dog","Cat","Bird"};
        final String dogs[] = {"Any","Beagle","Labrador","Bichon Frise","Maltese","Great Dane","Husky","Shihtzu","Rottweiler","German Shepherd","Bulldog","Poodle","Chihuahua","Doberman","Corgi","Greyhound","Saluki","Pomeranion"};
        final String cats[] = {"Any","British Shorthair","Persian Cat","Maine Coon"};
        final String birds[] = {"Any","African Parrot","Eagle","Falcon"};
        final String age[] = {"Any","Less Than 1 Year","1 Year","2 Years","3 Years","4 Years","5 Years","6 Years and Above"};
        final String gender[]={"Any","Male","Female"};
        final String af[] = {"Any","Adopt","Foster"};


        ArrayAdapter<String> adapter  = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, dogs);
                    typeChosen="dog";

                    breedSpinner.setAdapter(adapter1);
                    breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position)
                            {
                                case 0: breedChosen="Any";
                                    break;
                                case 1: breedChosen="Beagle";
                                    break;
                                case 2: breedChosen="Labrador";
                                    break;
                                case 3: breedChosen="Bichon Frise";
                                    break;
                                case 4: breedChosen="Maltese";
                                    break;
                                case 5: breedChosen="Great Dane";
                                    break;
                                case 6: breedChosen="Husky";
                                    break;
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
                if (position == 1) {
                    typeChosen="cat";
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, cats);
                    breedSpinner.setAdapter(adapter2);
                }

                if (position == 2) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, birds);
                    typeChosen="bird";
                    breedSpinner.setAdapter(adapter3);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,age);
        ageSpinner.setAdapter(adapter4);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0 :ageChosen =-1;
                            break;
                    case 1: ageChosen = 0;
                        break;
                    case 2: ageChosen = 1;
                        break;
                    case 3: ageChosen = 2;
                        break;
                    case 4: ageChosen = 3;
                        break;
                    case 5: ageChosen = 4;
                        break;
                    case 6: ageChosen = 5;
                        break;
                    case 7: ageChosen = 6;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter6= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,gender);
        genderSpinner.setAdapter(adapter6);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    genderChosen = "Any";
                }
                else if(position==1)
                    genderChosen = "Male";
                else{
                    genderChosen = "Female";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,af);
        afSpinner.setAdapter(adapter5);

        afSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    AdoptFosterChosen = 0;
                }
                else if(position==1)
                    AdoptFosterChosen = 1;
                else{
                    AdoptFosterChosen = 2;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void searchByFilter(){
        Button search = findViewById(R.id.search);
        search.setOnClickListener(v -> {

            Intent transfer = new Intent(FilterBy.this, PetsList.class);
            transfer.putExtra("type",typeChosen);
            transfer.putExtra("breed",breedChosen);
            transfer.putExtra("age",ageChosen);
            transfer.putExtra("gender",genderChosen);
            transfer.putExtra("adoptfoster",AdoptFosterChosen);
            startActivity(transfer);

        });
    }
}