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

public class rideTaker extends AppCompatActivity {

    EditText depart;
    EditText arrive;
    Button continueC;
    Drawable logoDrawable;
    int selectedYear, selectedMonth, selectedDayOfMonth;
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
        continueC = findViewById(R.id.continueD);


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

        continueC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to draw a track on google maps.
                drawTrack(depart.getText().toString(), arrive.getText().toString());
            }
        });
        editime = findViewById(R.id.editime1);
        editdate = findViewById(R.id.editdate1);

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
