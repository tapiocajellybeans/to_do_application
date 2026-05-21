package com.example.todoexample.receiver;

import static com.example.todoexample.AddNewTask.TAG;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.example.todoexample.MainActivity;
import com.example.todoexample.ToDoWidget;
import com.example.todoexample.Utils.DatabaseHandler;

public class updateWidget extends BroadcastReceiver {
    
    SharedPreferences sp;
    
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder textdisplayed = new StringBuilder();
        DatabaseHandler dbhelper = new DatabaseHandler(context);
        textdisplayed.append("tasks due : \n");
        Cursor cursor = dbhelper.getTasks();
        int counter = 1;
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(0);
                textdisplayed.append(counter + ". " + text + "\n");
                counter ++;
            } while (cursor.moveToNext());
        }

        sp = context.getSharedPreferences("alltasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tasks", String.valueOf(textdisplayed));
        Log.d(TAG, String.valueOf(textdisplayed));
        editor.commit();

        intent = new Intent(context, ToDoWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, ToDoWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}
