package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditIncomeCategory extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_category);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.incomeselcat);
        loadSpinnerData();

    }

    public void back(View view) {
        Intent intent = new Intent(EditIncomeCategory.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditIncomeCategory.this, Overview.class);
        startActivity(intent);
        finish();
    }
    private void loadSpinnerData() {
        // Spinner Drop down elements
        ArrayList<String> lables = db.getAlltheIncomeCats();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s.setAdapter(dataAdapter);
    }
}
