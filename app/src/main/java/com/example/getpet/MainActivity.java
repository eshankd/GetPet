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
    private FirebaseAuth mAuth;
    public String status; //if guest user

    private String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        loginAsGuest();
        signUp();
        login();
    }


    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //checks if user is already signed in

        //updateUI(currentUser);  change the UI accordingly
    }

    private void login() {

        Button loginBTN = (Button) findViewById(R.id.login);
        loginBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                emailIN = (EditText) findViewById(R.id.userEmail);
                passwordIN = (EditText) findViewById(R.id.userPassword);

                Log.d(TAG, "login: " + emailIN);

//                if(!validateForm()){
//                    return;
//                }
                mAuth.signInWithEmailAndPassword(emailIN.getText().toString(), passwordIN.getText().toString()).addOnCompleteListener( MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void loginAsGuest(){
        TextView loginAsGuest = (TextView) findViewById(R.id.loginasguest);
        loginAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AdoptFoster.class));
            }
        });
    }

    private void signUp(){
        Button signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });
    }



}