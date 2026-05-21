package com.example.todoexample.adapter;

import static com.example.todoexample.AddNewTask.TAG;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoexample.AddNewTask;
import com.example.todoexample.MainActivity;
import com.example.todoexample.R;
import com.example.todoexample.Utils.DatabaseHandler;
import com.example.todoexample.model.todomodel;

import java.util.Collections;
import java.util.List;

public class todoadapter extends RecyclerView.Adapter<todoadapter.ViewHolder> {

    private List<todomodel> todolist;
    private MainActivity activity;
    private DatabaseHandler db;

    public todoadapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasklayout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        db.openDatabase();

        todomodel item = todolist.get(holder.getAdapterPosition()); //get the position, and search for the item
        holder.task.setText(item.getTask());// task - (see ViewHolder(View view)), item.getTask is under todomodel
        holder.date_due_TV.setText(item.getDate_due());
        holder.task.setChecked(toBoolean(item.getStatus()));//get status of whether done or not done

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d(TAG, "onCheckedChanged: 1 " + holder.getAdapterPosition());
                    db.updateStatus(item.getId(), 1, getContext());
                    deleteItem(holder.getAdapterPosition());
                    //color = "#424242";
                }else{
                    Log.d(TAG, "onCheckedChanged: 0 " + item.getId());
                    db.updateStatus(item.getId(), 0, getContext());
                    //color = "#FFFFFF";
                }
            }
        });

    }

    public int getItemCount() {
        return todolist.size();
    }

    private Boolean toBoolean(int number) {
        return number != 0;
    }

    public void setTasks(List<todomodel> todolist) {
        this.todolist = todolist;
        notifyDataSetChanged(); //notify that data is updated
    }

    public void setDate_dues(List<todomodel> todolist) {
        this.todolist = todolist;
        notifyDataSetChanged(); //notify that data is updated
    }

    public Context getContext(){ return activity; }

    public void deleteItem(int position) { //del task func
        todomodel item = todolist.get(position);
        db.deleteTask(item.getId(), getContext());
        todolist.remove(position);
        notifyItemRemoved(position); //update recycler view
    }

    public void editItem(int position) {
        todomodel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        bundle.putString("date_due", item.getDate_due());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task; //for each checkbox in the task
        TextView date_due_TV;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById((R.id.todocheckbox)); //task is the checkbox + name of task
            date_due_TV = view.findViewById(R.id.date_due);
        }
    }
}
