package com.example.getpet;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterBy extends AppCompatActivity {

    BottomNavigationView navBar;
    String typeChosen, breedChosen, genderChosen;
    int ageChosen, AdoptFosterChosen;
    private FirebaseAuth auth;
    private ListView petListView;
    private PetObjectAdapter petAdapter;
    private User user;
    private FirebaseFirestore fStore;
    private String TAG = "PetsList";

    Map<String, Object> choice = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId(R.id.adopt);
        auth = FirebaseAuth.getInstance();
        user = User.getInstance();

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(FilterBy.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(FilterBy.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(FilterBy.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(FilterBy.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(FilterBy.this, Profile.class));
                    break;
            }
            return true;
        });

        final Spinner typeSpinner = findViewById(R.id.spinner1);
        final Spinner breedSpinner = findViewById(R.id.spinner2);
        final Spinner ageSpinner = findViewById(R.id.spinner3);
        final Spinner afSpinner = findViewById(R.id.spinner4);
        final Spinner genderSpinner = findViewById(R.id.spinner5);

        final String type[] = {"Dog", "Cat", "Bird"};
        final String dogs[] = {"Any", "Beagle", "Labrador", "Bichon Frise", "Maltese",
                "Great Dane", "Husky", "Shihtzu", "Rottweiler", "German Shepherd",
                "Bulldog", "Poodle", "Chihuahua", "Doberman", "Corgi", "Greyhound",
                "Saluki", "Pomeranion", "Golden Retriever", "Labrador"};
        final String cats[] = {"Any", "British Shorthair", "Persian Cat", "Maine Coon"};
        final String birds[] = {"Any", "African Parrot", "Eagle", "Falcon"};
        final String age[] = {"Any", "Less Than 1 Year", "1 Year", "2 Years", "3 Years", "4 Years", "5 Years", "6 Years and Above"};
        final String gender[] = {"Any", "Male", "Female"};
        final String af[] = {"Any", "Adopt", "Foster"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(FilterBy.this, android.R.layout.simple_spinner_dropdown_item, dogs);
                    typeChosen = "Dog";


//                    Query docref = fStore.collection("Dogs");
//
//                    docref.get()
//                            .addOnSuccessListener(queryDocumentSnapshots -> {
//                                int count = queryDocumentSnapshots.size();
//                                Log.d(TAG, Integer.toString(count));
//
//                                ArrayList<PetObject> petList = new ArrayList<PetObject>();
//                                petListView = findViewById(R.id.PetList);
//
//                                for (DocumentSnapshot snapDoc : queryDocumentSnapshots) {
//
//                                    petList.add(new PetObject(snapDoc.getString("ID"), snapDoc.getString("Name"), snapDoc.getString("Breed"), snapDoc.getString("Gender"), snapDoc.getLong("Age").intValue(), snapDoc.getString("Description")));
//                                }
//                            });


                    breedSpinner.setAdapter(adapter1);
                    breedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            breedChosen = dogs[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }
        });

        searchByFilter();
    }

    private void searchByFilter(){
        Button search = findViewById(R.id.search);
        search.setOnClickListener(v -> {

            Intent transfer = new Intent(FilterBy.this, PetsList.class);
            transfer.putExtra("type", typeChosen);
            transfer.putExtra("breed", breedChosen);
            transfer.putExtra("age", ageChosen);
            transfer.putExtra("gender", genderChosen);
            transfer.putExtra("adoptfoster", AdoptFosterChosen);
            startActivity(transfer);

        });
    }
}
