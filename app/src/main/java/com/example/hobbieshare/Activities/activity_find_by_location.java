package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbieshare.CallBacks.Callback_Hobbies;
import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.CallBacks.Callback_Users;
import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.Classes.Marker_Manager;
import com.example.hobbieshare.Classes.User;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;

public class activity_find_by_location extends AppCompatActivity implements OnMapReadyCallback {

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
    private TextView eventTitle, eventDescription, eventCategory, eventSubcategory;
    private ImageButton goBack;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Hobby> allHobbies = new ArrayList<>();
    private ClusterManager clusterManager;
    private ArrayList<Marker_Manager> clusterMarkerArr = new ArrayList<>();
    private Context context = this;
    private GoogleMap map;
    private SupportMapFragment mapFragmentSupport;
    private Marker marker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_location);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        findViews();
        joinToEventButton.setVisibility(View.INVISIBLE);
        //initSpinnersAdapters();

        getHobbiesFromDB();
        //setUpClusterer();

        /*
        eventMainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setInnerSpinnerAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */


        Bundle locationData = getIntent().getExtras();
        if (locationData != null) {
            myCoordinates[0] = locationData.getDouble("lat");
            myCoordinates[1] = locationData.getDouble("lon");
        }
        ZoomOnMap(myCoordinates[0], myCoordinates[1]);
        Log.d("allHobbies", "onCreate: allHobbies: " + allHobbies);


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
        //eventMainType = findViewById(R.id.find_by_location_screen_events_main_type);
        //eventInnerType = findViewById(R.id.find_by_location_screen_events_inner_type);
        eventTitle = findViewById(R.id.find_by_location_screen_event_title);
        //eventTime = findViewById(R.id.find_by_location_screen_event_time);
        eventDescription = findViewById(R.id.find_by_location_screen_event_description);
        goBack = findViewById(R.id.find_by_location_screen_img_go_back);
        //searchButton  = findViewById(R.id.find_by_location_screen_search);
        joinToEventButton = findViewById(R.id.find_by_location_screen_ask_to_join);
        eventCategory = findViewById(R.id.find_by_location_screen_event_Category);
        eventSubcategory = findViewById(R.id.find_by_location_screen_event_innerCategory);

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
            myMap.addMarker(new MarkerOptions().position(coordinates).title("My Location"));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15), 2000, null);

            Hobby tmpHobby;
            double lat1, lon1;

            for (int i = 0; i < allHobbies.size(); i++) {
                tmpHobby = allHobbies.get(i);
                Log.d("allHobbies", "allHobbies: " + i + ": " + tmpHobby);
                lat1 = Double.parseDouble(tmpHobby.getLat());
                lon1 = Double.parseDouble(tmpHobby.getLon());
                LatLng tmpCoordinates = new LatLng(lat1, lon1);
                myMap.addMarker(new MarkerOptions().position(tmpCoordinates).title(tmpHobby.getTitle())).setSnippet(tmpHobby.getDescription());
                Marker_Manager setItem = new Marker_Manager(lat1, lon1, tmpHobby.getTitle(), tmpHobby.getDescription(), tmpHobby);
                clusterManager.addItem(setItem);
                clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Marker_Manager>() {
                    @Override
                    public boolean onClusterItemClick(Marker_Manager item) {
                        return false;
                    }
                });
            }
        });
    };


    public void ZoomOnMap(double lat, double lon) {
        callback_map.mapClicked(lat, lon);
    }


    private void getHobbiesFromDB() {
        Callback_Hobbies callback_hobbies = new Callback_Hobbies() {
            @Override
            public void dataReady(ArrayList<Hobby> allHobbies) {
                Log.d("dataReady", "dataReady: " + allHobbies.size());
                setHobbiesFromCallback(allHobbies);
                setUsersFromDB();
                addMapMarkers();
            }
        };
        DB_Manager.getAllHobbies(callback_hobbies);

        Log.d("getHobbiesFromDB", "getHobbiesFromDB: " + allHobbies);
    }

    private void setHobbiesFromCallback(ArrayList<Hobby> allHobbies){
        for(Hobby hobby: allHobbies){
            this.allHobbies.add(hobby);
        }
        Log.d("setHobbiesFromCallback", "setHobbiesFromCallback: " + this.allHobbies.size());
        Log.d("setHobbiesFromCallback", "setHobbiesFromCallback: " + this.allHobbies.toString());
    }


    private void setUsersFromDB() {
        Callback_Users callback_users = new Callback_Users() {
            @Override
            public void dataReady(ArrayList<User> users) {
                Log.d("setUsersFromDB", "dataReady: users" + users.toString());
                setUsers(users);
            }
        };
        DB_Manager.getAllUsers(callback_users);
    }

    private void setUsers(ArrayList<User> users) {
        for(User user : users){
            this.allUsers.add(user);
        }

    }

    private void addHobbyToUser(Hobby hobby) {
        User myUser;
        for(User user: allUsers){
            if(user.getEmail().equals(firebaseUser.getEmail())) {
                myUser = user;
                myUser.addUserHobbies(hobby);
                addUserToHobby(user, hobby);
            }

        }
    }

    private void addUserToHobby(User user, Hobby hobby) {
        for(Hobby tmpHobby : allHobbies){
            if(tmpHobby.getEventId() == hobby.getEventId()){
                tmpHobby.setParticipants(user.getEmail());
                Toast.makeText(activity_find_by_location.this, "You have joined the bobby group ~ ! ", Toast.LENGTH_SHORT).show();
                DB_Manager.updateDB(user, hobby);
                Log.d("addUserToHobby", "addUserToHobby: all users: " + allUsers);
                Log.d("addUserToHobby", "addUserToHobby: all hobbies: " + allHobbies);
            }
        }
    }


    /** Map and Markers */
    private void addMapMarkers() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(myMap -> {
            if(clusterManager == null){
                clusterManager = new ClusterManager<Marker_Manager>(getApplicationContext(), myMap);
            }

            clusterManager.setRenderer(new DefaultClusterRenderer<Marker_Manager>(this, myMap, clusterManager) {
                @Override
                protected boolean shouldRenderAsCluster(Cluster cluster) {
                    return false;
                }
            });

            for (Hobby hobby : allHobbies) {
                try {

                    Marker_Manager newMarker = new Marker_Manager(
                            new LatLng(Double.parseDouble(hobby.getLat()),Double.parseDouble(hobby.getLon())),
                            hobby.getTitle(),
                            hobby.getDescription(),
                            hobby);
                    Log.d("addMapMarkers", "addMapMarkers: location of hobbies" + hobby.toString());

                    clusterManager.addItem(newMarker);
                    clusterMarkerArr.add(newMarker);

                    //myMap.setOnMarkerClickListener(clusterManager);

                } catch (NullPointerException e) {
                    Log.e("addMapMarkers", "addMapMarkers: NullPointerException: " + e.getMessage());
                }
            }



            clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener() {
                @Override
                public boolean onClusterItemClick(ClusterItem item) {
                    for(Hobby hobby : allHobbies){
                        LatLng tmpCoordinates = new LatLng(Double.parseDouble(hobby.getLat()), Double.parseDouble(hobby.getLon()));
                        if(item.getPosition().equals(tmpCoordinates)){
                            eventTitle.setText(hobby.getTitle());
                            eventDescription.setText(hobby.getDescription());
                            eventCategory.setText(hobby.getCategoryOfEvent());
                            eventSubcategory.setText(hobby.getSubCategory());
                            joinToEventButton.setVisibility(View.VISIBLE);

                            joinToEventButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addHobbyToUser(hobby);

                                }
                            });
                        }
                    }

                    return false;
                }
            });

            clusterManager.cluster();


        });

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }
}