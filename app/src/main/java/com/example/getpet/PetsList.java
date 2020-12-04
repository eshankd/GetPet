package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PetsList extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;private ListView petListView;
    private PetObjectAdapter petAdapter;

    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;
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
        transferredBreed = getIntent().getStringExtra("breed");
        transferredAge = getIntent().getIntExtra("age",0);
        transferredGender = getIntent().getStringExtra("gender");


        if(!transferredBreed.equals("Any"))
            choice.put("Breed",transferredBreed);
        if(transferredAge != -1)
            choice.put("Age",transferredAge);
        if(!transferredGender.equals("Any"))
            choice.put("Gender",transferredGender);


        LoadPets();
    }


    private void LoadPets() {

        Query docref = fStore.collection("Dogs");

        for (String i : choice.keySet()) {
            if(i.equals("Age") && (((Integer)choice.get(i)) == 6))
                docref = docref.whereGreaterThanOrEqualTo(i,6);
            else
                docref = docref.whereEqualTo(i,choice.get(i));
        }

        docref.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();
                    Log.d(TAG,Integer.toString(count));

                    ArrayList <PetObject> petList = new ArrayList<PetObject>();
                    petListView = findViewById(R.id.PetList);

                    for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                        petList.add(new PetObject(snapDoc.getString("ID"), snapDoc.getString("Name"), snapDoc.getString("Type"), snapDoc.getString("Breed"), snapDoc.getString("Gender"), snapDoc.getLong("Age").intValue(), snapDoc.getString("Description")));
                }


                petAdapter = new PetObjectAdapter(PetsList.this, petList);
                petListView.setAdapter(petAdapter);

                petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PetObject obj = petList.get(position);

                        Intent toPetProfile = new Intent(PetsList.this, petprofileview.class);
                        toPetProfile.putExtra("petName", obj.getName());
                        toPetProfile.putExtra("petAge", obj.getAge());
                        toPetProfile.putExtra("petGender", obj.getGender());
                        toPetProfile.putExtra("petBreed", obj.getBreed());
                        toPetProfile.putExtra("petDescription" , obj.getDescription());
                        toPetProfile.putExtra("petID", obj.getPetID());
                        startActivity(toPetProfile);
                    }
                });

            });
    }
}

