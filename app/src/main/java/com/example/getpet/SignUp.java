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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private String TAG = "Signup";
    private String userid;
    private EditText fNameIn;
    private EditText lNameIn;
    private EditText emailIn;
    private EditText passwordIn;
    private GoogleSignInClient gSignInClient;
    private Button gSignUpBTN;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        gSignUpBTN = findViewById(R.id.googleSignIn);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        gSignInClient = GoogleSignIn.getClient(this, gso);

        signup();
        //googleSignUp();
    }

//    private void googleSignUp(){
//
//        gSignUpBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signInIntent = gSignInClient.getSignInIntent();
//                startActivity(signInIntent);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try{
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle" + account.getId());
//                firebaseAuthWithGoogle(account.getId());
//            } catch( ApiException e){
//                Log.w(TAG, "Google sign in failed", e);
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(String idToken){
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Log.d(TAG, "signInWithCredential : success");
//                            Toast.makeText(SignUp.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Log.w(TAG, "signInWithCredential : fail", task.getException());
//
//                        }
//                    }
//                });
//    }

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
                            userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                            DocumentReference docref = fStore.collection("users").document(userid);
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
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Profile not created");
                                }
                            });
                            startActivity(new Intent(SignUp.this, AdoptFoster.class));
                        }
                        else{
                            Query emailQuery = fStore.collection("users").whereEqualTo("email",email);
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