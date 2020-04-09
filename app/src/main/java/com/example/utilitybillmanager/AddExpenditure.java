package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;
import com.example.utilitybillmanager.model.Category;

public class AddExpenditure extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;
    EditText e, n;
    DatePicker picker;
    Button submit;

    int catID;
    String catName;
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
        loadSpinnerData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String entedAmount = e.getText().toString();
                int day = picker.getDayOfMonth();
                int month = picker.getMonth();
                int year = picker.getYear();
                String note = n.getText().toString();
                boolean istoday = today(day, month, year);

                if (entedAmount.equals("")||note.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    double amount = Double.parseDouble(entedAmount);
                    boolean overBudget = db.overBudget(catID, amount);

                    if (istoday==true){
                        Toast.makeText(getApplicationContext(),"You cannot set expenditure for today. Go to make a payment tab",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (overBudget == true) {
                            boolean insert = db.insertExpences(amount,day,month,year,note,catID);
                            db.updateRecord(catID,amount);
                            if (insert==true){
                                String eventDetails = "You have to pay " + catName + " " + entedAmount + " Rupees on " + day + ". " + "Further you said " + note + " on that payment!";
                                db.insertEvent(day,month,year,eventDetails);
                                Toast.makeText(getApplicationContext(),"Record entered successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Entered amount is Over Budgeted to the selected category", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

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
        final ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, payableCatsList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s.setAdapter(dataAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Category category = dataAdapter.getItem(position);
                catID = category.getCatID();
                catName = category.getName();

                // Here you can do the action you want to...
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }
    public boolean today(int day, int month, int year){

        Calendar cal = Calendar.getInstance();
        int day1= cal.get(Calendar.DAY_OF_MONTH);
        int month1= cal.get(Calendar.MONTH);
        int year1 = cal.get(Calendar.YEAR);

        if (day1==day && month1==month && year1==year){
            return true;
        }
        else
            return false;
    }
}

