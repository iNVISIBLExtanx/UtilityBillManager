package com.example.utilitybillmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.text.style.UpdateAppearance;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context,  "UtilityBillManager.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table UserDetails(Name text,Email text primary key,userName text,password text)");
        db.execSQL("create table OutgoingCategories(CategoryID  integer primary key autoincrement,Name text,Budget double, Spent double)");
        db.execSQL("create table IncomingCategories(CategoryID  integer primary key autoincrement,Name text,Budget double, Income double)");
        db.execSQL("create table Expences(ExpencesID integer primary key autoincrement,FullAmount double, PaidAmount double, Day int, Month int, Year int, Liability boolean,Note text,CategoryID int,foreign key (CategoryID) references OutgoingCategories(CategoryID))");
        db.execSQL("create table EventCalendar(EventID integer primary key autoincrement, Day int, Month int, Year int, Event text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists UserDetails");
        db.execSQL("drop table if exists OutgoingCategories");
        db.execSQL("drop table if exists IncomingCategories");
        db.execSQL("drop table if exists Expences");
        db.execSQL("drop table if exists EventCalendar");
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
    public  boolean insertIncomeCategories(String Name, double Budget, double Income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name);
        contentValues.put("Budget", Budget);
        contentValues.put("Income", Income);
        long ins = db.insert("IncomingCategories", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }

    public  boolean insertExpences(double FullAmount,int Day,int Month,int Year,String Note,int CategoryID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FullAmount", FullAmount);
        contentValues.put("PaidAmount", 0);
        contentValues.put("Day", Day);
        contentValues.put("Month", Month);
        contentValues.put("Year", Year);
        contentValues.put("Liability", true);
        contentValues.put("Note", Note);
        contentValues.put("CategoryID", CategoryID);
        long ins = db.insert("Expences", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }

    public void updateRecord(int catID, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Category category = new Category();
        double getSpent = category.getSpent();
        double newSpent = getSpent + amount;
        contentValues.put("Spent", newSpent);
        db.update("OutgoingCategories", contentValues, "CategoryID"+"=?", new String[] {String.valueOf(catID)} );

    }

    public  void insertEvent(int Day,int Month,int Year, String Event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Day", Day);
        contentValues.put("Month", Month);
        contentValues.put("Year", Year);
        contentValues.put("Event", Event);
        db.insert("EventCalendar", null, contentValues);
    }

    public boolean checkEvent(int day, int month, int year){
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From EventCalendar where Day='" + day + "' and Month='" + month +  "' and Year='" + year + "';";
        Cursor cursor = db.rawQuery(quary, null);
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public ArrayList<String> loadOverOrUpcoming(int catID){
        ArrayList<String> paymenthistory = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From Expences where Liability=1 and CategoryID='" + catID + "';";
        paymenthistory.clear();
        Cursor cursor = db.rawQuery(quary, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            paymenthistory.add("ExpenceID : "+cursor.getString(0) +" /Full amount Rs: "+cursor.getString(1) + " /Paid amount Rs: "+cursor.getString(2) + " /Date : "+cursor.getString(3) + " - " + cursor.getString(4)+ " - " + cursor.getString(5) + " /Note : "+cursor.getString(7));
            cursor.moveToNext();
        }
        cursor.close();
        return paymenthistory;
    }

    public ArrayList<String> getEvents(int day, int month, int year) {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        eventList.clear();
        String quary = "Select * From EventCalendar where Day='" + day + "' and Month='" + month +  "' and Year='" + year + "';";

        Cursor cursor = db.rawQuery(quary, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            eventList.add(cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();

        return eventList;
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
    public boolean checkIncomeCategory(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM IncomingCategories where Name=?", new String[]{category});
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
        payableCatsList.clear();
        Cursor cursor = db.rawQuery("Select * from OutgoingCategories", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Category category = cursorToCategory(cursor);
            payableCatsList.add(category);
            cursor.moveToNext();
        }
            cursor.close();

        return payableCatsList;
    }

    private Category cursorToCategory1(Cursor cursor) {
        Category category = new Category();
        category.setBudget(cursor.getDouble(2));
        category.setSpent(cursor.getDouble(3));
        return category;
    }

    public boolean overBudget(int Category, double amount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From OutgoingCategories where CategoryID='" + Category + "';";
        Cursor cursor = db.rawQuery(quary,null);
        double left = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Category category = cursorToCategory1(cursor);
            double buget = category.getBudget();
            double spent = category.getSpent();
            left = (buget-spent);
            cursor.moveToNext();
        }
        cursor.close();
        if (left >= amount){
            return true;
        }
        else
            return false;
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





}
