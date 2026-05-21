package com.example.todoexample.calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoexample.MainActivity;
import com.example.todoexample.OnSwipeTouchListener;
import com.example.todoexample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarMainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, GestureDetector.OnGestureListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private FloatingActionButton fab;
    private ImageButton prevmonth, nextmonth;
    private GestureDetector gestureDetector;
    private ImageView imageView;

    public String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.calendar_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        // this is the view we will add the gesture detector to
        View myView = findViewById(R.id.imageview);
        //gestureDetector = new GestureDetector(CalendarMainActivity.this);

        myView.setOnTouchListener(new OnSwipeTouchListener(CalendarMainActivity.this) {
                                      public void onSwipeRight() {
                                          previousMonthAction();
                                          //Toast.makeText(CalendarMainActivity.this, "right", Toast.LENGTH_SHORT).show();
                                      }

                                      public void onSwipeLeft() {
                                          nextMonthAction();
                                          //Toast.makeText(CalendarMainActivity.this, "left", Toast.LENGTH_SHORT).show();
                                      }
                                  });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        prevmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction();
            }
        });

        nextmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction();
            }
        });

    }

    private void initWidgets()
    {
        fab = findViewById(R.id.change_page2);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

        prevmonth = findViewById(R.id.prevmonth);
        nextmonth = findViewById(R.id.nextmonth);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(""); }
            else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek)); }
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        if (date.getMonthValue() < 10) {
            return "0" + date.getMonthValue() + "/" + date.getYear();
        } else {
            return date.getMonthValue() + "/" + date.getYear();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction()
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction()
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            //String message = "Selected Date " + dayText + " " + selectedDate;
            if (Integer.parseInt(dayText) < 10) {
                dayText = "0" + dayText;
            }
            date =  dayText + "/" + monthYearFromDate(selectedDate);
            //String message = "Selected Date " + date;
            //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CalendarMainActivity.this, CalendarDailyView.class);
            intent.putExtra("date_daily", date);
            startActivity(intent);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}








