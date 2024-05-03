package com.example.corideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.List;

public class CustomAdapter2 extends ArrayAdapter<RideRequest2> {
    private Context context;
    private List<RideRequest2> rideRequestList;

    public CustomAdapter2(Context context, List<RideRequest2> rideRequestList) {
        super(context, R.layout.ride_card, rideRequestList);
        this.context = context;
        this.rideRequestList = rideRequestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ride_card, parent, false);
        }

        RideRequest2 rideRequest1 = rideRequestList.get(position);

        TextView tvArrival = convertView.findViewById(R.id.arrivalTextView3);
        TextView tvDeparture = convertView.findViewById(R.id.departureTextView3);
        TextView tvTime = convertView.findViewById(R.id.timeTextView3);
        TextView tvDate = convertView.findViewById(R.id.dateTextView3);
        TextView tvName = convertView.findViewById(R.id.NameTextView3);
        TextView tvPhone = convertView.findViewById(R.id.PhoneTextView3);
        TextView tvPlaces = convertView.findViewById(R.id.placeTextView3);

        tvArrival.setText(rideRequest1.getArrival2());
        tvDeparture.setText(rideRequest1.getDeparture2());
        tvTime.setText(rideRequest1.getTime2());
        tvDate.setText(rideRequest1.getDate2());
        tvName.setText(rideRequest1.getName2());
        tvPhone.setText(rideRequest1.getPhone2());
        tvPlaces.setText(rideRequest1.getPlaces2());

        return convertView;
    }

}