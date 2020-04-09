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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.utilitybillmanager.model.Category;

import java.util.ArrayList;
import java.util.List;

public class EditCategory extends AppCompatActivity {

    static  DatabaseHelper db;
    Spinner s;
    TextView ob;
    EditText nb;
    Button save;

    int catID;
    String catName;

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = nb.getText().toString();
                if (value.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    double newBudget = Double.parseDouble(value);
                    db.updateBudget(catID, newBudget);
                    Toast.makeText(getApplicationContext(),"Record entered successfully",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(EditCategory.this, ActCategory.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditCategory.this, ActCategory.class);
        startActivity(intent);
        finish();
    }



    private void loadSpinnerData() {
        // Spinner Drop down elements
        final ArrayList<Category> payableCatsList = db.getAllthePayableCats();
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
                Category category = payableCatsList.get(position);
                catID = category.getCatID();
                catName = category.getName();
                ob.setText(category.getBudget()+"");

                //SQLiteCursor item = (SQLiteCursor) s.getItemAtPosition(position);
                //String value = String.valueOf(item.getString(0));
                //ob.setText((int) db.retrieveOldBudget(catBudget));
                // Here you can do the action you want to...
                //ob.setText((int) catBudget);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

    }
}
