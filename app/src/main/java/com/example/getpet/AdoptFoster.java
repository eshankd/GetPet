package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdoptFoster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_foster);

        adoptFoster();
        findAHome();
    }

    private void adoptFoster(){
        Button adoptFoster = findViewById(R.id.adoptfoster);
        adoptFoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }

    private void findAHome(){
        Button findahome = findViewById(R.id.findahome);
        findahome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptFoster.this,FilterBy.class));
            }
        });
    }


}