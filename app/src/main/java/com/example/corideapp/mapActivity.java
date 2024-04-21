package com.example.corideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class mapActivity extends FragmentActivity implements OnMapReadyCallback {
        private GoogleMap mMap;
        FrameLayout map;
        String editTextClicked;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);

            map = findViewById(R.id.map);
            editTextClicked = getIntent().getStringExtra("edit_text");

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         *
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            // Add a marker in Tunisia and move the camera
            LatLng tunisia = new LatLng(33.7931605, 9.5607653);
            mMap.addMarker(new MarkerOptions()
                    .position(tunisia)
                    .title("Marker in Tunisia"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tunisia));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    // Pass back the selected location and the edit_text extra
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("selected_location", latLng);
                    resultIntent.putExtra("edit_text", editTextClicked);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            });
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    // Return null to use default info window behavior
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Customize the contents of the info window to change cursor appearance
                    // Here you can inflate a custom layout or modify the appearance of the default info window contents
                    // For example, you can change the background color, text color, etc.
                    // Return null to use default info window behavior
                    return null;
                }
            });
        }
    }


