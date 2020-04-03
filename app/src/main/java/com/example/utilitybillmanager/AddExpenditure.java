package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

public class AddExpenditure extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;
    EditText e, n;
    DatePicker picker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.paymentselcat);
        e = (EditText) findViewById(R.id.expenditureamount);
        picker = (DatePicker) findViewById(R.id.calender);
        n = (EditText) findViewById(R.id.expenditurenote);

        submit = (Button) findViewById(R.id.addexpenditureconf);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSpinnerData();
                Category selectedCat = (Category) s.getSelectedItem();
                String entedAmount = e.getText().toString();
                double amount = Double.parseDouble(entedAmount);
                int day = picker.getDayOfMonth();
                int month = picker.getMonth();
                int year = picker.getYear();
                String note = n.getText().toString();
                int catID = selectedCat.getCatID();

                boolean istoday = today(day, month, year);
                //boolean overBudget = db.overBudget(selectedCat,amount);

                if (istoday==true){
                    Toast.makeText(getApplicationContext(),"You cannot set expenditure for today. Go to make a payment tab",Toast.LENGTH_SHORT).show();
                }
                else{
                    //if (overBudget == false){
                        boolean insert = db.insertExpences(amount,day,month,year,catID);
                        if (insert==true){
                            Toast.makeText(getApplicationContext(),"Record entered successfully",Toast.LENGTH_SHORT).show();
                        //}
                    }
                    //else
                       // Toast.makeText(getApplicationContext(), "Entered amount is Over Budgeted to the selected category", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadSpinnerData();
    }

    public void back(View view) {
        Intent intent = new Intent(AddExpenditure.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddExpenditure.this, Overview.class);
        startActivity(intent);
        finish();
    }

    private void loadSpinnerData() {
        // Spinner Drop down elements
        ArrayList<Category> payableCatsList = db.getAllthePayableCats();
        // Creating adapter for spinner
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, payableCatsList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //int catID = payableCatsListIndexes.get(s.getSelectedItemPosition());

        // attaching data adapter to spinner
        s.setAdapter(dataAdapter);
    }

    public boolean today(int day, int month, int year){
        Date date = new Date();
        int day1 = date.getDay();
        int month1 = date.getMonth();
        int year1 = date.getYear();

        if (day1==day && month1==month && year1==year){
            return true;
        }
        else
            return false;
    }
}

