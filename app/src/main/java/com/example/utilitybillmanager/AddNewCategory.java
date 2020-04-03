package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class AddNewCategory extends AppCompatActivity {

    static DatabaseHelper db;
    EditText n, b;
    Button save;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);
        db = new DatabaseHelper(this);

        n = (EditText) findViewById(R.id.newCat);
        b = (EditText) findViewById(R.id.newCatBug);
        save = (Button) findViewById(R.id.addnewpayablecat);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = n.getText().toString();
                double budget = Double.valueOf(b.getText().toString());

                if (name.equals("")||budget==0){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean iscategory = db.checkCategory(name);
                    if (iscategory == true){
                        boolean insert = db.insertCategories(name, budget);
                        if (insert==true){
                            Toast.makeText(getApplicationContext(), "Category successfully added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Category is already taken", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(AddNewCategory.this, Category.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNewCategory.this, Category.class);
        startActivity(intent);
        finish();
    }

}
