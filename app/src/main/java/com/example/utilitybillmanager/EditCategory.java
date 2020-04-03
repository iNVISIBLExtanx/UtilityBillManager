package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditCategory extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;
    TextView ob;
    EditText nb;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        db = new DatabaseHelper(this);

        s = (Spinner) findViewById(R.id.paymentselcat);
        ob = (TextView) findViewById(R.id.oldpayablebudget);
        nb = (EditText) findViewById(R.id.newpayablebudget);
        save = (Button) findViewById(R.id.saveeditpayablecat);
        loadSpinnerData();
    }

    public void back(View view) {
        Intent intent = new Intent(EditCategory.this, Category.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditCategory.this, Category.class);
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
}
