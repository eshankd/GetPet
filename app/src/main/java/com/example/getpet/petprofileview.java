package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class petprofileview extends AppCompatActivity {

    BottomNavigationView navBar;

    private String transferredName;
    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;
    private String transferredPetID;
    private String trasferredDescription;

    private TextView petName;
    private TextView petAge;
    private TextView petBreed;
    private TextView petGender;
    private TextView petDescription;
    private ImageView petImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileview);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petProfilePetAge);
        petBreed = findViewById(R.id.petProfilePetBreed);
        petGender = findViewById(R.id.petProfilePetGender);
        petDescription = findViewById(R.id.petDescription);

        transferredName = getIntent().getStringExtra("petName");
        transferredAge = getIntent().getIntExtra("petAge", 0);
        transferredBreed = getIntent().getStringExtra("petBreed");
        transferredGender = getIntent().getStringExtra("petGender");
        transferredPetID = getIntent().getStringExtra("petID");
        trasferredDescription = getIntent().getStringExtra("petDescription");

        petName.setText(transferredName);
        petAge.setText(Integer.toString(transferredAge));
        petBreed.setText(transferredBreed);
        petGender.setText(transferredGender);
        petDescription.setText(trasferredDescription);



        FirebaseStorage storage = FirebaseStorage.getInstance();
        Log.d("petID", transferredPetID);
        StorageReference reference = storage.getReference().child("Dog Thumbnails/" + transferredPetID + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(transferredPetID, "jpg");
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                    petImage = findViewById(R.id.petImage);
                    petImage.setImageBitmap(bitmap);


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Storyboard":
                        startActivity(new Intent(petprofileview.this, Storyboard.class));
                        break;
                    case "Explore":
                        startActivity(new Intent(petprofileview.this, Explore.class));
                        break;
                    case "Adopt":
                        startActivity(new Intent(petprofileview.this, AdoptFoster.class));
                        break;
                    case "Notifications":
                        startActivity(new Intent(petprofileview.this, Notification.class));
                        break;
                    case "Profile":
                        startActivity(new Intent(petprofileview.this, Profile.class));
                        break;
                }
                return true;
            }
        });

        virtuallyAdopt();
        adoptButton();
    }

    private void virtuallyAdopt(){
        Button virtuallyadopt = findViewById(R.id.viewinAR);
        virtuallyadopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(petprofileview.this,ARView.class));
            }
        });
    }

    private void adoptButton(){
        Button adopt = findViewById(R.id.adoptButton);
        Dialog mDialog;
        mDialog = new Dialog(this);

        adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.setContentView(R.layout.adoptpopup);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
                mDialog.show();

                Button okay = mDialog.findViewById(R.id.okayButton);
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });

            }
        });
    }





}