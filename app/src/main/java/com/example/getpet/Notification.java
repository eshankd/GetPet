package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {


    // declaring all the variables to be used by the functions below
    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    User user;
    private ListView notificationsListView;
    private NotificationObjectAdapter notificationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.notifications));
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

        loadNotifications();
    }


    //function that loads all the notifications in th notifications activity inflating the listView
    private void loadNotifications() {

        fStore.collection("Notifications2").orderBy("timeStamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();

                    ArrayList<NotificationObject> notificationsList = new ArrayList<>();
                    notificationsListView = findViewById(R.id.notificationsList);

                    for (DocumentSnapshot snapDoc : queryDocumentSnapshots) {
                        if (snapDoc.getString("toUser").equals(user.getEmail())) {
                            //Log.d("email",snapDoc.getString("toUser")+" "+user.getEmail());
                            notificationsList.add(new NotificationObject(snapDoc.getId(), snapDoc.getString("fromName"), snapDoc.getString("fromUser"),
                                    snapDoc.getString("toUser"), snapDoc.getString("Message"), snapDoc.getString("sourceID"), snapDoc.getString("origin"),
                                    snapDoc.getBoolean("isRead"), snapDoc.getLong("timeAgo")));
                        }
                    }
                    notificationAdapter = new NotificationObjectAdapter(Notification.this, notificationsList);
                    notificationsListView.setAdapter(notificationAdapter);
                });
    }
}