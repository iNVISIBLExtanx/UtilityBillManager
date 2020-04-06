package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MakePayment extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;

    int catID;
    String catName;
    Button y,n;
    EditText amount, note, expenceID;
    ListView loading;
    LinearLayout history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.paymentselcat);
        y = (Button) findViewById(R.id.yes);
        n = (Button) findViewById(R.id.no);
        amount = (EditText) findViewById(R.id.paymentamount);
        note = (EditText) findViewById(R.id.paymentnote);
        loading = (ListView) findViewById(R.id.loadHistory);
        history = (LinearLayout) findViewById(R.id.overdueOupcoming);
        expenceID = (EditText) findViewById(R.id.expenceID);

        loadSpinnerData();
        history.setVisibility(View.GONE);

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setVisibility(View.GONE);
            }
        });

        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                history.setVisibility(View.VISIBLE);
                loadData();

            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(MakePayment.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MakePayment.this, Overview.class);
        startActivity(intent);
        finish();
    }

    public void loadData(){
        ArrayList<String> paymenthistory = db.loadOverOrUpcoming(catID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paymenthistory);
        loading.setAdapter(adapter);
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

}
