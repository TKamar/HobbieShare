package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.Fragments.Fragment_Map;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class activity_create_new_event extends AppCompatActivity implements OnMapReadyCallback {

    private EditText eventTitle, eventDescription, lat, lon;
    private Spinner eventMainType, eventInnerType;
    private MapView myMap;
    private Fragment_Map map_fragment;
    private GoogleMap gMap;
    private Marker marker;
    private Callback_Map callback_map;
    private ImageButton logo, goBack;
    private double doLat = 0, doLon = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_event);
        findViews();
        initFragments();
        Bundle data = getIntent().getExtras();
        Log.d("data of bundle", "onCreate: data " + data);

        if (data != null) {
            doLat = Double.valueOf(data.getString("lat"));
            doLon = Double.valueOf(data.getString("lon"));
        }
        onMapReady(gMap);


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });

    }





    public void findViews() {
        eventTitle = findViewById(R.id.create_new_event_screen_event_title_content);
        eventDescription = findViewById(R.id.create_new_event_screen_event_description_content);
        eventMainType = findViewById(R.id.create_new_event_screen_event_main_type_spinner);
        eventInnerType = findViewById(R.id.create_new_event_screen__event_inner_type_spinner);
        lat = findViewById(R.id.create_new_event_screen_event_lat_content);
        lon = findViewById(R.id.create_new_event_screen_event_lon_content);
        logo = findViewById(R.id.create_new_event_screen_logo);
        goBack = findViewById(R.id.create_new_event_screen_img_go_back);

    }

    private void initFragments() {
//        map_fragment = new Fragment_Map();
//        map_fragment.setActivity(this);
//        map_fragment.setCallBack_map(callback_map);
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_map, map_fragment).commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_new_event_screen_map);
        mapFragment.getMapAsync(this);
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null){
            finish();
            startActivity(intent);
        }

    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.create_new_event_screen_map);
            mapFragment.getMapAsync(gMap -> {
                LatLng coordinates = new LatLng(doLat, doLon);
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(coordinates).draggable(false).title("Hobby Event Location"));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15), 5000, null);
            });
    }

}