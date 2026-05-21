package com.example.todoexample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.todoexample.Utils.DatabaseHandler;
import com.example.todoexample.model.todomodel;
import com.example.todoexample.receiver.updateWidget;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog"; //tag name

    private EditText newTaskText;
    private Button newTaskSaveButton;
    private Calendar calendarNewTaskDateDue;
    private EditText newTaskDateDue;
    private DatabaseHandler db;

    public String date;
    public Date c;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); // bottom sheet dialog to move or sth
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newtaskname);
        newTaskDateDue = getView().findViewById(R.id.newtaskdatedue);
        newTaskSaveButton = getView().findViewById(R.id.newtasksavebutton);
        // here

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        //diff between updated or saved
        boolean isUpdate = false; //check if update task or create new task
        final Bundle bundle = getArguments(); //parse data to fragments
        if (bundle != null) { //isit an update
            isUpdate = true;
            String task = bundle.getString("task");
            String date_due = bundle.getString("date_due");
            newTaskText.setText(task);

            //date setter
            date = date_due;
            newTaskDateDue.setText(date);

            /*String parts[] = date_due.split("/");

            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1])-1;
            int year = Integer.parseInt(parts[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            long milliTime = calendar.getTimeInMillis();

            newTaskDateDue.setDate(milliTime,true, true);*/
            //endofDateSetter

            if (task.length() > 0 || date_due.length() > 0) { //isit empty
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }
        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskText.setTextColor(Color.GRAY);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });//change color of length of task
        //if there is no text, you cannot save -> grey color

        /*newTaskDateDue.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + "/" + (i1 + 1) + "/" + i; //ddmmyyyy
            }
        });*/

        calendarNewTaskDateDue = Calendar.getInstance();
        int y = calendarNewTaskDateDue.get(Calendar.YEAR);
        int m = calendarNewTaskDateDue.get(Calendar.MONTH);
        int d =calendarNewTaskDateDue.get(Calendar.DAY_OF_MONTH);

        newTaskDateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        (datePicker, y1, m1, d1) -> {
                            date = d1 + "/" + (m1 +1) + "/" + y1;
                            newTaskDateDue.setText(date);
                        }, y, m, d );
                //Disable past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                String date_due = date;

                String pattern = "dd/MM/yyyy";
                DateFormat df = new SimpleDateFormat(pattern);

                if (date_due == null) {
                    c = Calendar.getInstance().getTime();
                } else {
                    try {
                        c = new SimpleDateFormat("dd/MM/yyyy").parse(date_due);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date_due = df.format(c);

                if (finalIsUpdate) { //if you are trying to update task
                    db.updateTaskName(bundle.getInt("id"), text, getContext());
                    db.updateTaskDateDue(bundle.getInt("id"), date_due, getContext());
                } else {
                        todomodel task = new todomodel();
                        task.setTask(text);
                        task.setDate_due(date_due);
                        task.setStatus(0);
                        db.insertTask(task, getContext());
                    }
                dismiss();
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }


}
