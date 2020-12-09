package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//The MainActivity class is responsible for handling the functionality behind the activity that the user sees when they are presented with the option to sign in, sign up, or log in as a guest

public class MainActivity extends AppCompatActivity {

    private EditText emailIN;
    private EditText passwordIN;
    private FirebaseAuth auth;

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

    //Function that contains the onClickListener to submit the login details entered by the user and validates the login
    private void login() {

        Button loginBTN = findViewById(R.id.login);
        loginBTN.setOnClickListener(v -> {
            emailIN = findViewById(R.id.userEmail);
            passwordIN = findViewById(R.id.userPassword);

            String email = emailIN.getText().toString();
            String password = passwordIN.getText().toString();

            if(!validateForm(email, password)){
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, AdoptFoster.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Account doesn't exit, please signup", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    //Function that validates input from user
    private boolean validateForm(String email, String password) {
        if(email == null || email.trim().isEmpty())
            return false;
        return password != null && !password.trim().isEmpty();
    }

    //Function that allows the user to login as a guest with limited functionality
    private void loginAsGuest(){
        TextView loginAsGuest = findViewById(R.id.loginasguest);
        loginAsGuest.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AdoptFoster.class)));
    }

    //Function that takes the user to the signup page
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