package com.example.corideapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
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
    Drawable logoDrawable;
    TextView tvTimer1, tvTimer2;
    int t1Hour, t1Minute, t2Hour, t2Minute;
    private final static int MAP_REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_taker);

        // Initialize views
        depart = findViewById(R.id.depart);
        arrive = findViewById(R.id.arrive);

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

        tvTimer1 = findViewById(R.id.tvTimer1);
        tvTimer2 = findViewById(R.id.tvTimer2);

        // Initialize logo drawable
        logoDrawable = ContextCompat.getDrawable(this, R.drawable.logo);
        logoDrawable.setBounds(0, 0, logoDrawable.getIntrinsicWidth(), logoDrawable.getIntrinsicHeight());

        // Set the compound drawables for EditTexts
        arrive.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);
        depart.setCompoundDrawablesWithIntrinsicBounds(null, null, logoDrawable, null);

        // Set click listeners for time pickers
        tvTimer1.setOnClickListener(new View.OnClickListener() {
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
                                tvTimer1.setText(String.format("%02d:%02d", t1Hour, t1Minute));

                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t1Hour, t1Minute);
                timePickerDialog.show();
            }
        });

        tvTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        rideTaker.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                String time = t2Hour + ":" + t2Minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    tvTimer2.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                timePickerDialog.updateTime(t2Hour, t2Minute);
                timePickerDialog.show();
            }
        });

        // Apply system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
