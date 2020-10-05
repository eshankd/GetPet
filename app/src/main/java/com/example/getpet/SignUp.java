package com.example.getpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore fstore;
    private String TAG = "Signup";
    private String userid;
    private EditText fNameIn;
    private EditText lNameIn;
    private EditText emailIn;
    private EditText passwordIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        signup();
    }

    private void signup() {
        Button signupBTN = findViewById(R.id.signup);
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fNameIn = findViewById(R.id.signUpFname);
                lNameIn = findViewById(R.id.signUpLname);
                emailIn = findViewById(R.id.signUpEmail);
                passwordIn = findViewById(R.id.signUpPassword);

                final String email = emailIn.getText().toString();
                final String password = passwordIn.getText().toString();
                final String fName = fNameIn.getText().toString();
                final String lName = lNameIn.getText().toString();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            userid = auth.getCurrentUser().getUid();
                            DocumentReference docref = fstore.collection("users").document(userid);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fName);
                            user.put("lName",lName);
                            user.put("email",email);
                            user.put("pwd",password);
                            docref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+userid);
                                }
                            });
                            startActivity(new Intent(SignUp.this, AdoptFoster.class));
                        }
                        else{
                            Query emailQuery = fstore.collection("users").whereEqualTo("email",email);
                            emailQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "Email-id already in use", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });

            }
        });
    }
}