package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.EventListener;

public class activity_find_by_location extends AppCompatActivity {

    private ImageButton logo, searchButton, joinToEventButton;
    private double[] myCoordinates = new double[2];
    private Spinner eventMainType;
    private Spinner eventInnerType;
    private ArrayAdapter<CharSequence> mainSpinnerAdapter;
    private ArrayAdapter<CharSequence> innerSpinnerAdapter;
    private String[] mainSpinnerEventList= {"ספורט", "מוזיקה","אוכל", "משחקים","אומנות", "אוספים", "למידה ופיתוח אישי","אחר"};
    private String[] innerSpinnerEventListSports= {"כדורגל", "כדורסל","טניס", "פינג-פונג", "פוטבול","אופניים", "ריצה", "פריזבי","כושר"};
    private String[] innerSpinnerEventListMusic= {"נגינה כללי", "כתיבה","גיטרה", "פסנתר", "תופים","שירה"};
    private String[] innerSpinnerEventListGames= {"משחקי מחשב", "משחקי קופסה","משחקים בחוץ","שחמט" ,"מציאות מדומה"};
    private String[] innerSpinnerEventListArt= {"ציור", "צילום","יצירה", "פיסול"};
    private String[] innerSpinnerEventListCollections= {"קלפים", "בולים","קומיקס", "משחקים", "אומנות","תקליטים"};
    private String[] innerSpinnerEventListLearning= {"בישול", "אסטרונומיה","תכנות", "יצירה", "אימונים"};
    private String[] innerSpinnerEventListOther= {"קסמים", "אוריגמי","גרפיטי"};
    private String[] innerSpinnerEventListFood = {"בישול", "אפייה","מסעדות"};
    private TextView eventTitle, eventTime, eventDescription;
    private ImageButton goBack;

    private ArrayList<Hobby> allHobbies = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_location);

        findViews();
        initSpinnersAdapters();


        eventMainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setInnerSpinnerAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });
    }

    /**Spinners & Adapters */
    private void initSpinnersAdapters() {
        mainSpinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, mainSpinnerEventList);
        mainSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventMainType.setAdapter(mainSpinnerAdapter);
    }

    private String getSelectedEventType() {
        return eventMainType.getSelectedItem().toString();
    }

    private void setInnerSpinnerAdapter() {
        String[] currList = {""};
        String selectedMainType = getSelectedEventType();
        switch (selectedMainType) {
            case "ספורט":
                currList = innerSpinnerEventListSports;
                break;
            case "מוזיקה":
                currList = innerSpinnerEventListMusic;
                break;
            case "אוכל":
                currList = innerSpinnerEventListFood;
                break;
            case "משחקים":
                currList = innerSpinnerEventListGames;
                break;
            case "אומנות":
                currList = innerSpinnerEventListArt;
                break;
            case "אוספים":
                currList = innerSpinnerEventListCollections;
                break;
            case "למידה ופיתוח אישי":
                currList = innerSpinnerEventListLearning;
                break;
            case "אחר":
                currList = innerSpinnerEventListOther;
                break;
            default:
                break;
        }

        innerSpinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, currList);
        innerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventInnerType.setAdapter(innerSpinnerAdapter);
    }

    /** Init Functions */
    private void findViews() {

        logo = findViewById(R.id.location_screen_logo);
        eventMainType = findViewById(R.id.find_by_location_screen_events_main_type);
        eventInnerType = findViewById(R.id.find_by_location_screen_events_inner_type);
        eventTitle = findViewById(R.id.find_by_location_screen_event_title);
        eventTime = findViewById(R.id.find_by_location_screen_event_time);
        eventDescription = findViewById(R.id.find_by_location_screen_event_description);
        goBack = findViewById(R.id.find_by_location_screen_img_go_back);
        searchButton  = findViewById(R.id.find_by_location_screen_search);
        joinToEventButton = findViewById(R.id.find_by_location_screen_ask_to_join);

    }

    private void getHobbiesFromDB() {

    }


    /** Switch Activities */
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