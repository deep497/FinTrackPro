package com.expensetracker.services;

import com.expensetracker.dsa.ExpenseGraph;
import com.expensetracker.dsa.ExpenseHeap;
import com.expensetracker.models.Expense;
import com.expensetracker.models.User;
import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
    private Map<Integer, List<Expense>> userExpenses;
    private ExpenseHeap expenseHeap;
    private ExpenseGraph expenseGraph;
    private int nextExpenseId = 1;

    public ExpenseService() {
        userExpenses = new HashMap<>();
        expenseHeap = new ExpenseHeap();
        expenseGraph = new ExpenseGraph();
        initializeCategories();
    }

    private void initializeCategories() {
        expenseGraph.addCategory("Food");
        expenseGraph.addCategory("Transport");
        expenseGraph.addCategory("Entertainment");
        expenseGraph.addCategory("Utilities");
        expenseGraph.addCategory("Shopping");
        expenseGraph.addRelationship("Food", "Entertainment");
        expenseGraph.addRelationship("Shopping", "Entertainment");
    }

    public void addExpense(User user, String description, double amount, String category, LocalDate date) {
        Expense expense = new Expense(nextExpenseId++, description, amount, category, date, user.getId());
        userExpenses.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(expense);
        expenseHeap.addExpense(expense);
    }

    public List<Expense> getUserExpenses(User user) {
        return userExpenses.getOrDefault(user.getId(), new ArrayList<>());
    }

    public Expense getHighestExpense() {
        return expenseHeap.getHighestExpense();
    }

    public List<String> getRelatedCategories(String category) {
        return new ArrayList<>(expenseGraph.getRelatedCategories(category));
    }

    public double getTotalSpending(User user) {
        return getUserExpenses(user).stream().mapToDouble(Expense::getAmount).sum();
    }
}