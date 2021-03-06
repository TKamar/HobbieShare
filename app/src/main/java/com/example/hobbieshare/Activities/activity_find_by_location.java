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

    private ImageButton btn_home;
    private ImageButton btn_myGroups;
    private ImageButton btn_searchByLocation;
    private ImageButton btn_myProfile;
    private ImageButton logo, searchButton, joinToEventButton;
    private double lat, lon;
    private Spinner eventMainType;
    private Spinner eventInnerType;
    private ArrayAdapter<CharSequence> mainSpinnerAdapter;
    private ArrayAdapter<CharSequence> innerSpinnerAdapter;
    private String[] mainSpinnerEventList= {"??????????", "????????????","????????", "????????????","????????????", "????????????", "?????????? ???????????? ????????","??????"};
    private String[] innerSpinnerEventListSports= {"????????????", "????????????","????????", "????????-????????", "????????????","??????????????", "????????", "????????????","????????"};
    private String[] innerSpinnerEventListMusic= {"?????????? ????????", "??????????","??????????", "??????????", "??????????","????????"};
    private String[] innerSpinnerEventListGames= {"?????????? ????????", "?????????? ??????????","???????????? ????????","????????" ,"???????????? ??????????"};
    private String[] innerSpinnerEventListArt= {"????????", "??????????","??????????", "??????????"};
    private String[] innerSpinnerEventListCollections= {"??????????", "??????????","????????????", "????????????", "????????????","??????????????"};
    private String[] innerSpinnerEventListLearning= {"??????????", "????????????????????","??????????", "??????????", "??????????????"};
    private String[] innerSpinnerEventListOther= {"??????????", "??????????????","????????????"};
    private String[] innerSpinnerEventListFood = {"??????????", "??????????","????????????"};
    private TextView eventTitle, eventDescription, eventCategory, eventSubcategory;

    private String latitude = "";
    private String longitude = "";

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
        getHobbiesFromDB();


        Bundle data = getIntent().getExtras();
        if (data != null) {
            lat = Double.parseDouble(data.getString("lat"));
            lon = Double.parseDouble(data.getString("lon"));
        }

        ZoomOnMap(lat, lon);

        logo.setOnClickListener(new View.OnClickListener() {
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

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });

        btn_myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyHobbiesScreen();
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
            case "??????????":
                currList = innerSpinnerEventListSports;
                break;
            case "????????????":
                currList = innerSpinnerEventListMusic;
                break;
            case "????????":
                currList = innerSpinnerEventListFood;
                break;
            case "????????????":
                currList = innerSpinnerEventListGames;
                break;
            case "????????????":
                currList = innerSpinnerEventListArt;
                break;
            case "????????????":
                currList = innerSpinnerEventListCollections;
                break;
            case "?????????? ???????????? ????????":
                currList = innerSpinnerEventListLearning;
                break;
            case "??????":
                currList = innerSpinnerEventListOther;
                break;
            default:
                break;
        }

        innerSpinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, currList);
        innerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventInnerType.setAdapter(innerSpinnerAdapter);
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


    /** DB Data functions */

    private void getHobbiesFromDB() {
        Callback_Hobbies callback_hobbies = new Callback_Hobbies() {
            @Override
            public void dataReady(ArrayList<Hobby> allHobbies) {
                setHobbiesFromCallback(allHobbies);
                setUsersFromDB();
                addMapMarkers();
            }
        };
        DB_Manager.getAllHobbies(callback_hobbies);
    }

    private void setHobbiesFromCallback(ArrayList<Hobby> allHobbies){
        for(Hobby hobby: allHobbies) {
            this.allHobbies.add(hobby);
        }
    }


    private void setUsersFromDB() {
        Callback_Users callback_users = new Callback_Users() {
            @Override
            public void dataReady(ArrayList<User> users) {
                setUsers(users);
            }
        };
        DB_Manager.getAllUsers(callback_users);
    }

    private void setUsers(ArrayList<User> users) {
        for(User user : users) {
            this.allUsers.add(user);
        }

    }

    private void addHobbyToUser(Hobby hobby) {
        User myUser;
        for(User user: allUsers) {
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

                    clusterManager.addItem(newMarker);
                    clusterMarkerArr.add(newMarker);

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

    /** Init Functions */
    private void findViews() {
        logo = findViewById(R.id.location_screen_logo);
        //eventMainType = findViewById(R.id.find_by_location_screen_events_main_type);
        //eventInnerType = findViewById(R.id.find_by_location_screen_events_inner_type);
        eventTitle = findViewById(R.id.find_by_location_screen_event_title);
        //eventTime = findViewById(R.id.find_by_location_screen_event_time);
        eventDescription = findViewById(R.id.find_by_location_screen_event_description);
        //searchButton  = findViewById(R.id.find_by_location_screen_search);
        joinToEventButton = findViewById(R.id.find_by_location_screen_ask_to_join);
        eventCategory = findViewById(R.id.find_by_location_screen_event_Category);
        eventSubcategory = findViewById(R.id.find_by_location_screen_event_innerCategory);
        btn_home = findViewById(R.id.find_by_location_screen_btn_home);
        btn_myGroups = findViewById(R.id.find_by_location_screen_btn_myGroups);
        btn_searchByLocation = findViewById(R.id.find_by_location_screen_btn_location);
        btn_myProfile = findViewById(R.id.find_by_location_screen_btn_myProfile);
    }

    /**
     * Intent - Switch between activities
     * */

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null){
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }
    }

    private void goToProfileScreen() {
        Intent intent = new Intent(this, activity_user_profile.class);
        if (intent != null){
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }
    }

    private void goToMyHobbiesScreen() {
        Intent intent = new Intent(this, activity_my_hobbies.class);
        if (intent != null){
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }
    }


}