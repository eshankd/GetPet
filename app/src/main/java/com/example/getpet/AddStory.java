package com.example.getpet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddStory extends AppCompatActivity {

    private EditText captionIn;
    private Button chooseImage;
    private ImageView postImage;
    private Bitmap bitmap;
    boolean picUp;
    private FirebaseFirestore fStore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    BottomNavigationView navBar;
    private String TAG = "AddStory";
    private User user;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        try {
                            openFileChosen(data);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.storyboard));

        chooseImage = findViewById(R.id.chooseButton);
        postImage = findViewById(R.id.postImageIn);

        picUp = false;

        user = User.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

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
        choose();
    }

    private void postStory() {

        Button postStoryButton = findViewById(R.id.post);
        postStoryButton.setOnClickListener(v -> {

            captionIn = findViewById(R.id.inputPost);
            String caption = captionIn.getText().toString();

            Long time = Calendar.getInstance().getTimeInMillis();

            CollectionReference docref = fStore.collection("Posts");
            Map<String, Object> post = new HashMap<>();
            post.put("Caption", caption);
            post.put("Likes", new ArrayList<>());
            post.put("Name", user.getFullName());
            post.put("userEmail", user.getEmail());
            post.put("timeStamp", FieldValue.serverTimestamp());
            post.put("timeAgo", time);


            docref.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    if(picUp)
                        upload(documentReference);
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

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                someActivityResultLauncher.launch(chooserIntent);
            }
        });
    }

    private void openFileChosen(Intent data) throws FileNotFoundException {
        InputStream inputStream = getContentResolver().openInputStream(data.getData());
        bitmap = BitmapFactory.decodeStream(inputStream);
        postImage.setImageBitmap(bitmap);

        picUp = true;
    }


    private void upload(DocumentReference docRef){
        StorageReference storyThumbRef = storageRef.child("Storyboard Thumbnails/" + docRef.getId() + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storyThumbRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }
}