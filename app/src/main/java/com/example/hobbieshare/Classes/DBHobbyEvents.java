package com.example.hobbieshare.Classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHobbyEvents {

    private DatabaseReference databaseReference;

    public void DBHobbyEvents() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(HobbyEvent.class.getSimpleName());
    }

    public Task<Void> addHobbyEvent(HobbyEvent hobbyEvent) {
//        if(hobbyEvent == null) //throw exception
        return databaseReference.push().setValue(hobbyEvent);
    }
}
