package com.example.todoexample;

import static com.example.todoexample.AddNewTask.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.todoexample.Utils.DatabaseHandler;
import com.example.todoexample.adapter.todoadapter;
import com.example.todoexample.calendar.CalendarMainActivity;
import com.example.todoexample.model.todomodel;
import com.example.todoexample.receiver.updateWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasksrecyclerview;
    private todoadapter tasksadapter;
    private FloatingActionButton fab;
    private ImageButton settings, change_page, savetowidget;

    private List<todomodel> tasklist;
    private DatabaseHandler db;

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasklist = new ArrayList<>();

        tasksrecyclerview = findViewById(R.id.taskrecyclerview);
        tasksrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        tasksadapter = new todoadapter(db, this);
        tasksrecyclerview.setAdapter(tasksadapter);
        //things are happening

        fab = findViewById(R.id.fab);
        settings = findViewById(R.id.settings);
        change_page = findViewById(R.id.change_page);
        savetowidget = findViewById(R.id.savetowidget);

        ItemTouchHelper itemTouchHelper = new
        ItemTouchHelper(new RecyclerItemTouchHelper(tasksadapter));
        itemTouchHelper.attachToRecyclerView(tasksrecyclerview);

        tasklist = db.getAllTasks();
        Intent intent = new Intent(MainActivity.this, updateWidget.class);
        sendBroadcast(intent);
        //Collections.reverse(tasklist);
        tasksadapter.setTasks(tasklist);
        tasksadapter.setDate_dues(tasklist);

        Log.d(TAG, "------ onCreate has ran ------ ");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), TAG);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, Settings2.class);
                //startActivity(intent);
                Settings.newInstance().show(getSupportFragmentManager(), TAG);
            }
        });

        change_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarMainActivity.class);
                startActivity(intent);
            }
        });

        savetowidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateWidget();
                Intent intent = new Intent(MainActivity.this, updateWidget.class);
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "tasks successfully saved to widget!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasklist = db.getAllTasks();
        //Collections.reverse(tasklist);
        tasksadapter.setTasks(tasklist);
        tasksadapter.setDate_dues(tasklist);
        tasksadapter.notifyDataSetChanged();
    }
}