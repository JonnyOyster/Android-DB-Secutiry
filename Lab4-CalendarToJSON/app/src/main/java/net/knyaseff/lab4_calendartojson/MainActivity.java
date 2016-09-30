package net.knyaseff.lab4_calendartojson;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    EditText taskField;
    Button addButton;
    Button editButton;
    Button removeButton;

    int currentDate;
    int currentMonth;
    int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = (CalendarView) findViewById(R.id.calendar);
        taskField = (EditText) findViewById(R.id.taskField);
        addButton = (Button) findViewById(R.id.addButton);
        editButton = (Button) findViewById(R.id.editButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        check(currentDate, currentMonth, currentYear);
        controlCalendar();

    }

    public void controlCalendar() {
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createNewTask(currentDate, currentMonth, currentYear);
                Toast.makeText(getApplicationContext(), "Add success",
                        Toast.LENGTH_SHORT).show();
                check(currentDate, currentMonth, currentYear);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeTaskForToday(currentDate, currentMonth, currentYear);
                createNewTask(currentDate, currentMonth, currentYear);
                Toast.makeText(getApplicationContext(), "Edit success",
                        Toast.LENGTH_SHORT).show();
                check(currentDate, currentMonth, currentYear);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeTaskForToday(currentDate, currentMonth, currentYear);
                Toast.makeText(getApplicationContext(), "Remove success",
                        Toast.LENGTH_SHORT).show();
                check(currentDate, currentMonth, currentYear);
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                currentDate = dayOfMonth;
                currentMonth = month;
                currentYear = year;
                check(currentDate, currentMonth, currentYear);
            }
        });
    }

    public void createNewTask(int date, int month, int year) {
        Task task = new Task(taskField.getText().toString(), date, month, year);
        Tasks.add(task);
    }

    public void removeTaskForToday(int date, int month, int year) {
        try {
            Task task = Tasks.getTaskForToday(date, month, year);
            Tasks.remove(task);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Error while removing",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void check(int date, int month, int year) {
        taskField.setText("");
        if (!Tasks.isTaskForToday(date, month, year)) {
            addButton.setEnabled(true);
            editButton.setEnabled(false);
            removeButton.setEnabled(false);
        } else {
            taskField.setText(Tasks.getTaskForTodayString(date, month, year));
            addButton.setEnabled(false);
            editButton.setEnabled(true);
            removeButton.setEnabled(true);
        }
    }

}
