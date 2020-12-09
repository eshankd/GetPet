package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;

public class Comments extends AppCompatActivity {

    private ImageView postPic;
    private EditText commentIn;
    private Button postComment;
    private Bitmap bitmap;

    private FirebaseFirestore fStore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private User user;

    private String TAG = "Comments";
    private String image;

    BottomNavigationView navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Log.d("COMM", "IT REACHED");

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.explore));

        postPic = findViewById(R.id.imageView11);
        commentIn = findViewById(R.id.commentIn);
        postComment = findViewById(R.id.commentBtn);

        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        user = User.getInstance();

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Comments.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Comments.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Comments.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Comments.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Comments.this, Profile.class));
                    break;
            }
            return true;
        });

        image = getIntent().getStringExtra("image");
        StorageReference picRef = storageRef.child("Storyboard Thumbnails/" + image + ".jpg");
        final File localFile;
        try {
            localFile = File.createTempFile(image, "jpg");

            picRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                postPic.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        postComment();
    }

    private void postComment() {

        String toUserEmail = "";
        String toUserName;

        Intent intent = getIntent();

        toUserEmail = getIntent().getStringExtra("toUserEmail");

        toUserName = getIntent().getStringExtra("toUserName");


        String finalToUserEmail = toUserEmail;

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = commentIn.getText().toString();

                CollectionReference docRef = fStore.collection("Notifications2");

                Long time = Calendar.getInstance().getTimeInMillis();

                Map<String, Object> commentData = new HashMap<>();
                commentData.put("Message", comment);
                commentData.put("fromName", user.getFullName()+" commented");
                commentData.put("fromUser", user.getEmail());
                commentData.put("toUser", finalToUserEmail);
                commentData.put("timeStamp", FieldValue.serverTimestamp());
                commentData.put("isRead", false);
                commentData.put("origin", "Storyboard Thumbnails");
                commentData.put("sourceID", image);
                commentData.put("timeAgo", time );


                docRef.add(commentData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d(TAG, "Comment posted");
                        Toast.makeText(Comments.this, " Comment sent to " + toUserName, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Comment not posted");
                    }
                });
            }
        });




    }

}