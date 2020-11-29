package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStory extends AppCompatActivity {

    private EditText captionIn;
    private FirebaseFirestore fStore;
    BottomNavigationView navBar;
    private String TAG = "AddStory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.storyboard));

        fStore = FirebaseFirestore.getInstance();

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(AddStory.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(AddStory.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(AddStory.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(AddStory.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(AddStory.this, Profile.class));
                        break;
                }
                return true;
            }
        });

        post();

    }

    private void post() {
        Button post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captionIn = findViewById(R.id.inputPost);

                DocumentReference docref = fStore.collection("Posts").document("test");
                Map<String, Object> post = new HashMap<>();
                post.put("Caption", captionIn);
                post.put("Likes", 0);
                post.put("Name", "Test");
                post.put("PostID", 0001);
                docref.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Story added");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Story not added");
                    }
                });

                startActivity(new Intent(AddStory.this, Storyboard.class));
            }
        });
    }


}