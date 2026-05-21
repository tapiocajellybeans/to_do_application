package com.example.todoexample.receiver;

import static com.example.todoexample.AddNewTask.TAG;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todoexample.DestinationActivity;
import com.example.todoexample.R;
import com.example.todoexample.Utils.DatabaseHandler;

public class AlarmReceiver extends BroadcastReceiver {

    public StringBuilder textdisplayed = new StringBuilder();
    private DatabaseHandler dbhelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHandler dbhelper = new DatabaseHandler(context);

        Cursor cursor = dbhelper.getTasks();
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(0);
                textdisplayed.append(text + ", ");
            } while (cursor.moveToNext());
        }

        Intent i = new Intent(context, DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_baseline_check_24)
                .setContentTitle("tasks:")
                .setContentText(textdisplayed)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        Log.d(TAG, "onReceive: " + textdisplayed);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
