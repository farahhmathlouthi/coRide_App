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

    public class CustomAdapter extends ArrayAdapter<RideRequest> {
        private Context context;
        private List<RideRequest> rideRequestList;

        public CustomAdapter(Context context, List<RideRequest> rideRequestList) {
            super(context, R.layout.take_card, rideRequestList);
            this.context = context;
            this.rideRequestList = rideRequestList;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.take_card, parent, false);
            }

            RideRequest rideRequest = rideRequestList.get(position);

            TextView tvArrival = convertView.findViewById(R.id.arrivalTextView1);
            TextView tvDeparture = convertView.findViewById(R.id.departureTextView1);
            TextView tvDate = convertView.findViewById(R.id.dateTextView1);
            TextView tvTime = convertView.findViewById(R.id.timeTextView1);
            TextView tvName = convertView.findViewById(R.id.nameTextView1);
            TextView tvPhone = convertView.findViewById(R.id.phoneTextView1);

            tvArrival.setText(rideRequest.getArrival());
            tvDeparture.setText(rideRequest.getDeparture());
            tvDate.setText(rideRequest.getDate());
            tvTime.setText(rideRequest.getTime());
            tvName.setText(rideRequest.getUserName());
            tvPhone.setText(rideRequest.getUserPhone());

            return convertView;
        }
}
