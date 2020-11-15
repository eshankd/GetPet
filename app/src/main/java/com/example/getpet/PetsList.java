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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class PetsList extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;


    private ListView petListView;
    private PetObjectAdapter petAdapter;

    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;
    private String TAG = "DogsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);

        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });
        transferredBreed = getIntent().getStringExtra("breed");
        transferredAge = getIntent().getIntExtra("age",0);
        transferredGender = getIntent().getStringExtra("gender");

        LoadPets();
    }









    private void LoadPets() {
        fStore.collection("Dogs")
          //  .whereEqualTo("Age",transferredAge)
//            .whereEqualTo("Breed", transferredBreed)
//            .whereEqualTo("Gender", transferredGender)
            .get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int count = queryDocumentSnapshots.size();
                    Log.d(TAG,Integer.toString(count));

                    ArrayList <PetObject> petList = new ArrayList<PetObject>();
                    petListView = findViewById(R.id.PetList);

                    for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                        petList.add(new PetObject(snapDoc.getString("ID"), snapDoc.getString("Name"), snapDoc.getString("Breed"), snapDoc.getString("Gender"), snapDoc.getLong("Age").intValue(), snapDoc.getString("Description")));
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
                            startActivity(toPetProfile);
                        }
                    });

                }
            });
    }
}

