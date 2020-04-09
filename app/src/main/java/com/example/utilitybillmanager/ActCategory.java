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

public class ActCategory extends AppCompatActivity {
    static DatabaseHelper db;
    Button paybut, incbut, editpayable, newpayable, editincome, newincome;
    LinearLayout payablecat, incomecat;
    ListView payable, income;
    String clickedButton = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        paybut = (Button) findViewById(R.id.payablecat);
        incbut = (Button) findViewById(R.id.receivablecat);
        //payablecat = (LinearLayout) findViewById(R.id.payablecatview);
        incomecat = (LinearLayout) findViewById(R.id.incomecatview);
        //editpayable = (Button) findViewById(R.id.editpayablecat);
        //newpayable = (Button) findViewById(R.id.newpayablecat);
        editincome = (Button) findViewById(R.id.editincomecat);
        newincome = (Button) findViewById(R.id.newincomecat);
        //payable = findViewById(R.id.payablecatload);
        income = findViewById(R.id.incomecatload);
        db = new DatabaseHelper(this);

        paybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomecat.setVisibility(View.VISIBLE);
                clickedButton = "PAY";

                loadIncome();

                /*editpaya.setOnClickListener(new View.OnClickListener(){
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
                });*/

            }
        });

        incbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                payablecat.setVisibility(View.GONE);
                incomecat.setVisibility(View.VISIBLE);

                clickedButton = "INCOME";
                loadIncome();


            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(ActCategory.this, Overview.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActCategory.this, Overview.class);
        startActivity(intent);
        finish();
    }

   /* public void loadPayable(){
        ArrayList<String> eventList = db.getPayable();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventList);
        payable.setAdapter(adapter1);
    }*/

    public void loadIncome() {
        ArrayList<String> eventList = null;
        if (clickedButton.equals("PAY")) {
            eventList = db.getPayable();
        } else if (clickedButton.equals("INCOME")) {
            eventList = db.getIncome();
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventList);
        income.setAdapter(adapter2);

        editincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (clickedButton.equals("PAY")) {
                    intent = new Intent(ActCategory.this, EditCategory.class);
                } else if (clickedButton.equals("INCOME")) {
                    intent = new Intent(ActCategory.this, EditIncomeCategory.class);
                }
                startActivity(intent);
            }
        });

        newincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (clickedButton.equals("PAY")) {
                    intent = new Intent(ActCategory.this, AddNewCategory.class);
                } else if (clickedButton.equals("INCOME")) {
                    intent = new Intent(ActCategory.this, AddNewIncomeCategory.class);
                }
                startActivity(intent);
            }
        });
    }
}
