package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdoptFoster extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = User.getInstance();
        setContentView(R.layout.activity_adopt_foster);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(AdoptFoster.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(AdoptFoster.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(AdoptFoster.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(AdoptFoster.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(AdoptFoster.this, Profile.class));
                    break;
            }
            return true;
        });
        adoptFoster();
        findAHome();
        setupUser();
    }

    private void adoptFoster(){
        Button adoptFoster = findViewById(R.id.adoptfoster);
        adoptFoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }

    private void findAHome(){
        Button findahome = findViewById(R.id.findahome);
        findahome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(user.getPetsOwned() == -1)
                {
                    Toast.makeText(AdoptFoster.this, "Please Login to find a home for your Pet", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(AdoptFoster.this,CreatePetProfile1.class));
            }
        });
    }

    private void setupUser()    {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        if (firebaseUser != null)   {
            fStore.collection("Users").whereEqualTo(FieldPath.documentId(),firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String userFName;
                    String userLName;
                    String userEmail;
                    ArrayList<String> userPetsOwned;
                    for (DocumentSnapshot snapDoc : queryDocumentSnapshots) {


                        userFName = snapDoc.getString("FirstName");
                        userLName = snapDoc.getString("LastName");
                        userEmail = snapDoc.getString("Email");
                        userPetsOwned = (ArrayList<String>) snapDoc.get("PetsOwned");
                        user.setData(snapDoc.getId(),userFName, userLName, userPetsOwned, userEmail);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        auth.signOut();
        user.reset();
        startActivity(new Intent(AdoptFoster.this,MainActivity.class));
        Toast.makeText(AdoptFoster.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
    }


}