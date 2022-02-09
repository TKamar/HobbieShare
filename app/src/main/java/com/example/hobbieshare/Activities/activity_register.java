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

import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.Classes.User;
import com.example.hobbieshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class activity_register extends AppCompatActivity {

    private EditText registerFullname, registerEmail, registerPassword, registerPasswordConfirm, registerUsername;
    private MaterialButton registerButton;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ImageButton goBack;
    private TextView goToLogin;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String currUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        firebaseAuth = FirebaseAuth.getInstance();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOpeningScreen();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String passwordConfirm = registerPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    registerEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    registerPassword.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    registerPassword.setError("Password is Required");
                    return;
                }

                if(!password.equals(passwordConfirm)){
                    registerPasswordConfirm.setError("Password Confirm is not equal to Password");
                    return;
                }

                if (password.length() < 6){
                    registerPassword.setError("Password must be grater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                addUserToDB();


            }
        });
    }


    /** Switch Activities functions */
    private void goToOpeningScreen() {
        Intent intent = new Intent(this, activity_opening_screen.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, activity_login.class);
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


    private void findViews() {
        registerFullname = findViewById(R.id.plainTxt_fullName);
        registerEmail = findViewById(R.id.login_screen_plainTxt_Email);
        registerPassword = findViewById(R.id.login_screen_plainTxt_password);
        registerPasswordConfirm = findViewById(R.id.plainTxt_password_confirm);
        registerUsername = findViewById(R.id.plainTxt_Username);
        registerButton = findViewById(R.id.register_screen_btn_register);
        progressBar = findViewById(R.id.register_screen_progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        goBack = findViewById(R.id.register_img_go_back);
        goToLogin = findViewById(R.id.txt_go_to_login);
    }

    /** Register User into FireBase Data  */
    private void addUserToDB(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String fullName = registerFullname.getText().toString();
        String userName = registerUsername.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {
                            Toast.makeText(activity_register.this, "Verification mail has been sent!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(activity_register.this, "Registration completed!", Toast.LENGTH_LONG).show();

                    User user = new User()
                            .setFullName(fullName)
                            .setEmail(email)
                            .setPassword(password)
                            .setUserName(userName)
                            .addUserHobbies(null)
                            ;

                    myRef = database.getReference("users");
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                    DB_Manager.setCounter("Users_Counter", User.getIdGenerator());

                    Toast.makeText(activity_register.this, "User Created", Toast.LENGTH_SHORT).show();
                    goToHomeScreen();
                }
                else {
                    Toast.makeText(activity_register.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

}