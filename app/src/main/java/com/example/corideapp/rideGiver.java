package com.example.corideapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class rideGiver extends AppCompatActivity {

    EditText depart1;
    EditText arrive1;
    Button continueD;
    Drawable logoDrawable;
    int selectedYear1, selectedMonth1, selectedDayOfMonth1;
    int year1, month1, dayOfMonth1;
    TextView editime1, editdate1;
    int t1Hour1, t1Minute1, t2Hour, t2Minute;
    private final static int MAP_REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_giver);

        // Initialize views
        depart1 = findViewById(R.id.depart1);
        arrive1 = findViewById(R.id.arrive1);
        continueD = findViewById(R.id.continueD);


        depart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rideGiver.this, mapActivity.class);
                intent.putExtra("edit_text", "depart"); // Pass information about which EditText was clicked
                startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });

        arrive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rideGiver.this, mapActivity.class);
                intent.putExtra("edit_text", "arrive"); // Pass information about which EditText was clicked
                startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });

        continueD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to draw a track on google maps.
                drawTrack(depart1.getText().toString(), arrive1.getText().toString());
            }
        });
        editime1 = findViewById(R.id.editime1);
        editdate1 = findViewById(R.id.editdate1);

        // Initialize logo drawable
        logoDrawable = ContextCompat.getDrawable(this, R.drawable.logo);
        logoDrawable.setBounds(0, 0, logoDrawable.getIntrinsicWidth(), logoDrawable.getIntrinsicHeight());

        // Set the compound drawables for EditTexts
        arrive1.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);
        depart1.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);

        // Set click listeners for time pickers
        editime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        rideGiver.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour1 = hourOfDay;
                                t1Minute1 = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t1Hour1, t1Minute1);
                                editime1.setText(String.format("%02d:%02d", t1Hour1, t1Minute1));

                                // Convert to 12-hour format with AM/PM indication for robustness
                                String time = t1Hour1 + ":" + t1Minute1;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    editime1.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    // Handle parsing exception
                                    // You can display an error message or take appropriate action here
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t1Hour1, t1Minute1);
                timePickerDialog.show();
            }
        });

        // Assuming editDate is your EditText for selecting the date
        editdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                final Calendar calendar = Calendar.getInstance();
                year1 = calendar.get(Calendar.YEAR);
                month1 = calendar.get(Calendar.MONTH);
                dayOfMonth1 = calendar.get(Calendar.DAY_OF_MONTH);

                // Initialize date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        rideGiver.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Store selected date
                                selectedYear1 = year;
                                selectedMonth1 = monthOfYear;
                                selectedDayOfMonth1 = dayOfMonth;

                                // Set selected date to editDate EditText
                                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedMonth1 + 1, selectedDayOfMonth1, selectedYear1);
                                editdate1.setText(selectedDate);
                            }
                        },
                        year1, month1, dayOfMonth1
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
                            depart1.setText(selectedAddress);
                        } else if (editText.equals("arrive")) {
                            arrive1.setText(selectedAddress);
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
