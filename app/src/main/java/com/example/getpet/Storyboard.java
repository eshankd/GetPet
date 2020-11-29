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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Storyboard extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;

    private ListView postsListView;
    private StoryboardObjectAdapter storyboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);

        fStore = FirebaseFirestore.getInstance();

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
                        int count = queryDocumentSnapshots.size();

                        ArrayList <StoryboardObject> postsList = new ArrayList<StoryboardObject>();
                        postsListView = findViewById(R.id.storyboardList);

                        for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                            postsList.add(new StoryboardObject(snapDoc.getString("PostID"), snapDoc.getString("Name"), snapDoc.getString("Caption"), snapDoc.getLong("Likes").intValue()));
                        }

                        storyboardAdapter = new StoryboardObjectAdapter(Storyboard.this, postsList);
                        postsListView.setAdapter(storyboardAdapter);



                    }
                });
    }


    private void addStory() {
        FloatingActionButton addStory = findViewById(R.id.addStory);
        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Storyboard.this, AddStory.class));
            }
        });
    }
}