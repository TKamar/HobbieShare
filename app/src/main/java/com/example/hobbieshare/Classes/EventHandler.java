package com.example.hobbieshare.Classes;

import java.util.ArrayList;

public class EventHandler {

    private ArrayList<User> allUsers;
    private ArrayList<Hobby> allHobbiesEvents;

    public void EventHandler(ArrayList<User> allUsers, ArrayList<Hobby> allHobbiesEvents){
        this.allUsers = allUsers;
        this.allHobbiesEvents = allHobbiesEvents;
    }


}
