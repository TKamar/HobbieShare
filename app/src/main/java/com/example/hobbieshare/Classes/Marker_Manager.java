package com.example.hobbieshare.Classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Marker_Manager implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;

    private Hobby hobby;

    public Marker_Manager(double lat, double lng, String title, String snippet, Hobby hobby) {
        position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
        this.hobby = hobby;
    }

    public Marker_Manager(LatLng position, String title, String snippet, Hobby hobby) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.hobby = hobby;
    }

    public Marker_Manager() { }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }


    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }
}
