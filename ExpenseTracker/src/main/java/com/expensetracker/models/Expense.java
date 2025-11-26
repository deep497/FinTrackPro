package com.expensetracker.models;

import java.time.LocalDate;

public class Expense implements Comparable<Expense> {
    private int id;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;
    private int userId;

    public Expense(int id, String description, double amount, String category, LocalDate date, int userId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.userId = userId;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public int getUserId() { return userId; }

    @Override
    public int compareTo(Expense other) {
        return Double.compare(this.amount, other.amount);
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f (%s)", description, amount, category);
    }
}