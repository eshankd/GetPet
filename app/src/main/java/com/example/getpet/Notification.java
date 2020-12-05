package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    User user;
    private ListView notificationsListView;
    private NotificationObjectAdapter notificationAdapter;
    TextView notificationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.notifications));
        notificationCount = findViewById(R.id.notificationCount);
        user = User.getInstance();
        fStore = FirebaseFirestore.getInstance();

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Notification.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Notification.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Notification.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Notification.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Notification.this, Profile.class));
                    break;
            }
            return true;
        });

        loadNotitications();
    }

    private void loadNotitications() {
        fStore.collection("Notifications")
                .whereEqualTo("toUser", user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        int count = queryDocumentSnapshots.size();
                        notificationCount.setText(" (" + Integer.toString(count) + ")");

                        ArrayList<NotificationObject> notificationsList = new ArrayList<>();
                        notificationsListView = findViewById(R.id.notificationsList);

                        for(DocumentSnapshot snapDoc : queryDocumentSnapshots){

                            notificationsList.add(new NotificationObject(snapDoc.getString("fromName"),snapDoc.getString("fromUser"), snapDoc.getString("toUser"), snapDoc.getString("Message")));
                        }

                        notificationAdapter = new NotificationObjectAdapter(Notification.this, notificationsList);
                        notificationsListView.setAdapter(notificationAdapter);



                    }
                });
    }
}