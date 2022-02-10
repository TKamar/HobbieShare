package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hobbieshare.CallBacks.Callback_Users;
import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.User;
import com.example.hobbieshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class activity_user_profile extends AppCompatActivity {

    private TextView myEmail, myUsername, numOfHobbies, welcomingTitle;
    private ImageButton btn_home, btn_myGroups, btn_location, logo;
    private double lat, lon;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Bundle data = getIntent().getExtras();
        if (data != null) {
            lat = Double.parseDouble(data.getString("lat"));
            lon = Double.parseDouble(data.getString("lon"));
            Log.d("LatLon", "onCreate: lat: " + lat + " lon: " + lon);
        }

        findViews();
        setUsersFromDB();


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeScreen();
            }
        });


        btn_myGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyGroupsScreen();
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

    @SuppressLint("SetTextI18n")
    private void setUserInfo( User user) {
        myEmail.setText(user.getEmail());
        myUsername.setText(user.getUserName());
        numOfHobbies.setText("" + user.getUserHobbies().size());
        welcomingTitle.setText("Hi " + user.getFullName() + "!");
    }

    private void findViews() {
        myEmail = findViewById(R.id.email_holder_profile_screen);
        myUsername = findViewById(R.id.username_holder_profile_screen);
        numOfHobbies = findViewById(R.id.numOfGroups_holder_profile_screen);
        btn_home = findViewById(R.id.activity_user_profile_btn_home);
        btn_myGroups = findViewById(R.id.activity_user_profile_btn_myGroups);
        btn_location = findViewById(R.id.activity_user_profile_btn_location);
        logo = findViewById(R.id.activity_user_profile_screen_logo);
        welcomingTitle = findViewById(R.id.user_name_profile_screen);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), activity_opening_screen.class));
        finish();
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
        for(User user: users){
            if(user.getEmail().equals(firebaseUser.getEmail())) {
                setUserInfo(user);
            }
        }

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
        if (intent != null){
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }

    }

    private void goToMyGroupsScreen() {
        Intent intent = new Intent(this, activity_my_hobbies.class);
        if (intent != null) {
            intent.putExtra("lat", "" + lat);
            intent.putExtra("lon", "" + lon);
            finish();
            startActivity(intent);
        }
    }


}