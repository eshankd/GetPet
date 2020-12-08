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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userPicRef;
    private User user;
    private TextView nameOUT;
    private TextView emailOUT;
    private TextView petsOwnedOUT;
    private ImageView profilePictureOut;
    private Bitmap bitmap;
    private byte[] byteArray;
    private String TAG = "Profile";

    private TextView petsOwnedLabel;
    private TextView emailLabel;

    private Button loginButton;
    private Button editUserPicBtn;

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
        setContentView(R.layout.activity_profile);

        user = User.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        userPicRef = storageRef.child("User Images/" + user.getUserID() + ".jpg");

        loginButton = findViewById(R.id.loginButton);
        editUserPicBtn = findViewById(R.id.chooseUserPic);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.profile));

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Profile.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Profile.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Profile.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Profile.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Profile.this, Profile.class));
                    break;
            }
            return true;
        });

        nameOUT = findViewById(R.id.userProfileFName);
        emailOUT = findViewById(R.id.userProfileEmail);
        profilePictureOut = findViewById(R.id.imageView9);
        petsOwnedOUT = findViewById(R.id.petsOwnedOut);

        if (user.getPetsOwned() == -1){
            nameOUT.setText(user.getFullName());
            emailLabel = findViewById(R.id.emailLabel);
            petsOwnedLabel = findViewById(R.id.petsOwned);
            emailLabel.setVisibility(View.GONE);
            petsOwnedLabel.setVisibility(View.GONE);
        }
        else{
            nameOUT.setText(user.getFullName());
            emailOUT.setText(user.getEmail());
            petsOwnedOUT.setText(Integer.toString(user.getPetsOwned()));
            loginButton.setVisibility(View.GONE);



            final File localFile;
            try {
                localFile = File.createTempFile(user.getUserID(), "jpg");
                userPicRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    profilePictureOut.setImageBitmap(bitmap);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        signOut();
        loginButton();
        editUserPicButton();
    }

    private void editUserPicButton() {
        editUserPicBtn.setOnClickListener(new View.OnClickListener() {
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
        profilePictureOut.setImageBitmap(bitmap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

//        picUp = true;

        UploadTask uploadTask = userPicRef.putBytes(byteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Picture not uploaded");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Picture uploaded");
            }
        });
    }


    private void signOut() {
        Button signOutBTN = findViewById(R.id.signOut);
        signOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Profile.this, MainActivity.class));
                Toast.makeText(Profile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginButton() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
    }
}
