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

public class give_RideRequest extends AppCompatActivity {

    private ListView listView1;
    private CustomAdapter2 adapter1;
    private List<RideRequest2> rideRequestList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_give_ride_request);

        listView1 = findViewById(R.id.listView1);
        rideRequestList1 = new ArrayList<>();
        adapter1 = new CustomAdapter2(this, rideRequestList1);
        listView1.setAdapter(adapter1);

        retrieveRideRequestsFromFirebase();

    }

    private void retrieveRideRequestsFromFirebase() {

        DatabaseReference rideRequestsRef = FirebaseDatabase.getInstance().getReference().child("give_ride_requests");
        rideRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rideRequestList1.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RideRequest2 rideRequest2 = snapshot.getValue(RideRequest2.class);
                    if (rideRequest2 != null) {
                        rideRequestList1.add(rideRequest2);
                    }
                }
                adapter1.notifyDataSetChanged();

                // If no ride requests found, show a message
                if (rideRequestList1.isEmpty()) {
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