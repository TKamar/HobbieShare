package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class activity_create_new_event extends AppCompatActivity implements OnMapReadyCallback {

    private EditText eventTitle, eventDescription, lat, lon;
    private MapView myMap;
    private Fragment_Map map_fragment;
    private SupportMapFragment mapFragmentSupport;
    private GoogleMap gMap;
    private Marker marker;
    private Callback_Map callback_map;
    private ImageButton logo, goBack, createNewEventBtn;
    private double doLat = 0, doLon = 0;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_event);


        findViews();
        initFragments();
        initSpinnersAdapters();

        Bundle data = getIntent().getExtras();
        if (data != null) {

            doLat = Double.valueOf(data.getString("lat"));
            doLon = Double.valueOf(data.getString("lon"));
        }
        onMapReady(gMap);

        eventMainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setInnerSpinnerAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        createNewEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewEventToDB();
            }
        });



    }

    private void saveNewEventToDB() {
        Intent intent = new Intent(this, activity_home_screen.class);
        intent.putExtra("event title", eventTitle.getText().toString());
        intent.putExtra("event description", eventDescription.getText().toString());
        intent.putExtra("event main category", eventMainType.getSelectedItem().toString());
        intent.putExtra("event inner category", eventInnerType.getSelectedItem().toString());
        intent.putExtra("event lat", lat.getText().toString());
        intent.putExtra("event lon", lon.getText().toString());


        goToHomeScreen();
    }

    /** Init Functions */

    public void findViews() {
        eventTitle = findViewById(R.id.create_new_event_screen_event_title_content);
        eventDescription = findViewById(R.id.create_new_event_screen_event_description_content);
        eventMainType = findViewById(R.id.create_new_event_screen_event_main_type_spinner);
        eventInnerType = findViewById(R.id.create_new_event_screen__event_inner_type_spinner);
        lat = findViewById(R.id.create_new_event_screen_event_lat_content);
        lon = findViewById(R.id.create_new_event_screen_event_lon_content);
        logo = findViewById(R.id.create_new_event_screen_logo);
        goBack = findViewById(R.id.create_new_event_screen_img_go_back);
        createNewEventBtn = findViewById(R.id.create_new_event_screen_create_new_event_button);

    }

    private void initFragments() {
        map_fragment = new Fragment_Map();
        map_fragment.setActivity(this);
        map_fragment.setCallBack_map(callback_map);
        mapFragmentSupport = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.create_new_event_screen_map);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_map, map_fragment).commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.create_new_event_screen_map);
        mapFragment.getMapAsync(this);
    }

    /** Switch Screens Functions */

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null){
            finish();
            startActivity(intent);
        }

    }

    /** Map and Markers Functions*/

    private interface callback_positionValues{
        void getPositions(double newLat, double newLon);
    }

    @Override
    public void onMapReady( GoogleMap googleMap) {
        gMap = googleMap;

        mapFragmentSupport.getMapAsync(gMap -> {
            LatLng coordinates = new LatLng(doLat, doLon);
            gMap.clear();

            marker = gMap.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .draggable(true)
                    .title("Hobby Event Location")
                    .zIndex(1)
                    .snippet("Long press to move marker"));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15), 2000, null);

            callback_positionValues newCoordinates = (newLat, newLon) -> getEventPosition(new LatLng(newLat, newLon));
            newCoordinates.getPositions(marker.getPosition().latitude, marker.getPosition().longitude);

            gMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    callback_positionValues newCoordinates = (newLat, newLon) -> getEventPosition(new LatLng(newLat, newLon));
                    newCoordinates.getPositions(marker.getPosition().latitude, marker.getPosition().longitude);
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {

                }
            });

            });
    }

    @SuppressLint("SetTextI18n")
    public void getEventPosition(LatLng newCoordinates) {
        lat.setText("" + newCoordinates.latitude);
        lon.setText("" + newCoordinates.longitude);
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




}