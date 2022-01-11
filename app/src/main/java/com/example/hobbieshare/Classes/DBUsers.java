package com.example.hobbieshare.Classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBUsers {

    private DatabaseReference databaseReference;

    public void DBUsers() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> addUser(User user) {
//        if(user == null) //throw exception
         return databaseReference.push().setValue(user);
    }





}
