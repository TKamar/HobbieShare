package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class activity_find_by_location extends AppCompatActivity {

    private ImageButton logo;
    double[] myCoordinates = new double[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_location);

        findViews();

        Bundle locationData = getIntent().getExtras();
        if (locationData != null) {
            myCoordinates[0] = locationData.getDouble("lat");
            myCoordinates[1] = locationData.getDouble("lon");
        }
        ZoomOnMap(myCoordinates[0], myCoordinates[1]);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });
    }

    private void findViews() {
        logo = findViewById(R.id.location_screen_logo);
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null){
            finish();
            startActivity(intent);
        }

    }

    /**
     * Callback functions
     */

    Callback_Map callback_map = (lat, lon) -> {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(myMap -> {
            LatLng coordinates = new LatLng(lat, lon);
            myMap.clear();
            myMap.addMarker(new MarkerOptions().position(coordinates).title("Player Location"));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15), 300, null);
        });
    };

    public void ZoomOnMap(double lat, double lon) {
        callback_map.mapClicked(lat, lon);
    }

}