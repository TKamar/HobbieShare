package com.example.hobbieshare.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.hobbieshare.CallBacks.Callback_Counter;
import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.Classes.User;
import com.example.hobbieshare.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class activity_opening_screen extends AppCompatActivity {

    private ImageView logo;
    private FirebaseAuth mAuth;
    private MaterialButton btn_login;
    private MaterialButton btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        findViews();
        initCounters();
        logo.setVisibility(View.INVISIBLE);
        btn_login.setVisibility(View.INVISIBLE);
        btn_register.setVisibility(View.INVISIBLE);
        startAnimation(logo);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterScreen();
            }
        });




    }

    /** Switch Activities */
    private void goToLoginScreen() {
        Intent intent = new Intent(this, activity_login.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    private void goToRegisterScreen() {
        Intent intent = new Intent(this, activity_register.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, activity_home_screen.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    /** Animation */
    private void startAnimation(ImageView img) {
        img.setVisibility(View.VISIBLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        img.setY(-height / 2);
        img.setScaleY(0.0f);
        img.setScaleX(0.0f);
        img.animate()
                .scaleY(1.0f)
                .scaleX(1.0f)
                .translationY(0)
                .setDuration(3000)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        animationDone();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void animationDone() {

        btn_login.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);
    }


    /** Init Functions */
    private void findViews() {
        logo = findViewById(R.id.activity_opening_screen_logo);
        btn_login = findViewById(R.id.activity_opening_screen_login_button);
        btn_register = findViewById(R.id.activity_opening_screen_register_button);
    }


    private void initCounters() {
        DB_Manager.getCounter("Users_Counter", new Callback_Counter() {
            @Override
            public void dataReady(int value) {
                User.setIdGenerator(value);
            }
        });

        DB_Manager.getCounter("Hobbies_Counter", new Callback_Counter() {
            @Override
            public void dataReady(int value) {
                Hobby.setHobbiesIdGenerator(value);
            }
        });
    }



}