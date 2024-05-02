package com.example.corideapp;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class rideTaker extends AppCompatActivity {

    EditText depart;
    EditText arrive;
    Button continueC,viewtrip;
    Drawable logoDrawable;
    int selectedYear, selectedMonth, selectedDayOfMonth;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    int year, month, dayOfMonth;
    TextView editime, editdate;
    int t1Hour, t1Minute, t2Hour, t2Minute;
    private final static int MAP_REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_taker);

        // Initialize views
        depart = findViewById(R.id.depart1);
        arrive = findViewById(R.id.arrive);
        continueC = findViewById(R.id.sendR);
        editime = findViewById(R.id.editime1); // Initialize editime TextView
        editdate = findViewById(R.id.editdate1); // Initialize editdate TextView
        viewtrip = findViewById(R.id.continueD);

        mAuth = FirebaseAuth.getInstance();


        depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rideTaker.this, mapActivity.class);
                intent.putExtra("edit_text", "depart"); // Pass information about which EditText was clicked
                startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });

        arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rideTaker.this, mapActivity.class);
                intent.putExtra("edit_text", "arrive"); // Pass information about which EditText was clicked
                startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });



        viewtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to draw a track on google maps.
                drawTrack(depart.getText().toString(), arrive.getText().toString());
            }
        });
        continueC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from EditText fields
                String departure = depart.getText().toString();
                String arrival = arrive.getText().toString();
                String date = editdate.getText().toString();
                String time = editime.getText().toString();

                // Save ride request to Firebase
                saveRideRequestToFirebase(departure, arrival, date, time);
                Intent intent = new Intent(rideTaker.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize logo drawable
        logoDrawable = ContextCompat.getDrawable(this, R.drawable.logo);
        logoDrawable.setBounds(0, 0, logoDrawable.getIntrinsicWidth(), logoDrawable.getIntrinsicHeight());

        // Set the compound drawables for EditTexts
        arrive.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);
        depart.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);

        // Set click listeners for time pickers
        editime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        rideTaker.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour = hourOfDay;
                                t1Minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t1Hour, t1Minute);
                                editime.setText(String.format("%02d:%02d", t1Hour, t1Minute));

                                // Convert to 12-hour format with AM/PM indication for robustness
                                String time = t1Hour + ":" + t1Minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    editime.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    // Handle parsing exception
                                    // You can display an error message or take appropriate action here
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t1Hour, t1Minute);
                timePickerDialog.show();
            }
        });

        // Assuming editDate is your EditText for selecting the date
        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Initialize date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        rideTaker.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Store selected date
                                selectedYear = year;
                                selectedMonth = monthOfYear;
                                selectedDayOfMonth = dayOfMonth;

                                // Set selected date to editDate EditText
                                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedMonth + 1, selectedDayOfMonth, selectedYear);
                                editdate.setText(selectedDate);
                            }
                        },
                        year, month, dayOfMonth
                );

                // Show date picker dialog
                datePickerDialog.show();
            }
        });

        // Apply system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveRideRequestToFirebase(String departure, String arrival, String date, String time) {
        if (departure.isEmpty() || arrival.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(rideTaker.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user ID
        String userId = mAuth.getCurrentUser().getUid();
        // Reference to the "users" table to retrieve user information
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve user information
                String userName = dataSnapshot.child("name").getValue(String.class);
                String userPhone = dataSnapshot.child("phone").getValue(String.class);

                // Create a HashMap to store ride request details
                HashMap<String, Object> rideRequestMap = new HashMap<>();
                rideRequestMap.put("departure", departure);
                rideRequestMap.put("arrival", arrival);
                rideRequestMap.put("date", date);
                rideRequestMap.put("time", time);
                rideRequestMap.put("userName", userName); // Add user name to the ride request
                rideRequestMap.put("userPhone", userPhone); // Add user phone to the ride request

                // Push the ride request to Firebase
                DatabaseReference rideRequestsRef = FirebaseDatabase.getInstance().getReference().child("ride_requests");
                rideRequestsRef.push().setValue(rideRequestMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Ride request saved successfully
                                Toast.makeText(rideTaker.this, "Ride request created successfully", Toast.LENGTH_SHORT).show();
                                // Optionally, you can navigate to another activity or perform additional actions here
                                readDataFromOtherTable();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save ride request
                                Toast.makeText(rideTaker.this, "Failed to create ride request", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }

    private void readDataFromOtherTable() {
        DatabaseReference otherTableRef = FirebaseDatabase.getInstance().getReference().child("users");
        otherTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey(); // Retrieve the user ID
                    String name = snapshot.child("name").getValue(String.class); // Retrieve the name from the user data
                    String phone = snapshot.child("phone").getValue(String.class); // Retrieve the phone number from the user data

                    // Now you can use 'name' and 'phone' to access the retrieved data
                    // For example, you can use them to update the ride request data
                    // or perform any other operations as needed.
                    Log.d(TAG, "Name: " + name + ", Phone: " + phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }



    private void drawTrack(String source, String destination) {
        try {
            // create a uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);

            // initializing a intent with action view.
            Intent i = new Intent(Intent.ACTION_VIEW, uri);

            // below line is to set maps package name
            i.setPackage("com.google.android.apps.maps");

            // below line is to set flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // start activity
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // when the google maps is not installed on users device
            // we will redirect our user to google play to download google maps.
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            // initializing intent with action view.
            Intent i = new Intent(Intent.ACTION_VIEW, uri);

            // set flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // to start activity
            startActivity(i);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == MAP_REQUEST_CODE) {
            if (data != null && data.hasExtra("selected_location")) {
                LatLng selectedLocation = data.getParcelableExtra("selected_location");
                String selectedAddress = getAddressFromLatLng(selectedLocation); // Implement reverse geocoding here

                // Determine which EditText was clicked
                if (data.hasExtra("edit_text")) {
                    String editText = data.getStringExtra("edit_text");
                    if (editText != null && !editText.isEmpty()) {
                        if (editText.equals("depart")) {
                            depart.setText(selectedAddress);
                        } else if (editText.equals("arrive")) {
                            arrive.setText(selectedAddress);
                        }
                    }
                }
            }
        }
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String addressText = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1); // Maximum number of addresses to return

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // Concatenate address lines and add to addressText
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressText += (i == 0) ? address.getAddressLine(i) : ("\n" + address.getAddressLine(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressText;
    }
}
