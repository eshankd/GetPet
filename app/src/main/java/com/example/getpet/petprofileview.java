package com.example.getpet;

import androidx.annotation.ColorInt;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class petprofileview extends AppCompatActivity {

    BottomNavigationView navBar;

    private String transferredName;
    private int transferredAge;
    private String transferredBreed;
    private String transferredGender;
    private String transferredPetID;
    private String trasferredDescription;
    private String  transferredUserEmail;
    private FirebaseFirestore fStore;
    private TextView petName;
    private TextView petAge;
    private TextView petBreed;
    private TextView petGender;
    private TextView petDescription;
    private ImageView petImage;
    private User user;
    private String TAG = "petprofileview";
    Button adopt;
    Button viewinARBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileview);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);
        user = User.getInstance();
        fStore = FirebaseFirestore.getInstance();

        petName = findViewById(R.id.petName);
        petAge = findViewById(R.id.petProfilePetAge);
        petBreed = findViewById(R.id.petProfilePetBreed);
        petGender = findViewById(R.id.petProfilePetGender);
        petDescription = findViewById(R.id.petDescription);
        adopt = findViewById(R.id.adoptButton);
        viewinARBtn = findViewById(R.id.viewinAR);

        transferredName = getIntent().getStringExtra("petName");
        transferredUserEmail = getIntent().getStringExtra("userEmail");
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
        StorageReference reference = storage.getReference().child("Pet Images/" + transferredPetID + ".jpg");

        final File localFile;
        try {
            localFile = File.createTempFile(transferredPetID, "jpg");
            reference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                petImage = findViewById(R.id.petImage);
                petImage.setImageBitmap(bitmap);


            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        navBar.setOnNavigationItemSelectedListener(item -> {
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
        });

        if(user.getPetsOwned() == -1)
        {
            adopt.setVisibility(View.VISIBLE);
            adopt.setBackground(getDrawable(R.drawable.disabledbutton));


        }

        else if(!user.getEmail().equals(transferredUserEmail))
        {
            adopt.setVisibility(View.VISIBLE);
        }


        if(transferredPetID.equals("3LHKy3jlYFuXfVPCrLSs") ||
                transferredPetID.equals("5w68ws7hpEAUTLvXrLH3") ||
                transferredPetID.equals("jxI6GLCzebh1IZwOFKLq") ||
                transferredPetID.equals("zeXDKDimEBQM5m2Irq1s"))
        {
            viewinARBtn.setVisibility(View.VISIBLE);
        }

        viewinAR();
        adoptButton();

    }

    private void viewinAR(){

        viewinARBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(petprofileview.this, ARView.class);
                intent.putExtra("petID", transferredPetID);
                startActivity(intent);

            }
        });

    }

    private void adoptButton(){

        Dialog mDialog;
        mDialog = new Dialog(this);

        adopt.setOnClickListener(v -> {
            if(user.getPetsOwned() == -1)
            {
                Toast.makeText(this, "Please Login to Adopt a Pet", Toast.LENGTH_SHORT).show();
                return;
            }

            if(user.isOwned(transferredPetID))
            {
                Toast.makeText(this, "You already own this Pet", Toast.LENGTH_SHORT).show();
                return;
            }

            mDialog.setContentView(R.layout.adoptpopup);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();

            DocumentReference docRef = fStore.collection("Users").document(user.getUserID());
            docRef.update("PetsOwned", FieldValue.arrayUnion(transferredPetID)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    user.adoptPet(transferredPetID);

                }
            });


            Long time = Calendar.getInstance().getTimeInMillis();
            CollectionReference docref = fStore.collection("Notifications2");
            Map<String, Object> post = new HashMap<>();
            post.put("fromUser", user.getEmail());
            post.put("fromName",user.getFullName());
            post.put("toUser", transferredUserEmail);
            post.put("Message", "has adopted your pet " + transferredName + "!");
            post.put("sourceID", transferredPetID);
            post.put("origin", "Pet Images");
            post.put("isRead", false);
            post.put("timeStamp", FieldValue.serverTimestamp());
            post.put("timeAgo", time);


            docref.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.d(TAG, "Notification Sent!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding post");
                }
            });


            Map<String, Object> postto = new HashMap<>();
            postto.put("fromUser", transferredUserEmail);
            postto.put("fromName","Congratulations");
            postto.put("toUser", user.getEmail());
            postto.put("Message", "on your new pet " + transferredName+  "!");
            postto.put("sourceID", transferredPetID);
            postto.put("origin", "Pet Images");
            postto.put("isRead", false);
            postto.put("timeStamp", FieldValue.serverTimestamp());
            postto.put("timeAgo", time);


            docref.add(postto).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.d(TAG, "Notification Sent!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error");
                }
            });



            Button okay = mDialog.findViewById(R.id.okayButton);
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

        });
    }





}