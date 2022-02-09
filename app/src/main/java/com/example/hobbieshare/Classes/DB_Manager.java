package com.example.hobbieshare.Classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hobbieshare.CallBacks.Callback_Counter;
import com.example.hobbieshare.CallBacks.Callback_Hobbies;
import com.example.hobbieshare.CallBacks.Callback_Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DB_Manager {

//    public interface Callback_tmpHobby {
//        void dataReady(ArrayList<Hobby> allHobbies);
//    }

    public static void getAllHobbies(Callback_Hobbies callback_hobbies) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hobbies");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Hobby> allHobbies = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        Hobby hobbyEvent = child.getValue(Hobby.class);
                        Log.d("PTT", "onDataChange: hobbyEvent:" + hobbyEvent.toString());
                        allHobbies.add(hobbyEvent);
                    } catch (Exception exception) { }
                }

                if (callback_hobbies != null) {
                    callback_hobbies.dataReady(allHobbies);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public static void getHobbiesOfCurrUser(Callback_Hobbies callback_hobbies) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("userHobbies");
        Log.d("getHobbiesOfCurrUser", "getHobbiesOfCurrUser: my ref" + myRef);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Hobby> allHobbies = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        Hobby hobbyEvent = child.getValue(Hobby.class);
                        allHobbies.add(hobbyEvent);
                    } catch (Exception exception) { }
                }

                if (callback_hobbies != null) {
                    callback_hobbies.dataReady(allHobbies);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public static void getAllUsers(Callback_Users callback_users) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> allUsers = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        User user = child.getValue(User.class);
                        allUsers.add(user);
                        Log.d("getAllUsers", "onDataChange: users in DB Manager: " + user.toString());
                    } catch (Exception exception) {

                    }
                }
                if (callback_users != null) {
                    callback_users.dataReady(allUsers);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void getCounter(String counterType, Callback_Counter callback_counter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Counter_BD");
        myRef.child(counterType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = Integer.parseInt(snapshot.getValue().toString());
                if (callback_counter != null) {
                    callback_counter.dataReady(counter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void setCounter(String counterType, int value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Counter_BD");
        myRef.child(counterType).setValue(value);
    }

    public static void updateDB(User user, Hobby hobby) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference myUsersRef = database.getReference("users");
        DatabaseReference myHobbiesRef = database.getReference("hobbies");

        myUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myUsersRef.child(firebaseUser.getUid()).child("userHobbies").setValue(user.getUserHobbies());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myHobbiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myHobbiesRef.child(""+hobby.getEventId()).child("participants").setValue(hobby.getParticipants());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
