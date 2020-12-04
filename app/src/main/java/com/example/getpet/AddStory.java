package com.example.getpet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.FirestoreGrpc;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddStory extends AppCompatActivity {

    private EditText captionIn;
    private Button chooseImage;
    private Button uploadImage;
    private ImageView postImage;
    private Uri ImageUri;

    private FirebaseFirestore fStore;
    private StorageReference storageReference;
    BottomNavigationView navBar;
    private String TAG = "AddStory";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.storyboard));

        chooseImage = findViewById(R.id.chooseButton);
        uploadImage = findViewById(R.id.uploadButton);
        postImage = findViewById(R.id.postImage);

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
      //  upload();
        choose();

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


    private void choose(){

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChosen();
            }
        });


    }
//    private void upload(){
//
//        final String randomKey = UUID.randomUUID().toString();
//        StorageReference thisReference = storageReference.child("Storyboard Thumbnails/" + randomKey);
//
//        thisReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(AddStory.this, "Upload Success", Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(AddStory.this, "Upload Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }




//    ActivityResultLauncher<String> myActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    ImageUri = uri;
//                    postImage.setImageURI(ImageUri);
//                }
//            });


    private void openFileChosen(){



        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(i);
        //myActivityResultLauncher.launch(i.toString());


    }



    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){


          //  ImageUri = result.getUri();

            postImage.setImageURI(ImageUri);
        }


    }



}