package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hobbieshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.example.hobbieshare.Activities.FacebookLoginActivity;

public class activity_user_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), activity_opening_screen.class));
        finish();
    }


}