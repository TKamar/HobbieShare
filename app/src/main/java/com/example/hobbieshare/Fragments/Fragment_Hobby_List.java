package com.example.hobbieshare.Fragments;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hobbieshare.CallBacks.Callback_Hobbies;
import com.example.hobbieshare.CallBacks.Callback_List;
import com.example.hobbieshare.Classes.DB_Manager;
import com.example.hobbieshare.Classes.Hobby;
import com.example.hobbieshare.Classes.Hobby_Adapter;
import com.example.hobbieshare.R;

import java.util.ArrayList;

public class Fragment_Hobby_List extends Fragment {

    private ArrayList<Hobby> myHobbies = new ArrayList<>();
    private RecyclerView board_LST_hobbies;
    private AppCompatActivity activity;
    private Callback_List callback_list;


    public Fragment_Hobby_List setMyHobbies(ArrayList<Hobby> myHobbies) {
        this.myHobbies = myHobbies;
        return this;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallbackList(Callback_List callbackList) {
        this.callback_list = callbackList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        Callback_Hobbies callback_hobbies = new Callback_Hobbies() {
            @Override
            public void dataReady(ArrayList<Hobby> hobbies) {
                Hobby_Adapter hobby_adapter = new Hobby_Adapter(getActivity(), myHobbies);

                board_LST_hobbies.setLayoutManager(new GridLayoutManager(getActivity(),1));
                board_LST_hobbies.setHasFixedSize(true);
                board_LST_hobbies.setItemAnimator(new DefaultItemAnimator());
                board_LST_hobbies.setAdapter(hobby_adapter);

                Log.d("initViews1", "initViews: " + myHobbies);
                setHobbiesFromCallback(myHobbies);
                Log.d("initViews2", "initViews: " + myHobbies);
                hobby_adapter.setHobbyEventItemClickListener(new Hobby_Adapter.HobbyEventItemClickListener() {
                    @Override
                    public void hobbyItemClicked(Hobby hobby, int position) {
                        double lat = Double.parseDouble(hobby.getLat());
                        double lon = Double.parseDouble(hobby.getLon());
                        callback_list.getHobbyLocation(lat, lon);
                    }
                });
            }
        };
        DB_Manager.getHobbiesOfCurrUser(callback_hobbies);
        Log.d("initViews", "initViews: " + myHobbies);
        Log.d("initViews", "initViews: " + this.myHobbies);
    }

    private void setHobbiesFromCallback(ArrayList<Hobby> allHobbies){
        for(Hobby hobby: allHobbies){
            this.myHobbies.add(hobby);
        }
        Log.d("setHobbiesFromCallback", "setHobbiesFromCallback: " + this.myHobbies.size());
        Log.d("setHobbiesFromCallback", "setHobbiesFromCallback: " + this.myHobbies.toString());
    }

    private void findViews(View view) {
        board_LST_hobbies = view.findViewById(R.id.board_LST_hobbies);
    }


}
