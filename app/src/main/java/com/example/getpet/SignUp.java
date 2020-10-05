package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private String TAG = "Signup";
    private EditText Fname;
    private EditText Lname;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        signup();
    }

    private void signup() {
        Button signupBTN = findViewById(R.id.signup);
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fname = findViewById(R.id.signUpFname);
                Lname = findViewById(R.id.signUpLname);
                email = findViewById(R.id.signUpEmail);
                password = findViewById(R.id.signUpPassword);

                String emailIn = email.getText().toString();
                String passwordIn = password.getText().toString();
                String FnameIn = Fname.getText().toString();
                String LnameIn = Lname.getText().toString();

                auth.createUserWithEmailAndPassword(emailIn,passwordIn).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, AdoptFoster.class));

                        }
                        else{
                            Toast.makeText(SignUp.this, "Account creating unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}