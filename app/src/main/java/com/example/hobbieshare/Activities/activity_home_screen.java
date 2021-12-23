package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hobbieshare.R;

public class activity_home_screen extends AppCompatActivity {

    private ImageButton btn_home;
    private ImageButton btn_myGroups;
    private ImageButton btn_searchByLocation;
    private ImageButton btn_createNewEvent;
    private ImageButton btn_myProfile;
    private ImageButton btn_searchAroundMe;
    private ImageButton btn_myHobbies;
    private Context context;

    double[] myCoordinates = new double[2];
    private LocationManager locationManager;
    private String latitude = "";
    private String longitude = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        findViews();
        context = getApplicationContext();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

        Bundle data = getIntent().getExtras();
        if (data != null) {
            myCoordinates[0] = data.getDouble("lat");
            myCoordinates[1] = data.getDouble("lon");
        }



        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity_switch_activities switchScreen = new activity_switch_activities();
//                switchScreen.goToHomeScreen(context);
                goToHomeScreen();
            }
        });

        btn_searchAroundMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity_switch_activities switchScreen = new activity_switch_activities();
//                switchScreen.goToLocationScreen(context);
                goToLocationScreen();
            }
        });

        btn_searchByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity_switch_activities switchScreen = new activity_switch_activities();
//                switchScreen.goToLocationScreen(context);
                goToLocationScreen();
            }
        });

    }


    private void findViews() {

        btn_home = findViewById(R.id.btn_home);
        btn_myGroups = findViewById(R.id.btn_myGroups);
        btn_searchByLocation = findViewById(R.id.btn_location);
        btn_createNewEvent = findViewById(R.id.button_create_new_event);
        btn_myProfile = findViewById(R.id.btn_myProfile);
        btn_searchAroundMe = findViewById(R.id.button_search_hobbies);
        btn_myHobbies = findViewById(R.id.button_myHobbies);
    }

    /**
     * Intent - Switch between activities
     * */

    private void goToLocationScreen() {
        Intent intent = new Intent(this, activity_find_by_location.class);
        intent.putExtra("lat", Double.valueOf(latitude));
        intent.putExtra("lon", Double.valueOf(longitude));
        if (intent != null) {
            finish();
            startActivity(intent);
        }

    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null){
            finish();
            startActivity(intent);
        }

    }


    /**
     * Location Funcs
     * */

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                activity_home_screen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity_home_screen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double lon = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}