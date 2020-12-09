package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailIN;
    private EditText passwordIN;
    private FirebaseAuth auth;
    public String status; //if guest user

    private String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        loginAsGuest();
        signUp();
        login();

    }


    @Override
    public void onStart(){
        super.onStart();
    }

//function that lets thr user login to use the functionalities of the application
    private void login() {

        Button loginBTN = findViewById(R.id.login);
        loginBTN.setOnClickListener(v -> {
            emailIN = findViewById(R.id.userEmail);
            passwordIN = findViewById(R.id.userPassword);

            String email = emailIN.getText().toString();
            String password = passwordIN.getText().toString();

            Log.d("email", email);
            Log.d("pass", password);

            if(!validateForm(email, password)){
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( MainActivity.this, task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    startActivity(new Intent(MainActivity.this, AdoptFoster.class));
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Account doesn't exit, please signup", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
// function that validates the form to see if the user has entered all the details
    private boolean validateForm(String email, String password) {
        if(email == null || email.trim().isEmpty())
            return false;
        return password != null && !password.trim().isEmpty();
    }


// function to let the user login as a guest
    private void loginAsGuest(){
        TextView loginAsGuest = findViewById(R.id.loginasguest);
        loginAsGuest.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AdoptFoster.class)));
    }
// function which direct the user to the signup pages to create their account
    private void signUp(){
        Button signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));

            }
        });
    }



}