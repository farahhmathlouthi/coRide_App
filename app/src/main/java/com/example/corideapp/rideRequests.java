package com.example.corideapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rideRequests extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter adapter;
    private List<RideRequest> rideRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ride_requests);

        listView = findViewById(R.id.listView);
        rideRequestList = new ArrayList<>();
        adapter = new CustomAdapter(this, rideRequestList);
        listView.setAdapter(adapter);

        retrieveRideRequestsFromFirebase();

    }

    private void retrieveRideRequestsFromFirebase() {

        DatabaseReference rideRequestsRef = FirebaseDatabase.getInstance().getReference().child("ride_requests");
        rideRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rideRequestList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RideRequest rideRequest = snapshot.getValue(RideRequest.class);
                    if (rideRequest != null) {
                        rideRequestList.add(rideRequest);
                    }
                }
                adapter.notifyDataSetChanged();

                // If no ride requests found, show a message
                if (rideRequestList.isEmpty()) {
                    // You can display a message here or handle it as needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });

    }
}