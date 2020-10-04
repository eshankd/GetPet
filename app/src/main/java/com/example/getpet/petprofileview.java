package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class petprofileview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofileview);

        virtuallyAdopt();
    }

    private void virtuallyAdopt(){
        Button virtuallyadopt = findViewById(R.id.virtuallyadopt);
        virtuallyadopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(petprofileview.this,ARView.class));
            }
        });
    }
}