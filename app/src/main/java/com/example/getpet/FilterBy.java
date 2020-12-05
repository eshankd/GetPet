package com.example.getpet;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilterBy extends AppCompatActivity {

    BottomNavigationView navBar;
    String typeChosen, breedChosen, genderChosen, AdoptFosterChosen;
    int ageChosen;
    private FirebaseAuth auth;
    private ListView petListView;
    private PetObjectAdapter petAdapter;
    private User user;
    private FirebaseFirestore fStore;

    Map<String, Object> choice = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = User.getInstance();

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
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

        final Spinner typeSpinnerIn = findViewById(R.id.spinner1);
        final Spinner breedSpinner = findViewById(R.id.spinner2);
        final Spinner ageSpinner = findViewById(R.id.spinner3);
        final Spinner afSpinner = findViewById(R.id.spinner4);
        final Spinner genderSpinner = findViewById(R.id.spinner5);

        breedSpinner.setEnabled(false);
        ageSpinner.setEnabled(false);
        genderSpinner.setEnabled(false);
        afSpinner.setEnabled(false);

        List<String> typeList = new ArrayList<String>(), breedList = new ArrayList<String>();

        final String type[] = {"Dog", "Cat", "Bird"};
        final String age[] = {"Any", "Less Than 1 Year", "1 Year", "2 Years", "3 Years", "4 Years", "5 Years", "6 Years and Above"};
        final String gender[] = {"Any", "Male", "Female"};
        final String af[] = {"Any", "Adopt", "Foster"};


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinnerIn.setAdapter(typeAdapter);

        typeSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                breedSpinner.setEnabled(true);
                ageSpinner.setEnabled(true);
                genderSpinner.setEnabled(true);
                afSpinner.setEnabled(true);

                if (position == 0) {

                    typeChosen = type[position];

                    ArrayAdapter<CharSequence> dogBreedAdapter = ArrayAdapter.createFromResource(FilterBy.this, R.array.dogbreeds, android.R.layout.simple_spinner_item);
                    dogBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinner.setAdapter(dogBreedAdapter);
                    breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


                    ArrayAdapter<CharSequence> catBreedAdapter = ArrayAdapter.createFromResource(FilterBy.this, R.array.catbreeds, android.R.layout.simple_spinner_item);
                    catBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinner.setAdapter(catBreedAdapter);
                    breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                });

                });

               @Override
                 public void onNothingSelected(AdapterView<?> parent) { }

                                                });






        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, age);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ageChosen = position-1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, gender);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderChosen = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> afAdapter = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, af);
        afSpinner.setAdapter(afAdapter);
        afSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AdoptFosterChosen = af[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchByFilter();
    }

    private void searchByFilter(){
        Button search = findViewById(R.id.search);
        search.setOnClickListener(v -> {

            Intent transfer = new Intent(FilterBy.this, PetsList.class);
            transfer.putExtra("type", typeChosen);
            transfer.putExtra("breed", breedChosen);
            transfer.putExtra("age", ageChosen);
            transfer.putExtra("gender", genderChosen);
            transfer.putExtra("adoptfoster", AdoptFosterChosen);
            startActivity(transfer);

        });
    }
}

