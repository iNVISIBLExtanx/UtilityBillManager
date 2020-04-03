package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signUp extends AppCompatActivity {
    static DatabaseHelper db;
    EditText n, e, uN, p, pA;
    Button login;
    TextView alreadylogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        db = new DatabaseHelper(this);

        n = (EditText) findViewById(R.id.signname);
        e = (EditText) findViewById(R.id.signemail);
        uN = (EditText) findViewById(R.id.signusername);
        p = (EditText) findViewById(R.id.signuserpassword);
        pA = (EditText) findViewById(R.id.signuserpasswordagain);

        login = (Button) findViewById(R.id.signlogin);
        alreadylogin = (TextView) findViewById(R.id.signuseralreadylogin);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = n.getText().toString();
                String email = e.getText().toString();
                String userName = uN.getText().toString();
                String password = p.getText().toString();
                String repassword = pA.getText().toString();
                if (name.equals("")||email.equals("")||userName.equals("")||password.equals("")||repassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (password.equals(repassword)){
                        boolean checkmail = db.checkEmail(email);
                        boolean checkUserName = db.checkUserName(userName);
                        if (checkmail == true){
                            if (email.trim().matches(emailPattern)){
                                if (checkUserName == true){

                                    boolean insert = db.insertCusDetails(name, email, userName, password);
                                    if (insert == true){

                                        Toast.makeText(getApplicationContext(), "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Username is already entered!", Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Email is already entered!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "You didn't enter the same password", Toast.LENGTH_SHORT).show();
                }
            }

        });

        alreadylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alreadyloginIntent = new Intent(signUp.this, MainActivity.class);
                startActivity(alreadyloginIntent);
            }
        });
    }

}
