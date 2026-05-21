package com.example.todoexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoexample.MainActivity;
import com.example.todoexample.R;
import com.example.todoexample.Utils.DatabaseHandler;
import com.example.todoexample.calendar.CalendarDailyView;
import com.example.todoexample.calendar.CalendarMainActivity;
import com.example.todoexample.model.todomodel;

import java.util.ArrayList;
import java.util.List;

public class date_dailyadapter extends RecyclerView.Adapter<date_dailyadapter.ViewHolder> {

    private LayoutInflater mInflater;
    private CalendarDailyView activity;
    private List<todomodel> tasklist;
    private List<String> mData;

    // data is passed into the constructor
    public date_dailyadapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.date_dailylayout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //todomodel item = tasklist.get(holder.getAdapterPosition()); //get the position, and search for the item
        //holder.tasklist_daily.setText(item.getTask());// task - (see ViewHolder(View view)), item.getTask is under todomodel

        String animal = mData.get(position);
        holder.tasklist_daily.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tasklist_daily;

        ViewHolder(View itemView) {
            super(itemView);
            tasklist_daily = itemView.findViewById(R.id.taskstext_daily);
        }
    }
}