package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class activity_switch_activities extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void goToLocationScreen(Context context) {
        Intent intent = new Intent(context, activity_find_by_location.class);
        Log.d("My Intent", "goToLocationScreen: my intent - " + intent);
        if (intent != null) {
            finish();
            startActivity(intent);
        }

    }

    public void goToHomeScreen(Context context) {
        Intent intent = new Intent(getApplicationContext(), activity_home_screen.class);
        if (intent != null){
            finish();
            startActivity(intent);
        }

    }
}