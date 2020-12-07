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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Storyboard extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;

    private ListView postsListView;
    private StoryboardObjectAdapter storyboardAdapter;

    User user = User.getInstance();

    private String TAG = "Storyboard";

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
        fStore.collection("Posts").orderBy("timeStamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int count = queryDocumentSnapshots.size();

                        ArrayList <StoryboardObject> postsList = new ArrayList<>();
                        postsListView = findViewById(R.id.storyboardList);

                        for(DocumentSnapshot snapDoc : queryDocumentSnapshots){
                            ArrayList<String> usersLiked;
                            usersLiked = (ArrayList<String>) snapDoc.get("Likes");
                            postsList.add(new StoryboardObject(snapDoc.getId(), snapDoc.getString("Name"), snapDoc.getString("Caption"),
                                    snapDoc.getString("userEmail"), usersLiked, usersLiked.contains(user.getEmail())));
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

}
