package com.technology.hisabKitab.fragments;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.technology.hisabKitab.Model.User;


import java.util.ArrayList;

public class FireBaseHelper {
    DatabaseReference db;
    Boolean saved;
    ArrayList<User> users = new ArrayList<>();

    public FireBaseHelper(DatabaseReference db) {
        this.db = db;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
         users.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User user = ds.getValue(User.class);
            users.add(user);
        }
    }

    public ArrayList<User> retrieve() {


        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return users;
    }

}
