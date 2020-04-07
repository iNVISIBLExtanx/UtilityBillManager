package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MakePayment extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;

    int catID;
    Button y,n,payment;
    EditText amo, no, e;
    ListView loading;
    LinearLayout history;

    String eID;
    int expenceID;
    boolean isOverDue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.paymentselcat);
        y = (Button) findViewById(R.id.yes);
        n = (Button) findViewById(R.id.no);
        payment = (Button) findViewById(R.id.makepayconf);
        amo = (EditText) findViewById(R.id.paymentamount);
        no = (EditText) findViewById(R.id.paymentnote);
        loading = (ListView) findViewById(R.id.loadHistory);
        history = (LinearLayout) findViewById(R.id.overdueOupcoming);
        e = (EditText) findViewById(R.id.expenceID);

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
                //eID = e.getText().toString();
                //expenceID = Integer.parseInt(eID);
                /*if (eID != null && !eID.isEmpty() && !eID.equals("null")){
                    isOverDue = true;
                }*/
                /*if (TextUtils.isEmpty(eID)){
                    isOverDue = false;
                }
                else
                    expenceID = Integer.parseInt(eID);*/
                //expenceID = Integer.parseInt(eID);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amou = amo.getText().toString();
                Calendar cal = Calendar.getInstance();
                final int day= cal.get(Calendar.DAY_OF_MONTH);
                final int month= cal.get(Calendar.MONTH);
                final int year = cal.get(Calendar.YEAR);
                String note = no.getText().toString();

                if (amou.equals("")||note.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    double amount = Double.parseDouble(amou);
                    boolean overBudget = db.overBudget(catID, amount);

                    if (overBudget==true){

                        eID = e.getText().toString();
                        if (TextUtils.isEmpty(eID)){
                            isOverDue = false;
                        }
                        else
                            expenceID = Integer.parseInt(eID);

                        if (isOverDue == true){
                            boolean insert = db.updateExpence(expenceID,amount,day,month,year,note);
                            if (insert == true){
                                db.updateRecord(catID,amount);
                                boolean liability = db.checkLiability(expenceID);
                                if (liability==false){
                                    db.notLiable(expenceID);
                                }
                                Toast.makeText(getApplicationContext(),"Overdue/Upcoming is payed!",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getApplicationContext(),"Something is not working correct.",Toast.LENGTH_SHORT).show();

                        }if (isOverDue == false) {
                            boolean insert = db.makePavement(amount,day,month,year,note,catID);
                            if (insert==true){
                                db.updateRecord(catID,amount);
                                Toast.makeText(getApplicationContext(),"Record entered successfully",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getApplicationContext(),"Something is not working correct.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Entered amount is Over Budgeted to the selected category", Toast.LENGTH_SHORT).show();
                    }
                }

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

                // Here you can do the action you want to...
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }

}
