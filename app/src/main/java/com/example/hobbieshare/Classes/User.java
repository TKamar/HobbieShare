package com.example.hobbieshare.Classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    private String fullName, email, userId;
    private ArrayList<HobbyEvent> userHobbies;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private void User() {
        if (firebaseUser != null){
            this.fullName = firebaseUser.getDisplayName();
            this.email = firebaseUser.getEmail();
            this.userId = firebaseUser.getUid();
            this.userHobbies = new ArrayList<>();
        }
    }

    public User getUser() {
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<HobbyEvent> getUserHobbies() {
        return userHobbies;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }
}
