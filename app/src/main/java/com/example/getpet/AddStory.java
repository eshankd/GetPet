package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddStory extends AppCompatActivity {

    private EditText captionIn;
    private FirebaseFirestore fStore;
    BottomNavigationView navBar;
    private String TAG = "AddStory";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.storyboard));

        user = User.getInstance();

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

        postStory();

    }

    private void postStory() {

        Button postStoryButton = findViewById(R.id.post);
        postStoryButton.setOnClickListener(v -> {

            captionIn = findViewById(R.id.inputPost);
            String caption = captionIn.getText().toString();

            CollectionReference docref = fStore.collection("Posts");
            Map<String, Object> post = new HashMap<>();
            post.put("Caption", caption);
            post.put("Likes", 0);
            post.put("Name", user.getFullName());
            post.put("PostID", "P008");



            docref.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "Story added");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding post");
                }
            });

            startActivity(new Intent(AddStory.this, Storyboard.class));
        });
    }


}