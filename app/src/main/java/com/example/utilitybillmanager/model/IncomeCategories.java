package com.example.utilitybillmanager.model;

public class IncomeCategories {

    private int CategoryID;
    private String Name;
    private double Budget;
    private double Income;

    public IncomeCategories() {

    }

    public void setCatID(int ID) {
        this.CategoryID = ID;
    }

    public int getCatID() {
        return this.CategoryID;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void setBudget(double budget) {
        this.Budget = budget;
    }

    public double getBudget() {
        return this.Budget;
    }

    public void setIncome(double spent) {
        this.Income = spent;
    }

    public double getIncome() {
        return this.Income;
    }

    public String toString() {
        return Name;
    }}
