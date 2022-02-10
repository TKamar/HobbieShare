package com.example.hobbieshare.Classes;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class User {

    private String fullName, email, password, userName;
    private ArrayList<Hobby> userHobbies = new ArrayList<>();
    private static int idGenerator = 0;
    private final int userId;
    //private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public User() {
        this.userId = idGenerator++;
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

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public ArrayList<Hobby> getUserHobbies() {
        return userHobbies;
    }

    public User setUserHobbies(ArrayList<Hobby> userHobbies) {
        this.userHobbies = userHobbies;
        return this;
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(int idGenerator) {
        User.idGenerator = idGenerator;
    }

    public int getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", userHobbies=" + userHobbies +
                ", userId=" + userId +
                //", firebaseAuth=" + firebaseAuth +
                '}';
    }

    public User addUserHobbies (Hobby hobby) {
        this.userHobbies.add(hobby);
        return this;
    }

}
