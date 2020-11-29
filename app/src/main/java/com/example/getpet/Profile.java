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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private TextView nameOUT;
    private TextView emailOUT;
    private ImageView profilePictureOut;
    private String name;
    private String email;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.profile));

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

        nameOUT = findViewById(R.id.userProfileFName);
        emailOUT = findViewById(R.id.userProfileEmail);
        profilePictureOut = findViewById(R.id.imageView9);

        user = auth.getCurrentUser();
        userId = Objects.requireNonNull(user.getUid());
        if (user != null) {
            fStore.collection("Users")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot snapDoc : queryDocumentSnapshots){
                                if (snapDoc.getId().equals(userId)){
                                    name = snapDoc.getString("FirstName") + " " + snapDoc.getString("LastName");
                                    break;
                                }
                        }
                    }
            });
            email = user.getEmail();
            nameOUT.setText(name);
            emailOUT.setText(email);

        } else {
            Toast.makeText(Profile.this, "User info not found", Toast.LENGTH_SHORT).show();
        }

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
