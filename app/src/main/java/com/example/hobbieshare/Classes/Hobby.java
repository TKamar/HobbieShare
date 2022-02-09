package com.example.hobbieshare.Classes;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Hobby {

    private final int eventId;
    private static int idGeneratorHobbies = 0;

    private String title;
    private String eventManagerId;
    private ArrayList<String> participants = new ArrayList<>();
    private String categoryOfEvent;
    private String subCategory;
    private String description;
    private String lat, lon;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public Hobby() {
        this.eventId = idGeneratorHobbies++;
    }

    public Hobby(int eventId, String title, String eventManagerId, ArrayList<String> participants, String categoryOfEvent, String subCategory,
                 String description, String lat, String lon) {
        this.eventId = eventId;
        this.title = title;
        this.eventManagerId = eventManagerId;
        this.participants = participants;
        this.categoryOfEvent = categoryOfEvent;
        this.subCategory = subCategory;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }


    public static int getIdGenerator() {
        return idGeneratorHobbies;
    }

    public static void setHobbiesIdGenerator(int counter) {
        Hobby.idGeneratorHobbies = counter;
    }


    public Hobby setParticipants(String participantID) {
        this.participants.add(participantID);
        return this;
    }

    public static void setIdGeneratorHobbies(int idGeneratorHobbies) {
        Hobby.idGeneratorHobbies = idGeneratorHobbies;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public Hobby setParticipants(ArrayList<String> participants) {
        this.participants = participants;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Hobby setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEventManagerId() {
        return eventManagerId;
    }

    public void setEventManagerId(String eventManagerId) {
        this.eventManagerId = eventManagerId;
    }

    public String getCategoryOfEvent() {
        return categoryOfEvent;
    }

    public Hobby setCategoryOfEvent(String categoryOfEvent) {
        this.categoryOfEvent = categoryOfEvent;
        return this;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public Hobby setSubCategory(String subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Hobby setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getEventId() {
        return eventId;
    }

    public int getIdGeneratorHobbies() {
        return idGeneratorHobbies;
    }

//    public String getAdminEvent() {
//        return adminEvent;
//    }
//
//    public Hobby setAdminEvent(String adminEvent) {
//        this.adminEvent = adminEvent;
//        return this;
//    }

    public String getLat() {
        return lat;
    }

    public Hobby setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public Hobby setLon(String lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", eventManagerId='" + eventManagerId + '\'' +
                ", participants=" + participants +
                ", categoryOfEvent='" + categoryOfEvent + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", description='" + description + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", firebaseAuth=" + firebaseAuth +
                '}';
    }
}
