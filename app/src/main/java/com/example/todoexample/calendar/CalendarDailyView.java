package com.example.todoexample.calendar;

import static com.example.todoexample.AddNewTask.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoexample.R;
import com.example.todoexample.Utils.DatabaseHandler;
import com.example.todoexample.adapter.date_dailyadapter;

import java.util.ArrayList;

public class CalendarDailyView extends AppCompatActivity {

    private TextView displaydate;
    private RecyclerView displaytasks;
    private DatabaseHandler dbhelper;

    date_dailyadapter adapter;

    public String dateselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendardailyview);
        getSupportActionBar().hide();

        displaydate = findViewById(R.id.displaydates);
        displaytasks = findViewById(R.id.taskrecyclerview_daily);

        Intent intent = getIntent();
        dateselected = intent.getStringExtra("date_daily");

        displaydate.setText(dateselected);

        DatabaseHandler dbhelper = new DatabaseHandler(this);
        // data to populate the RecyclerView with
        ArrayList<String> tasknames = new ArrayList<>();

        Cursor cursor = dbhelper.getTasksforDaily(dateselected);
        if (cursor.moveToFirst()) {
            do {
                String text =  cursor.getString(0);
                Log.d(TAG, "tasks for " + dateselected + " are as follows: " + text);
                tasknames.add(text);
            } while (cursor.moveToNext());
        }

        // set up the RecyclerView
        displaytasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new date_dailyadapter(this, tasknames);
        displaytasks.setAdapter(adapter);

    }


}
