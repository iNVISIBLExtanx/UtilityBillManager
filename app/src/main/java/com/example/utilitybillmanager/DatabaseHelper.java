package com.example.utilitybillmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.utilitybillmanager.model.Category;
import com.example.utilitybillmanager.model.IncomeCategories;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "UtilityBillManager.db", null, 5);
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


    public boolean insertCusDetails(String name, String email, String userName, String password) {
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

    public boolean insertCategories(String Name, double Budget) {
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

    public boolean insertIncomeCategories(String Name, double Budget, double Income) {
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

    public boolean insertExpences(double FullAmount, int Day, int Month, int Year, String Note, int CategoryID) {
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

    public boolean makePavement(double FullAmount, int Day, int Month, int Year, String Note, int CategoryID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FullAmount", FullAmount);
        contentValues.put("PaidAmount", FullAmount);
        contentValues.put("Day", Day);
        contentValues.put("Month", Month);
        contentValues.put("Year", Year);
        contentValues.put("Liability", false);
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
        String quary = "Select * From OutgoingCategories where CategoryID='" + catID + "';";
        Cursor cursor = db.rawQuery(quary, null);
        double newSpent = 0;
        //Category category = new Category();
        //double getSpent = category.getSpent();
        //double newSpent = getSpent + amount;
        //contentValues.put("Spent", newSpent);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            double getSpent = cursor.getDouble(3);
            newSpent = getSpent + amount;
            cursor.moveToNext();
        }
        cursor.close();
        contentValues.put("Spent", newSpent);
        db.update("OutgoingCategories", contentValues, "CategoryID" + "=?", new String[]{String.valueOf(catID)});
    }

    public boolean updateExpence(int expenceID, double amount, int day, int month, int year, String Note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        String quary = "Select * From Expences where ExpencesID='" + expenceID + "';";
//        Cursor cursor = db.rawQuery(quary,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//
//            cursor.moveToNext();
//        }
//        cursor.close();

        contentValues.put("PaidAmount", amount);
        contentValues.put("Day", day);
        contentValues.put("Month", month);
        contentValues.put("Year", year);
        contentValues.put("Note", Note);
        long ins = db.update("Expences", contentValues, "ExpencesID" + "=?", new String[]{String.valueOf(expenceID)});
        if (ins == -1)
            return false;
        else
            return true;
    }

    public boolean checkLiability(int expenceID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From Expences where ExpencesID='" + expenceID + "';";
        Cursor cursor = db.rawQuery(quary, null);
        double remaining = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            double fullamount = cursor.getDouble(1);
            double paidamount = cursor.getDouble(2);
            remaining = (fullamount - paidamount);
            cursor.moveToNext();
        }
        cursor.close();
        if (remaining == 0) {
            return false;
        } else
            return true;
    }

    public void notLiable(int expenceID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Liability", false);
        long ins = db.update("Expences", contentValues, "ExpencesID" + "=?", new String[]{String.valueOf(expenceID)});
    }

    public void insertEvent(int Day, int Month, int Year, String Event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Day", Day);
        contentValues.put("Month", Month);
        contentValues.put("Year", Year);
        contentValues.put("Event", Event);
        db.insert("EventCalendar", null, contentValues);
    }

    public boolean checkEvent(int day, int month, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From EventCalendar where Day='" + day + "' and Month='" + month + "' and Year='" + year + "';";
        Cursor cursor = db.rawQuery(quary, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public ArrayList<String> loadOverOrUpcoming(int catID) {
        ArrayList<String> paymenthistory = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * From Expences where Liability=1 and CategoryID='" + catID + "';";
        paymenthistory.clear();
        Cursor cursor = db.rawQuery(quary, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            paymenthistory.add("ExpenceID : " + cursor.getString(0) + " /Full amount Rs: " + cursor.getString(1) + " /Paid amount Rs: " + cursor.getString(2) + " /Date : " + cursor.getString(3) + " - " + cursor.getString(4) + " - " + cursor.getString(5) + " /Note : " + cursor.getString(7));
            cursor.moveToNext();
        }
        cursor.close();
        return paymenthistory;
    }

    public ArrayList<String> getEvents(int day, int month, int year) {
        ArrayList<String> eventList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        eventList.clear();
        String quary = "Select * From EventCalendar where Day='" + day + "' and Month='" + month + "' and Year='" + year + "';";

        Cursor cursor = db.rawQuery(quary, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            eventList.add(cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();

        return eventList;
    }

    public ArrayList<String> getPayable() {
        ArrayList<String> payable = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        payable.clear();
        String quary = "Select * From OutgoingCategories";

        Cursor cursor = db.rawQuery(quary, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            payable.add("Name: " + cursor.getString(1) + " /Budget: " + cursor.getString(2) + " /Spent: " + cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();

        return payable;
    }

    public ArrayList<String> getIncome() {
        ArrayList<String> income = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        income.clear();
        String quary = "Select * From IncomingCategories";

        Cursor cursor = db.rawQuery(quary, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            income.add("Name: " + cursor.getString(1) + " /Budget: " + cursor.getString(2) + " /Income: " + cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();

        return income;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where email=?", new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean checkUserName(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where userName=?", new String[]{userName});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean checkCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM OutgoingCategories where Name=?", new String[]{category});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean checkIncomeCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM IncomingCategories where Name=?", new String[]{category});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean checkCorrectUser(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM UserDetails where userName=? and password=?", new String[]{userName, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setCatID(cursor.getInt(0));
        category.setName(cursor.getString(1));
        category.setBudget(cursor.getDouble(2));
        return category;
    }
    private IncomeCategories cursorToCategory2(Cursor cursor) {
        IncomeCategories incomeCategories = new IncomeCategories();
        incomeCategories.setCatID(cursor.getInt(0));
        incomeCategories.setName(cursor.getString(1));
        incomeCategories.setBudget(cursor.getDouble(2));
        incomeCategories.setIncome(cursor.getDouble(3));
        return incomeCategories;
    }

    public ArrayList<Category> getAllthePayableCats() {
        ArrayList<Category> payableCatsList = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        payableCatsList.clear();
        Cursor cursor = db.rawQuery("Select * from OutgoingCategories", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            payableCatsList.add(category);
            cursor.moveToNext();
        }
        cursor.close();

        return payableCatsList;
    }
    public ArrayList<IncomeCategories> getAlltheIncomeCats() {
        ArrayList<IncomeCategories> incomelist = new ArrayList<IncomeCategories>();
        SQLiteDatabase db = this.getReadableDatabase();
        incomelist.clear();
        Cursor cursor = db.rawQuery("Select * from IncomingCategories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            IncomeCategories incomeCategories = cursorToCategory2(cursor);
            incomelist.add(incomeCategories);
            cursor.moveToNext();
        }
        cursor.close();

        return incomelist;
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
        Cursor cursor = db.rawQuery(quary, null);
        double left = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory1(cursor);
            double buget = category.getBudget();
            double spent = category.getSpent();
            left = (buget - spent);
            cursor.moveToNext();
        }
        cursor.close();
        if (left >= amount) {
            return true;
        } else
            return false;
    }

    /*public boolean notExceedingBudget() {
        double incomeBudget = 0;
        double outgoingBudget = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "SELECT i.Budget, o.Budget" + " FROM IncomingCategories i, OutgoingCategories o";
        Cursor cursor = db.rawQuery(quary,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            incomeBudget = cursor.getDouble(3) + cursor.getDouble(3);
            cursor.moveToNext();
        }
        cursor.close();

        String quary2 = "SELECT Budget" + " FROM OutgoingCategories";
        Cursor cursor2 = db.rawQuery(quary2,null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()){
            outgoingBudget = cursor2.getDouble(3);
            cursor2.moveToNext();
        }
        cursor2.close();
        if (incomeBudget >= outgoingBudget){
            return true;
        }
        else
            return false;
    }*/



    /*public double retrieveOldBudget(int catID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from OutgoingCategories where CategoryID=?", new String[]{String.valueOf(catID)});
        double budgetValue = 0;
        if (cursor.moveToFirst()) {
            do {
                budgetValue = cursor.getDouble(2);
            }   while (cursor.moveToNext());
        }
        cursor.close();
        return budgetValue;
    }*/
    /*public ArrayList<Double> retrieveOldBudget(int catID){
        ArrayList<Double> budget = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "Select * from OutgoingCategories where CategoryID=" + catID + "';";
        budget.clear();
        Cursor cursor = db.rawQuery(quary, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            budget.add(cursor.getDouble(2));
            cursor.moveToNext();
        }
        cursor.close();
        return budget;
    }*/
    public void updateBudget(int catID, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        String quary = "Select * From Expences where ExpencesID='" + expenceID + "';";
//        Cursor cursor = db.rawQuery(quary,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//
//            cursor.moveToNext();
//        }
//        cursor.close();

        contentValues.put("Budget", amount);

        db.update("OutgoingCategories", contentValues, "CategoryID" + "=?", new String[]{String.valueOf(catID)});


    }

    public void updateIncome(int catID, double income, double budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Budget", budget);
        contentValues.put("Income", income);


        db.update("IncomingCategories", contentValues, "CategoryID" + "=?", new String[]{String.valueOf(catID)});


    }
}
