package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    EditText u,p;
    Button lb;
    TextView rnb;
    static DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        u = (EditText) findViewById(R.id.uname);
        p = (EditText) findViewById(R.id.pass);
        lb = (Button) findViewById(R.id.login);
        rnb = (TextView) findViewById(R.id.register);
        lb.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userName = u.getText().toString();
                String password = p.getText().toString();
                boolean check = db.checkCorrectUser(userName, password);

                if (userName.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                    if (check == true){
                        Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                         Intent overviewIntent = new Intent(MainActivity.this, Overview.class);
                         startActivity(overviewIntent);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
                    }
        });

        rnb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){                                             /*signUp*/
                Intent registerIntent = new Intent(MainActivity.this, signUp.class);
                startActivity(registerIntent);
            }
        });
    }


}
