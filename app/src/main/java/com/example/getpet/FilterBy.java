package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.Toast;
import java.lang.reflect.Array;

public class FilterBy extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);

        searchByFilter();


        final Spinner typeSpinner = findViewById(R.id.spinner1);
        final Spinner breedSpinner = findViewById(R.id.spinner2);
        final Spinner ageSpinner = findViewById(R.id.spinner3);
        final Spinner afSpinner = findViewById(R.id.spinner4);


        final String type[]={"Dog","Cat","Bird"};
        final String dogs[] = {"Any","Beagle","Labrador","Bichon Frise","Maltese","Great Dane","Husky","Shihtzu","Rottweiler","German Shepherd","Bulldog","Poodle","Chihuahua","Doberman","Corgi","Greyhound","Saluki","Pomeranion"};
        final String cats[] = {"Any","British Shorthair","Persian Cat","Maine Coon"};
        final String birds[] = {"Any","African Parrot","Eagle","Falcon"};
        final String age[] = {"Less Than 1 Year","1 Year","2 Years","3 Years","4 Years","5 Years","6 Years"};
        final String af[] = {"Any","Adopt","Foster"};


        ArrayAdapter<String> adapter  = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, dogs);
                    breedSpinner.setAdapter(adapter1);
                }
                if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, cats);
                    breedSpinner.setAdapter(adapter2);
                }

                if (position == 2) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, birds);
                    breedSpinner.setAdapter(adapter3);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,age);
        ageSpinner.setAdapter(adapter4);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,af);
        afSpinner.setAdapter(adapter5);

        afSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void searchByFilter(){
        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(FilterBy.this,DogsList.class));
            }
        });
    }
}