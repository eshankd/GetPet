package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Objects;

public class Profile extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
//    private FirebaseUser fUser;
    User user;
    private TextView nameOUT;
    private TextView emailOUT;
    private TextView petsOwnedOUT;
    private ImageView profilePictureOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        auth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.profile));
        user.getInstance();

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Profile.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Profile.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Profile.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Profile.this, Notification.class));
                    break;
                case "Profile":
                    //Do Nothing
                    break;
            }
            return true;
        });

//        nameOUT = findViewById(R.id.userProfileFName);
//        emailOUT = findViewById(R.id.userProfileEmail);
//        profilePictureOut = findViewById(R.id.imageView9);
//        petsOwnedOUT = findViewById(R.id.petsOwnedOut);
//
//        nameOUT.setText(user.getFullName());
//        emailOUT.setText(user.getEmail());
//        petsOwnedOUT.setText(user.getPetsOwned());


//        user = auth.getCurrentUser();
//        userId = Objects.requireNonNull(user.getUid());

        //query the db with uid
        //if exists update ui
        //if not
//        if (user != null) {
//            fStore.collection("Users").whereEqualTo(FieldPath.documentId(), userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    for (DocumentSnapshot snapDoc : queryDocumentSnapshots) {
//                        nameOUT.setText(snapDoc.getString("FirstName") + " " + snapDoc.getString("LastName"));
//                        emailOUT.setText(snapDoc.getString("Email"));
//                        //petsOwnedOUT.setText(snapDoc.getLong("PetsOwned").toString());
//                    }
//                }
//            });
//        }
//        else {
//            Toast.makeText(Profile.this, "User info not found", Toast.LENGTH_SHORT).show();
//        }

        signOut();
        loginButton();
    }

    private void signOut() {
        Button signOutBTN = findViewById(R.id.signOut);
        signOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
                Toast.makeText(Profile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
    }
}
