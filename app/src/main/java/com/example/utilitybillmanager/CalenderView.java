package com.example.utilitybillmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CalenderView extends AppCompatActivity {

    static  DatabaseHelper db;
    CalendarView calendarView;
    ListView feedback;

    int dayOfMonth1;
    int month1;
    int year1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        db = new DatabaseHelper(this);


        calendarView = (CalendarView) findViewById(R.id.calendarView);
        feedback = findViewById(R.id.feedback);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dayOfMonth1 = dayOfMonth;
                month1 = month;
                year1 = year;

                boolean checkEvent = db.checkEvent(dayOfMonth,month,year);
                if (checkEvent==false){
                    Toast.makeText(getApplicationContext(),"No transaction on this date",Toast.LENGTH_SHORT).show();
                }
                else {
                    loadData();
                }
            }

        });

    }

    public void loadData(){
        ArrayList<String> eventList = db.getEvents(dayOfMonth1,month1,year1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventList);
        feedback.setAdapter(adapter);
    }
    public void back(View view) {
        Intent intent = new Intent(CalenderView.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CalenderView.this, Overview.class);
        startActivity(intent);
        finish();
    }


}
