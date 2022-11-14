package com.example.travelin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyFlightRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightRecyclerViewAdapter.MyViewHolder> {

    Context c;
    Flight[] flights;

    public MyFlightRecyclerViewAdapter(Context c, Flight[] flights) {
        this.c = c;
        this.flights = flights;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.flight_row_layout, parent, false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1.setText(flights[position].get_airline_name());
        System.out.println(flights[1].get_airline_name());
    }

    @Override
    public int getItemCount() {
        return flights.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.flight_name);
        }
    }
}
