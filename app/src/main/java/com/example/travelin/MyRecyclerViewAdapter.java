package com.example.travelin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context c;
    String[] daysOfWeek, temperature;

    public MyRecyclerViewAdapter(Context c, String[] daysOfWeek, String[] temperature) {
        this.c = c;
        this.daysOfWeek = daysOfWeek;
        this.temperature = temperature;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.my_row_layout, parent, false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1.setText(daysOfWeek[position]);
        holder.text2.setText(temperature[position]);
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.length;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.daysOfWeek);
            text2 = itemView.findViewById(R.id.temperature);

        }



    }

}

