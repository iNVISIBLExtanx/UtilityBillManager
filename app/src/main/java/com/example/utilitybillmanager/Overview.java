package com.example.utilitybillmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class Overview extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setupNavigation(savedInstanceState, toolbar);

    }

    private void setupNavigation(Bundle savedInstanceState, Toolbar toolbar) {
        // Navigation menu items
        List<IDrawerItem> iDrawerItems = new ArrayList<>();
        iDrawerItems.add(new PrimaryDrawerItem().withName("Overview").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Add Expenditure").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Make Payment").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Payment Status").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Trasaction History").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Calender View").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Category").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Charts").withIcon(R.drawable.ic_info).withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf")));

        // sticky DrawItems ; footer menu items

        List<IDrawerItem> stockyItems = new ArrayList<>();

        PrimaryDrawerItem primaryDrawerItem = new PrimaryDrawerItem()
                .withName("Settings")
                .withTypeface(Typeface.createFromAsset(getAssets(),"bold.ttf"))
                .withIcon(R.drawable.ic_info)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(Overview.this, Overview.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                });

        stockyItems.add(primaryDrawerItem);

        // navigation menu header
        AccountHeader header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withEmail("invisible.icompany@gmail.com")
                        .withName("iNVISIBLExtanx")
                        .withIcon(R.drawable.ic_info))
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.headerback)
                .withSelectionListEnabledForSingleProfile(false) // we need just one profile
                .build();

        // Navigation drawer
        new DrawerBuilder()
                .withActivity(this) // activity main
                .withToolbar(toolbar) // toolbar
                .withSavedInstance(savedInstanceState) // saveInstance of activity
                .withDrawerItems(iDrawerItems) // menu items
                .withTranslucentNavigationBar(true)
                .withStickyDrawerItems(stockyItems) // footer items
                .withAccountHeader(header) // header of navigation
                .withOnDrawerItemClickListener(this) // listener for menu items click
                .build();


    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if(position==1){
            Intent intent = new Intent(Overview.this, Overview.class);
            startActivity(intent);
            Toast.makeText(this, "Overview", Toast.LENGTH_SHORT).show();
        }else if(position==2){
            Intent intent = new Intent(Overview.this, AddExpenditure.class);
            startActivity(intent);
            Toast.makeText(this, "AddExpenditure", Toast.LENGTH_SHORT).show();
        }else if(position==3){;
            Intent intent = new Intent(Overview.this, MakePayment.class);
            startActivity(intent);
            Toast.makeText(this, "MakePayment", Toast.LENGTH_SHORT).show();
        }else if(position==4){
            Intent intent = new Intent(Overview.this, PaymentStatus.class);
            startActivity(intent);
            Toast.makeText(this, "PaymentStatus", Toast.LENGTH_SHORT).show();
        }else if(position==5){
            Intent intent = new Intent(Overview.this, TrasactionHistory.class);
            startActivity(intent);
            Toast.makeText(this, "TrasactionHistory", Toast.LENGTH_SHORT).show();
        }else if(position==6){
            Intent intent = new Intent(Overview.this, CalenderView.class);
            startActivity(intent);
            Toast.makeText(this, "CalenderView", Toast.LENGTH_SHORT).show();
        }else if(position==7){
            Intent intent = new Intent(Overview.this, Category.class);
            startActivity(intent);
            Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
        }else if(position==8){
            Intent intent = new Intent(Overview.this, Charts.class);
            startActivity(intent);
            Toast.makeText(this, "Charts", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
