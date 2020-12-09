package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PetsList extends AppCompatActivity {


    //declaring variables to be used in the below functions
    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    private ListView petListView;
    private PetObjectAdapter petAdapter;
    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;
    private String transferredType;
    private String TAG = "PetsList";

    Map<String, Object> choice = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);

        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(PetsList.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(PetsList.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(PetsList.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(PetsList.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(PetsList.this, Profile.class));
                    break;
            }
            return true;
        });


        //declared variables being assigned intents gotten from other activities
        transferredBreed = getIntent().getStringExtra("breed");
        transferredAge = getIntent().getIntExtra("age",0);
        transferredGender = getIntent().getStringExtra("gender");
        transferredType = getIntent().getStringExtra("type");


        if(!transferredBreed.equals("Any"))
            choice.put("Breed",transferredBreed);
        if(transferredAge != -1)
            choice.put("Age",transferredAge);
        if(!transferredGender.equals("Any"))
            choice.put("Gender",transferredGender);
        choice.put("Type",transferredType);


        LoadPets();
    }

// loads the pets for the user to see after filtering
    private void LoadPets() {

        Query docref = fStore.collection("Pets");

        for (String i : choice.keySet()) {
            if(i.equals("Age") && (((Integer)choice.get(i)) == 6))
                docref = docref.whereGreaterThanOrEqualTo(i,6);
            else
                docref = docref.whereEqualTo(i,choice.get(i));
        }

        docref.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();

                    ArrayList <PetObject> petList = new ArrayList<PetObject>();
                    petListView = findViewById(R.id.PetList);

                    for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                        petList.add(new PetObject(snapDoc.getId(),
                                snapDoc.getString("Name"),
                                snapDoc.getString("userEmail"),
                                snapDoc.getString("Type"),
                                snapDoc.getString("Breed"),
                                snapDoc.getString("Gender"),
                                snapDoc.getLong("Age").intValue(),
                                snapDoc.getString("Description")));
                }


                petAdapter = new PetObjectAdapter(PetsList.this, petList);
                petListView.setAdapter(petAdapter);

                petListView.setOnItemClickListener((parent, view, position, id) -> {
                    PetObject obj = petList.get(position);

                    Intent toPetProfile = new Intent(PetsList.this, petprofileview.class);
                    toPetProfile.putExtra("petName", obj.getName());
                    toPetProfile.putExtra("petAge", obj.getAge());
                    toPetProfile.putExtra("petGender", obj.getGender());
                    toPetProfile.putExtra("petBreed", obj.getBreed());
                    toPetProfile.putExtra("petDescription" , obj.getDescription());
                    toPetProfile.putExtra("petID", obj.getPetID());
                    toPetProfile.putExtra("userEmail",obj.getUserEmail());
                    startActivity(toPetProfile);
                });

            });
    }
}

