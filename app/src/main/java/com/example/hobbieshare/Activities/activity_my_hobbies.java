package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.hobbieshare.CallBacks.Callback_Hobbies;
import com.example.hobbieshare.CallBacks.Callback_List;
import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.Fragments.Fragment_Hobby_List;
import com.example.hobbieshare.Fragments.Fragment_Map;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_my_hobbies extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    private ImageButton btn_home;
    private ImageButton btn_myGroups;
    private ImageButton btn_createNewEvent;
    private ImageButton btn_myProfile;
    private ImageButton btn_location;
    private ImageButton logo;

    private ArrayList<Hobby> myHobbies = new ArrayList<>();

    private double lat, lon;

    private Fragment_Hobby_List fragment_hobby_list;
    private Fragment_Map fragment_map;
    private LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hobbies);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            lat = Double.parseDouble(data.getString("lat"));
            lon = Double.parseDouble(data.getString("lon"));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        findViews();
        initFragmentMap();
        initFragmentHobbyList();

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });


        btn_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileScreen();
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFindByLocationScreen();
            }
        });


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });

    }

    /** Init Fragments */
    private void initFragmentHobbyList() {
        fragment_hobby_list = new Fragment_Hobby_List();
        fragment_hobby_list.setActivity(this);
        fragment_hobby_list.setCallbackList(callback_list);
        callback_list.getHobbyLocation(lat, lon);
        getSupportFragmentManager().beginTransaction().add(R.id.my_hobbies_hobbyList, fragment_hobby_list).commit();
    }

    private void initFragmentMap() {
        fragment_map = new Fragment_Map();
        fragment_map.setActivity(this);
        fragment_map.setCallbackMap(callback_map);
        fragment_map.setLat(lat).setLon(lon);
        callback_map.mapClicked(lat,lon);
        getSupportFragmentManager().beginTransaction().add(R.id.my_hobbies_hobbyList, fragment_map).commit();
    }

    Callback_List callback_list = new Callback_List() {
        @Override
        public void getHobbyLocation(double lat, double lon) {
            fragment_map.setLat(lat).setLon(lon);
            callback_map.mapClicked(lat, lon);

        }
    };

    Callback_Map callback_map = (lat, lon) -> {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.my_hobbies_map);
        mapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(lat, lon);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Hobby Location"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2000, null);
        });
    };



    /** Init Views */
    private void findViews() {
        btn_home = findViewById(R.id.my_hobbies_btn_home);
        btn_myGroups = findViewById(R.id.my_hobbies_btn_myGroups);
        btn_myProfile = findViewById(R.id.my_hobbies_btn_myProfile);
        btn_location = findViewById(R.id.my_hobbies_btn_location);
        logo = findViewById(R.id.my_hobbies_screen_logo);
    }


    /**
     * Intent - Switch between activities
     * */

    private void goToFindByLocationScreen() {
        Intent intent = new Intent(this, activity_find_by_location.class);
        if (intent != null) {
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }

    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null) {
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }

    }

    private void goToProfileScreen() {
        Intent intent = new Intent(this, activity_user_profile.class);
        if (intent != null) {
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }
    }

}