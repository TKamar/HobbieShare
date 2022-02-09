package com.example.hobbieshare.Classes;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Hobby_Adapter {

    private Activity activity;
    private ArrayList<Hobby> allHobbies;
    private HobbyEventItemClickListener hobbyEventItemClickListener;

    public interface HobbyEventItemClickListener {
        void hobbyItemClicked(Hobby hobby);
    }

    public Hobby_Adapter(FragmentActivity activity, ArrayList<Hobby> allHobbies){
        this.activity = activity;
        this.allHobbies = allHobbies;
    }

    public Hobby_Adapter setHobbyEventItemClickListener (HobbyEventItemClickListener hobbyEventItemClickListener) {
        this.hobbyEventItemClickListener = hobbyEventItemClickListener;
        return this;
    }



}
