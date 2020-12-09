package com.example.getpet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
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
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreatePetProfileSubmit extends AppCompatActivity {


    BottomNavigationView navBar;
    private FirebaseFirestore fStore;
    private User user;
    private FirebaseAuth auth;
    private CalendarView mCalendarView;
    private String TAG = "CreatePetProfileSubmit";
    private EditText descriptionIn;
    private DocumentReference tempRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    int dayIn, monthIn, yearIn;
    String transferredName;
    String transferredBreed;
    String transferredGender;
    String transferredType;

    Period diff;

    private byte[] byteArray;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet_profile_submit);

        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        user = User.getInstance();

        auth = FirebaseAuth.getInstance();

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);



        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(CreatePetProfileSubmit.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(CreatePetProfileSubmit.this, Profile.class));
                    break;
            }
            return true;
        });


        mCalendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dayIn = dayOfMonth;
            monthIn = month;
            yearIn = year;

            LocalDate loc = LocalDate.of(yearIn,monthIn,dayIn);
            LocalDate now = LocalDate.now();
            diff = Period.between(loc,now);
        });


        submitForm();
    }

    private void submitForm() {

        Button submit = findViewById(R.id.submitPetProfile);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                descriptionIn = findViewById(R.id.description);

                final String description = descriptionIn.getText().toString();

                transferredName = getIntent().getStringExtra("name");
                transferredBreed = getIntent().getStringExtra("breed");
                transferredGender = getIntent().getStringExtra("gender");
                transferredType = getIntent().getStringExtra("type");
                byteArray = getIntent().getByteArrayExtra("picture");


                CollectionReference docref = fStore.collection("Pets");
                Map<String, Object> petProfile = new HashMap<>();
                petProfile.put("Name", transferredName);
                petProfile.put("Gender", transferredGender);
                petProfile.put("Type",transferredType);
                petProfile.put("Age", diff.getYears());
                petProfile.put("Breed", transferredBreed);
                petProfile.put("Description", description);
                petProfile.put("userEmail", user.getEmail());

                docref.add(petProfile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        tempRef = documentReference;
                        Log.d(TAG, "Profile Created Success");
                        upload(documentReference);
                        Toast.makeText(CreatePetProfileSubmit.this, transferredName + " is up for adoption!", Toast.LENGTH_SHORT).show();

                        DocumentReference usersDocRef = fStore.collection("Users").document(user.getUserID());
                        usersDocRef.update("PetsOwned", FieldValue.arrayUnion(tempRef.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                user.adoptPet(tempRef.getId());

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Profile not created");
                    }
                });

                Long time = Calendar.getInstance().getTimeInMillis();
                CollectionReference notificationCollection = fStore.collection("Notifications2");
                Map<String, Object> notify = new HashMap<>();
                notify.put("fromUser", "fromUser");
                notify.put("fromName",transferredName);
                notify.put("toUser", user.getEmail());
                notify.put("Message", "is up for adoption!");
                notify.put("sourceID", tempRef);
                notify.put("origin", "Pet Images");
                notify.put("isRead", false);
                notify.put("timeStamp", FieldValue.serverTimestamp());
                notify.put("timeAgo", time);


                notificationCollection.add(notify).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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


                String userID  = (auth.getCurrentUser()).getUid();
                DocumentReference docRef = fStore.collection("Pets").document(userID);
                docRef.update("Pets Owned", FieldValue.arrayUnion(user.getEmail()));



                startActivity(new Intent(CreatePetProfileSubmit.this, AdoptFoster.class));
            }

        });


    }

    private void upload(DocumentReference docRef){

        StorageReference storyThumbRef = storageRef.child("Pet Images/" + docRef.getId() + ".jpg");

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storyThumbRef.putBytes(byteArray);
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
}









