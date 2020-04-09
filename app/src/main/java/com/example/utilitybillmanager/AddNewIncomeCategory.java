package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewIncomeCategory extends AppCompatActivity {

    static DatabaseHelper db;
    EditText n,i,b;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_income_category);
        db = new DatabaseHelper(this);

        n = findViewById(R.id.newincomecategory);
        i = findViewById(R.id.newcatincome);
        b = findViewById(R.id.newCatBug);
        save = findViewById(R.id.addnewincomecat);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = n.getText().toString();
                String income = i.getText().toString();
                String budget = b.getText().toString();

                if (name.equals("") || income.equals("")|| budget.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    double income1 = Double.valueOf(income);
                    double budget1 = Double.valueOf(budget);
                    boolean checkIncomeCategory = db.checkIncomeCategory(name);
                    if (checkIncomeCategory==true){
                        boolean insert = db.insertIncomeCategories(name, budget1,income1);
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
        Intent intent = new Intent(AddNewIncomeCategory.this, ActCategory.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNewIncomeCategory.this, ActCategory.class);
        startActivity(intent);
        finish();
    }
}
