package com.example.todoexample.Utils;

import static com.example.todoexample.AddNewTask.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todoexample.MainActivity;
import com.example.todoexample.model.todomodel;
import com.example.todoexample.receiver.updateWidget;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 6; //*** rem to change version to update SQL
    private static final String NAME = "toDoListDatabase"; //name of sql
    private static final String TODO_TABLE = "todo"; //table name
    private static final String ID = "id"; //id - pri key
    private static final String TASK = "task"; // tasks
    private static final String DATE_DUE = "date_due"; //date due
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " +
            DATE_DUE + " DATE, " + STATUS + " INTEGER)";
    // sql query in a string
    private SQLiteDatabase db;//ref of the sql database

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);//execute SQL query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //update the SQL
        //drop the older table
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //create new table
        onCreate(db); //oncreate method that will create a new table for us
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(todomodel task, Context context) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(DATE_DUE, task.getDate_due());
        cv.put(STATUS, 0);//all tasks are unchecked
        db.insert(TODO_TABLE, null, cv);

        Intent intent = new Intent(context, updateWidget.class);
        context.sendBroadcast(intent);

    }

    @SuppressLint("Recycle")
    public List<todomodel> getAllTasks(){
        List<todomodel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            cur = db.rawQuery(" SELECT * FROM " + TODO_TABLE + " ORDER BY " + DATE_DUE + " ASC ", null);
            if (cur != null) {
                if(cur.moveToFirst()) {
                    do{
                        todomodel task = new todomodel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setDate_due(cur.getString(cur.getColumnIndexOrThrow(DATE_DUE)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext()); //while cur not empty, it will run
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }

        return taskList;
    }

    public void updateStatus(int id, int status, Context context) { //update checked or unchecked
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});

        Intent intent = new Intent(context, updateWidget.class);
        context.sendBroadcast(intent);
    }

    public void updateTaskName(int id, String task, Context context) { //update task name
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});

        Intent intent = new Intent(context, updateWidget.class);
        context.sendBroadcast(intent);
    }

    public void updateTaskDateDue(int id, String date_due, Context context) { //update task name
        ContentValues cv = new ContentValues();
        cv.put(DATE_DUE, date_due);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});

        Intent intent = new Intent(context, updateWidget.class);
        context.sendBroadcast(intent);
    }

    public void deleteTask(int id, Context context) {
        db.delete(TODO_TABLE, ID + "=?",  new String[] {String.valueOf(id)});

        Intent intent = new Intent(context, updateWidget.class);
        context.sendBroadcast(intent);
    }

    public Cursor getTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(" SELECT " + TASK + " FROM " + TODO_TABLE, null);
    }

    public Cursor getTasksforDaily(String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(" SELECT " + TASK + " FROM " + TODO_TABLE + " WHERE " + DATE_DUE + " LIKE '%" + day +"%';" , null);
    }

}
