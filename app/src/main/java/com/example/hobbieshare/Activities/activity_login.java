package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbieshare.Classes.User;
import com.example.hobbieshare.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_login extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    private EditText loginEmail, loginPassword;
    private MaterialButton loginButton;
    private CallbackManager mCallbackManager;
    private ImageButton goBack;
    private ProgressBar progressBar;
    private TextView goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                User user = new User();

                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6){
                    loginPassword.setError("Password must be grater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the User

                // [START initialize_auth]
                // Initialize Firebase Auth
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(activity_login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), activity_home_screen.class));
                        }
                        else {
                            Toast.makeText(activity_login.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                // [END initialize_auth]
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOpeningScreen();
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_register.class));
                finish();
            }
        });

    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if( mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }

    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]


    private void updateUI(FirebaseUser user) {

    }


    /** Init Views */
    private void findViews() {
        loginButton = findViewById(R.id.login_screen_btn_login);
        loginEmail = findViewById(R.id.login_screen_plainTxt_Email);
        loginPassword = findViewById(R.id.login_screen_plainTxt_password);
        goBack = findViewById(R.id.login_img_go_back);
        progressBar = findViewById(R.id.login_screen_progressBar);
        goToRegister = findViewById(R.id.txt_go_to_register);
    }

    /**
     * Intent - Switch between activities
     * */
    private void goToOpeningScreen() {
        Intent intent = new Intent(this, activity_opening_screen.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }
}