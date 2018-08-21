package com.technology.hisabKitab.fragments;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.technology.hisabKitab.Model.AddEntery;


import java.util.ArrayList;

public class Firebase {
    DatabaseReference db;
    Boolean saved;
    ArrayList<AddEntery> users = new ArrayList<>();

    public Firebase(DatabaseReference db) {
        this.db = db;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        users.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            AddEntery user = ds.getValue(AddEntery.class);
            users.add(user);
        }
    }

    public ArrayList<AddEntery> retrieve() {


        db.addValueEventListener(new ValueEventListener() {

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
