package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Storyboard extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    User user;
    Button likeButton;
    private ListView postsListView;
    private StoryboardObjectAdapter storyboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);

        fStore = FirebaseFirestore.getInstance();
        user = User.getInstance();
        likeButton = findViewById(R.id.likeButton);
        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.storyboard));

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.toString()) {
                    case "Storyboard":
                        break;
                    case "Explore":
                        startActivity(new Intent(Storyboard.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(Storyboard.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(Storyboard.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(Storyboard.this, Profile.class));
                        break;
                }
                return true;
            }
        });


        addStory();
        LoadPosts();
    }

    private void LoadPosts() {
        fStore.collection("Posts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        ArrayList <StoryboardObject> postsList = new ArrayList<>();
                        postsListView = findViewById(R.id.storyboardList);

                        for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                            postsList.add(new StoryboardObject(snapDoc.getId(), snapDoc.getString("Name"),snapDoc.getString("userEmail"),
                                    snapDoc.getString("Caption"), snapDoc.getLong("Likes").intValue()));
                        }

                        storyboardAdapter = new StoryboardObjectAdapter(Storyboard.this, postsList);
                        postsListView.setAdapter(storyboardAdapter);
                    }
                });
    }


    private void addStory() {
        FloatingActionButton addStory = findViewById(R.id.addStory);
        addStory.setOnClickListener(v -> startActivity(new Intent(Storyboard.this, AddStory.class)));
    }

//    private void like(DocumentReference docRef) {
//
//        String fromUser = user.getEmail();
//        String fromName - user.getFromName();
//
//        String toUser = ;
//
//        likeButton.setOnClickListener(v -> {
//
//            CollectionReference docref = fStore.collection("Notifications");
//            Map<String, Object> post = new HashMap<>();
//            post.put("fromUser", fromUser);
//            post.put("toUser", toUser);
//            post.put("Message", fromUser + "has liked your post!" );
//
//            docref.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    if(picUp)
//                        upload(documentReference);
//                    Log.d(TAG, "Story added");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.w(TAG, "Error adding post");
//                }
//            });
//
//        });
//    }







}