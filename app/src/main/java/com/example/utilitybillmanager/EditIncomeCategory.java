package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditIncomeCategory extends AppCompatActivity {

    static DatabaseHelper db;
    Spinner s;
    TextView oi,ob;
    EditText ni,nb;
    Button save;

    int getpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_category);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.incomeselcat);
        oi = (TextView) findViewById(R.id.oldcategoryincome);
        ob = (TextView) findViewById(R.id.oldcategorybudget);
        ni = (EditText) findViewById(R.id.newincome);
        nb = (EditText) findViewById(R.id.newbudget);
        save = (Button) findViewById(R.id.saveeditincomecat);
        loadSpinnerData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredIncome = ni.getText().toString();
                String enteredBudget = nb.getText().toString();

                if (enteredIncome.equals("") || enteredBudget.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    double newIncome = Double.parseDouble(enteredIncome);
                    double newBudget = Double.parseDouble(enteredBudget);


                }

            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(EditIncomeCategory.this, Category.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditIncomeCategory.this, Category.class);
        startActivity(intent);
        finish();
    }

    private void loadSpinnerData() {
        // Spinner Drop down elements
        ArrayList<String> lables = db.getAlltheIncomeCats();

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s.setAdapter(dataAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SQLiteCursor item = (SQLiteCursor) parent.getItemAtPosition(position);
                //String value = String.valueOf(item.getString(0));
                //getpos = incomelist.getPosition(position);
                //oi.setText(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}