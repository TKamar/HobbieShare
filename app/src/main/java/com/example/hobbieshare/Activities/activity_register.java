package com.example.hobbieshare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hobbieshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {

    private EditText registerFullname, registerEmail, registerPassword, registerPasswordConfirm, registerUsername;
    private MaterialButton registerButton;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ImageButton goBack;
    private TextView goToLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), activity_home_screen.class));
            finish();
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOpeningScreen();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), activity_login.class));
                finish();
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

                /** Register User into FireBase Data  */
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), activity_home_screen.class));
                        }
                        else {
                            Toast.makeText(activity_register.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }

    private void goToOpeningScreen() {
        Intent intent = new Intent(this, activity_opening_screen.class);
        if (intent != null) {
            finish();
            startActivity(intent);
        }
    }

    private void findViews(){
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
}