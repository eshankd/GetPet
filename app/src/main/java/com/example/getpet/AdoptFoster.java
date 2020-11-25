package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class AdoptFoster extends AppCompatActivity {

    BottomNavigationView navBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_adopt_foster);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(AdoptFoster.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(AdoptFoster.this, Explore.class));
                        break;
                    case "Adopt":
                        break;
                    case "Notifications":
                        startActivity(new Intent(AdoptFoster.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(AdoptFoster.this, Profile.class));
                        break;
                }
                return true;
            }
        });
        adoptFoster();
        findAHome();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        Log.d("petID", "D003");
        StorageReference reference = storage.getReference().child("Dog Thumbnails/" + "D003" + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile("D003", "jpg");
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                    ImageView vadoptedPet = findViewById(R.id.vadoptedPet);
                    vadoptedPet.setImageBitmap(bitmap);


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }









    }








    private void adoptFoster(){
        Button adoptFoster = findViewById(R.id.adoptfoster);
        adoptFoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }

    private void findAHome(){
        Button findahome = findViewById(R.id.findahome);
        findahome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,CreatePetProfile.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        auth.signOut();
        startActivity(new Intent(AdoptFoster.this,MainActivity.class));
        Toast.makeText(AdoptFoster.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
    }


}