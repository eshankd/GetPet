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
import com.google.firebase.auth.FirebaseUser;

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

    private void login() {

        Button loginBTN = findViewById(R.id.login);
        loginBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(MainActivity.this, AdoptFoster.class));
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Account doesn't exit, please signup", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private boolean validateForm(String email, String password) {
        if(email == null || email.trim().isEmpty())
            return false;
        return password != null && !password.trim().isEmpty();
    }



    private void loginAsGuest(){
        TextView loginAsGuest = findViewById(R.id.loginasguest);
        loginAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdoptFoster.class));
            }
        });
    }

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