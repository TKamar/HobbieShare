package com.example.hobbieshare.Classes;

import java.util.ArrayList;

public class HobbyEvent {

    private String title;
    private int eventManagerId;
    private ArrayList<User> participants;
    //private String dayOfEvent;
    private String categoryOfEvent;
    private String subCategory;
    private int eventId;
    private int idGenerator = 0;
    //private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private User adminEvent;
    double[] myCoordinates = new double[2];
    String lat, lon;

    public void HobbyEvent(String name, int eventManagerId/*, String dayOfEvent*/, String categoryOfEvent, String subCategory, Double lat, Double lon) {
        this.title = name;
        this.eventManagerId = eventManagerId;
        //this.dayOfEvent = dayOfEvent;
        this.categoryOfEvent = categoryOfEvent;
        this.subCategory = subCategory;
//        this.adminEvent = adminEvent.getUser();
        ArrayList<User> participants = new ArrayList<>();
        participants.add(adminEvent);
        this.myCoordinates[0] = lat;
        this.myCoordinates[1] = lon;
        this.eventId = idGenerator;
        idGenerator++;
    }

    public HobbyEvent(String title, String categoryOfEvent, String subCategory, String lat, String lon) {
        this.title = title;
        this.categoryOfEvent = categoryOfEvent;
        this.subCategory = subCategory;
        ArrayList<User> participants = new ArrayList<>();
        this.lat = lat;
        this.lon = lon;
        this.eventId = idGenerator;
        idGenerator++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

//    public void setParticipants(boolean participants) {
//        this.participants = participants;
//    }

    public void setParticipants(User participant) {
        this.participants.add(participant) ;
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
