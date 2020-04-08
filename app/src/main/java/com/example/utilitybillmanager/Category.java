package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    static  DatabaseHelper db;
    Button paybut, incbut, editpayable, newpayable, editincome, newincome;
    LinearLayout payablecat, incomecat;
    ListView payable,income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        paybut = (Button) findViewById(R.id.payablecat);
        incbut = (Button) findViewById(R.id.receivablecat);
        payablecat = (LinearLayout) findViewById(R.id.payablecatview);
        incomecat = (LinearLayout) findViewById(R.id.incomecatview);
        editpayable = (Button) findViewById(R.id.editpayablecat);
        newpayable = (Button) findViewById(R.id.newpayablecat);
        editincome = (Button) findViewById(R.id.editincomecat);
        newincome = (Button) findViewById(R.id.newincomecat);
        payable = findViewById(R.id.payablecatload);
        income = findViewById(R.id.incomecatload);

        paybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payablecat.setVisibility(View.VISIBLE);
                incomecat.setVisibility(View.GONE);
                loadPayable();

                editpayable.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent editpayablecat = new Intent(Category.this, EditCategory.class);
                        startActivity(editpayablecat);
                    }
                });
                newpayable.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent newpayablecat = new Intent(Category.this, AddNewCategory.class);
                        startActivity(newpayablecat);
                    }
                });

            }
        });

        incbut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                payablecat.setVisibility(View.GONE);
                incomecat.setVisibility(View.VISIBLE);
                loadIncome();

                editincome.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent editincomecat = new Intent(Category.this, EditIncomeCategory.class);
                        startActivity(editincomecat);
                    }
                });

                newincome.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent newincomecat = new Intent(Category.this, AddNewIncomeCategory.class);
                        startActivity(newincomecat);
                    }
                });
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(Category.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Category.this, Overview.class);
        startActivity(intent);
        finish();
    }

    public void loadPayable(){
        ArrayList<String> eventList = db.getPayable();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventList);
        payable.setAdapter(adapter1);
    }

    public void loadIncome(){
        ArrayList<String> eventList = db.getIncome();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventList);
        income.setAdapter(adapter2);
    }

    private int CategoryID;
    private String Name;
    private double Budget;
    private double Spent;

    public Category(){
        this.CategoryID = 0;
        this.Name = "";
        this.Budget=0;
        this.Spent = 0;

    }
    public void setCatID(int ID){
        this.CategoryID = ID;
    }
    public int getCatID(){
        return this.CategoryID;
    }
    public void setName(String name){
        this.Name = name;
    }
    public String getName(){
        return this.Name;
    }
    public void setBudget(double budget){
        this.Budget = budget;
    }
    public double getBudget(){
        return this.Budget;
    }
    public void setSpent(double spent){
        this.Spent = spent;
    }
    public double getSpent(){
        return this.Spent;
    }

    public String toString(){
        return Name;
    }




}
