package com.example.getpet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

//The Comments class is responsible for handling the functionality behind the acitivity that allows the user to add a comment on a specific post from the storyboard

public class CreatePetProfile1 extends AppCompatActivity {


    BottomNavigationView navBar;
    FirebaseAuth auth;
    String gender;
    EditText petName;
    RadioGroup radioGender;
    Button nextButton;
    String typeChosen,breedChosen;
    Spinner typeSpinnerIn,breedSpinnerIn;

    private Button chooseImage;
    Bitmap bitmap;
    private ImageView petImage;
    private byte[] byteArray;

    boolean picUp;

    //Function that launches the gallery app to choose which image to use for the pets picture
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
        setContentView(R.layout.activity_create_pet_profile1);


        petName = findViewById(R.id.petNameIn);
        radioGender = findViewById(R.id.radioGender);
        nextButton = findViewById(R.id.nextButton);
        typeSpinnerIn  = findViewById(R.id.typeSpinnerIn);
        breedSpinnerIn = findViewById(R.id.breedSpinnerIn);
        typeSpinnerIn = findViewById(R.id.typeSpinnerIn);

        picUp = false;

        chooseImage = findViewById(R.id.choose);
        petImage = findViewById(R.id.uploadPetImage2);

        auth = FirebaseAuth.getInstance();

        //navigation bar that is present throughout the app
        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(CreatePetProfile1.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(CreatePetProfile1.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(CreatePetProfile1.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(CreatePetProfile1.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(CreatePetProfile1.this, Profile.class));
                    break;
            }
            return true;
        });

        String type[] = {"Dog", "Cat", "Bird"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinnerIn.setAdapter(typeAdapter);

        //function that populates the breed spinner depending on what type of pet is chosen by the user
        typeSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    typeChosen = type[position];

                    ArrayAdapter<CharSequence> dogBreedAdapter = ArrayAdapter.createFromResource(CreatePetProfile1.this,R.array.dogbreeds, android.R.layout.simple_spinner_item);
                    dogBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinnerIn.setAdapter(dogBreedAdapter);
                    breedSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                else if (position == 1) {

                    ArrayAdapter<CharSequence> catBreedAdapter = ArrayAdapter.createFromResource(CreatePetProfile1.this,R.array.catbreeds, android.R.layout.simple_spinner_item);
                    catBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    breedSpinnerIn.setAdapter(catBreedAdapter);
                    breedSpinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nextForm();
        choose();
    }

//    Function that contains an onClickListener
    public void nextForm(){

        petName = findViewById(R.id.petNameIn);
        radioGender = findViewById(R.id.radioGender);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!picUp){
                    Toast.makeText(CreatePetProfile1.this, "Please upload a picture", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String name = petName.getText().toString();

                    if (radioGender.getCheckedRadioButtonId() == R.id.MaleRadio)
                        gender = "Male";
                    else if (radioGender.getCheckedRadioButtonId() == R.id.FemaleRadio)
                        gender = "Female";

                    Intent i = new Intent(CreatePetProfile1.this, CreatePetProfileSubmit.class);

                    i.putExtra("name", name);
                    i.putExtra("gender", gender);
                    i.putExtra("breed", breedChosen);
                    i.putExtra("type",typeChosen);
                    i.putExtra("picture",byteArray);
                    startActivity(i);
                }
            }
        });
    }

    //Function that contains onClickListener for choose Image button that allows the user to select an image from their phone storage to upload
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

    //Function that loads the image the user selects from local storage
    private void openFileChosen(Intent data) throws FileNotFoundException {

        InputStream inputStream = getContentResolver().openInputStream(data.getData());
        bitmap = BitmapFactory.decodeStream(inputStream);
        petImage.setImageBitmap(bitmap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        picUp = true;
    }

}