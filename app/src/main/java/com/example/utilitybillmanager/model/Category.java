package com.example.utilitybillmanager.model;

public class Category {
    private int CategoryID;
    private String Name;
    private double Budget;
    private double Spent;

    public Category() {

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

    public void setSpent(double spent) {
        this.Spent = spent;
    }

    public double getSpent() {
        return this.Spent;
    }

    public String toString() {
        return Name;
    }
}
