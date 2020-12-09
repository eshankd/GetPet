package com.example.getpet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Explore extends AppCompatActivity {

    BottomNavigationView navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        navBar = findViewById(R.id.bottom_navbar);
        navBar.setSelectedItemId((R.id.explore));

        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.toString()) {
                case "Storyboard":
                    startActivity(new Intent(Explore.this, Storyboard.class));
                    break;
                case "Explore":
                    startActivity(new Intent(Explore.this, Explore.class));
                    break;
                case "Adopt":
                    startActivity(new Intent(Explore.this, AdoptFoster.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(Explore.this, Notification.class));
                    break;
                case "Profile":
                    startActivity(new Intent(Explore.this, Profile.class));
                    break;
            }
            return true;
        });


        afroPets();
        shampooch();
        cityvetclinic();
        petlovers();
        allGrooming();
        allHotels();


//        searchView = (SearchView) findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                webView.loadUrl("https://www.google.come/search?q= "+ searchView.getQuery());
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

    }





    private void afroPets() {

        ImageView afroPetsImage = findViewById(R.id.afroPets);
        afroPetsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://g.page/afro_pets?share";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void shampooch() {

        ImageView shampooch = findViewById(R.id.shampooch);
        shampooch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://goo.gl/maps/S5EbgWBcZ3LtgqQj6";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void cityvetclinic() {

        ImageView cityvetclinic = findViewById(R.id.cityvetclinic);
        cityvetclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://g.page/thecityvetmirdif?share";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void petlovers() {

        ImageView petlovers = findViewById(R.id.petlovers);
        petlovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://g.page/petloversvetclinic?share";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void allGrooming(){

        ImageView allGrooming = findViewById(R.id.allGrooming);
        allGrooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?safe=strict&tbs=lf:1,lf_ui:10&tbm=lcl&sxsrf=ALeKk00VwEcRJpHFko8wIiq8rd-LImJ0vA:1606733772132&q=grooming+places+in+dubai&rflfq=1&num=10&sa=X&ved=2ahUKEwio3_HdjartAhVOUMAKHXsSDUUQjGp6BAgDEFk&biw=1920&bih=937#rlfi=hd:;si:;mv:[[25.2675823,55.4506368],[25.021045400000002,55.112381799999994]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!1m4!1u16!2m2!16m1!1e1!1m4!1u16!2m2!16m1!1e2!2m1!1e2!2m1!1e16!2m1!1e3!3sIAE,lf:1,lf_ui:10";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void allHotels(){

        ImageView allHotels = findViewById(R.id.allHotels);
        allHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.google.com/search?safe=strict&tbs=lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk03GsxL2UbGTZMDW0x3bZ2K78JJjDw:1606733936146&q=boarding+for+pets+places+in+dubai&rflfq=1&num=10&sa=X&ved=2ahUKEwjtpYysjqrtAhVSZcAKHWmXA9IQjGp6BAgLEHM&biw=1920&bih=937#rlfi=hd:;si:;mv:[[25.2351756,55.479967699999996],[24.967624900000004,55.1220657]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!1m4!1u2!2m2!2m1!1e1!1m4!1u16!2m2!16m1!1e1!1m4!1u16!2m2!16m1!1e2!2m1!1e2!2m1!1e16!2m1!1e3!3sIAE,lf:1,lf_ui:2";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });





    }


}