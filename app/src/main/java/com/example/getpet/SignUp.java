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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.valueOf;

public class SignUp extends AppCompatActivity {


    //declaring all the variables being used in the code
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private String TAG = "Signup";
    private String userid;
    private EditText fNameIn;
    private EditText lNameIn;
    private EditText emailIn;
    private TextView notMatch;
    private EditText passwordIn;
    private EditText confirmPassword;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        signup();
    }

// Function that lets the user signup with thier details and create an account
    private void signup() {
        Button signupBTN = findViewById(R.id.signup);

        signupBTN.setOnClickListener(v -> {
            fNameIn = findViewById(R.id.signUpFname);
            lNameIn = findViewById(R.id.signUpLname);
            emailIn = findViewById(R.id.signUpEmail);
            passwordIn = findViewById(R.id.signUpPassword);
            confirmPassword = findViewById(R.id.confirmPassword);
            notMatch = findViewById(R.id.notMatch);

            if (!passwordIn.getText().toString().equals(confirmPassword.getText().toString())) {
                notMatch.setText("Passwords do not match!");
            }

            else if(fNameIn.getText().toString() == "" || lNameIn.getText().toString() == "" || emailIn.getText().toString() == ""|| passwordIn.getText().toString() == "")
            {
                notMatch.setText("Please fill in all the details");
            }

            else {

                final String email = emailIn.getText().toString();
                final String password = passwordIn.getText().toString();
                final String fName = fNameIn.getText().toString();
                final String lName = lNameIn.getText().toString();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        DocumentReference docref = fStore.collection("Users").document(userid);
                        Map<String, Object> user = new HashMap<>();
                        user.put("FirstName", fName);
                        user.put("LastName", lName);
                        user.put("Email", email);
                        user.put("PetsOwned", new ArrayList<>());
                        docref.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: user Profile is created for " + userid)).addOnFailureListener(e -> Log.w(TAG, "Profile not created"));
                        startActivity(new Intent(SignUp.this, AdoptFoster.class));
                    } else {
                        Query emailQuery = fStore.collection("Users").whereEqualTo("Email", email);
                        emailQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Email-ID already in use", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

            }
        });
    }
}