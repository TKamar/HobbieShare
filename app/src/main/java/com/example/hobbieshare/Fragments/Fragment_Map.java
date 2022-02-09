package com.example.hobbieshare.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hobbieshare.CallBacks.Callback_Map;
import com.example.hobbieshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Fragment_Map extends Fragment {

    private AppCompatActivity activity;
    private Callback_Map callback_map;
    private double lat;
    private double lon;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallbackMap(Callback_Map callBack_map) {
        this.callback_map = callBack_map;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        return view;
    }

    public Fragment_Map setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public Fragment_Map setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(lat, lon);
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2000, null);
    }

}