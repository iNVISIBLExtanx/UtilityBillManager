package com.example.utilitybillmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context,  "UtilityBillManager.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table UserDetails(Name text,Email text primary key,userName text,password text)");
        db.execSQL("create table OutgoingCategories(CategoryID  integer primary key autoincrement,Name text,Budget double, Spent double)");
        db.execSQL("create table IncomingCategories(CategoryID  integer primary key autoincrement,Name text,Budget double, Income double)");
        db.execSQL("create table Expences(ExpencesID integer primary key autoincrement,FullAmount double, PaidAmount double, Day int, Month int, Year int, Liability boolean,CategoryID int,foreign key (CategoryID) references OutgoingCategories(CategoryID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists UserDetails");
        db.execSQL("drop table if exists  OutgoingCategories");
        db.execSQL("drop table if exists  IncomingCategories");
        db.execSQL("drop table if exists Expences");
    }


    public  boolean insertCusDetails(String name, String email, String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Email", email);
        contentValues.put("Username", userName);
        contentValues.put("Password", password);
        long ins = db.insert("UserDetails", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }

    public  boolean insertCategories(String Name, double Budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name);
        contentValues.put("Budget", Budget);
        contentValues.put("Spent", 0);
        long ins = db.insert("OutgoingCategories", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }


    public  boolean insertExpences(double FullAmount,int Day,int Month,int Year,int CategoryID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FullAmount", FullAmount);
        contentValues.put("PaidAmount", 0);
        contentValues.put("Day", Day);
        contentValues.put("Month", Month);
        contentValues.put("Year", Year);
        contentValues.put("Liability", true);
        contentValues.put("CategoryID", CategoryID);
        long ins = db.insert("Expences", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }


    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where email=?", new String[]{email});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    public boolean checkUserName(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where userName=?", new String[]{userName});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }
    public boolean checkCategory(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM OutgoingCategories where Name=?", new String[]{category});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    public boolean checkCorrectUser(String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where userName=? and password=?", new String[]{userName, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setCatID(cursor.getInt(0));
        category.setName(cursor.getString(1));
        return category;
    }

    public ArrayList<Category> getAllthePayableCats() {
        ArrayList<Category> payableCatsList = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from OutgoingCategories", null);
        payableCatsList.clear();
        if (cursor.moveToFirst()) {
            do {
                Category category = cursorToCategory(cursor);
                payableCatsList.add(category);
            } while (cursor.moveToNext());
        }
            cursor.close();

        return payableCatsList;
    }


    public ArrayList<String> getAlltheIncomeCats() {
        ArrayList<String> incomelist = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from IncomingCategories", null);
        incomelist.clear();
        if (cursor.moveToFirst()) {
            do {
                incomelist.add(cursor.getString(1));
            }   while (cursor.moveToNext());
        }
        cursor.close();

        return incomelist;
    }


    public boolean overBudget(String selectedCat, double amount){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM OutgoingCategories where Name=?", new String[]{selectedCat});
            Double budget = cursor.getDouble(cursor.getColumnIndex("Budget"));
            Double spent = cursor.getDouble(cursor.getColumnIndex("Spent"));
            Double remaining = (budget-spent);

            if (remaining < amount){
                return true;
        }
            else
                return false;
    }






}