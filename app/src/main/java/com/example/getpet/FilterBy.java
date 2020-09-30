package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterBy extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);

        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

//        Spinner spinner2 = findViewById(R.id.spinner2);
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(adapter);
//        spinner2.setOnItemSelectedListener(this);
//
//        Spinner spinner3 = findViewById(R.id.spinner3);
//        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner3.setAdapter(adapter);
//        spinner3.setOnItemSelectedListener(this);
//
//        Spinner spinner4 = findViewById(R.id.spinner4);
//        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner4.setAdapter(adapter);
//        spinner4.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}