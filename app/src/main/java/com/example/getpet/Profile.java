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
    private User user;
    private TextView nameOUT;
    private TextView emailOUT;
    private TextView petsOwnedOUT;
    private ImageView profilePictureOut;

    private TextView petsOwnedLabel;
    private TextView emailLabel;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = User.getInstance();
        auth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);

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
                    startActivity(new Intent(Profile.this, Profile.class));
                    break;
            }
            return true;
        });

        nameOUT = findViewById(R.id.userProfileFName);
        emailOUT = findViewById(R.id.userProfileEmail);
        profilePictureOut = findViewById(R.id.imageView9);
        petsOwnedOUT = findViewById(R.id.petsOwnedOut);

        if (user.getPetsOwned() == -1){
            nameOUT.setText(user.getFullName());
            emailLabel = findViewById(R.id.emailLabel);
            petsOwnedLabel = findViewById(R.id.petsOwned);
            emailLabel.setVisibility(View.GONE);
            petsOwnedLabel.setVisibility(View.GONE);
        }
        else{
            nameOUT.setText(user.getFullName());
            emailOUT.setText(user.getEmail());
            petsOwnedOUT.setText(Integer.toString(user.getPetsOwned()));
            loginButton.setVisibility(View.GONE);
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
    }
}
