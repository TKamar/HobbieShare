package com.example.hobbieshare.Classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.hobbieshare.Classes.User;
import java.util.ArrayList;
import java.util.Date;

public class HobbyEvent {

    private String name;
    private int eventManagerId;
    private ArrayList<User> participants;
    //private String dayOfEvent;
    private String categoryOfEvent;
    private String subCategory;
    private int eventId;
    private int idGenerator = 0;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private User adminEvent;
    double[] myCoordinates = new double[2];

    public void HobbyEvent(String name, int eventManagerId/*, String dayOfEvent*/, String categoryOfEvent, String subCategory, Double lat, Double lon) {
        this.name = name;
        this.eventManagerId = eventManagerId;
        //this.dayOfEvent = dayOfEvent;
        this.categoryOfEvent = categoryOfEvent;
        this.subCategory = subCategory;
        this.adminEvent = adminEvent.getUser();
        ArrayList<User> participants = new ArrayList<>();
        participants.add(adminEvent);
        this.myCoordinates[0] = lat;
        this.myCoordinates[1] = lon;
        this.eventId = idGenerator;
        idGenerator++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventManagerId() {
        return eventManagerId;
    }

    public void setEventManagerId(int eventManagerId) {
        this.eventManagerId = eventManagerId;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

//    public String getDayOfEvent() {
//        return dayOfEvent;
//    }
//
//    public void setDayOfEvent(String dayOfEvent) {
//        this.dayOfEvent = dayOfEvent;
//    }

    public String getCategoryOfEvent() {
        return categoryOfEvent;
    }

    public void setCategoryOfEvent(String categoryOfEvent) {
        this.categoryOfEvent = categoryOfEvent;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
