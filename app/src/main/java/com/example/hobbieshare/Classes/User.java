package com.example.hobbieshare.Classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    private String fullName, email, password;
    private ArrayList<HobbyEvent> userHobbies;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private static int idGenerator = 0;
    private final int userId;

    public User() {
        this.userId = idGenerator;
        idGenerator++;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public ArrayList<HobbyEvent> getUserHobbies() {
        return userHobbies;
    }

    public User setUserHobbies(ArrayList<HobbyEvent> userHobbies) {
        this.userHobbies = userHobbies;
        return this;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public int getUserId() {
        return userId;
    }
}
