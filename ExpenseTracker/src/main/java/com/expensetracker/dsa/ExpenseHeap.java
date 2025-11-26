package com.expensetracker.dsa;

import com.expensetracker.models.Expense;
import java.util.PriorityQueue;

public class ExpenseHeap {
    private PriorityQueue<Expense> expenseQueue;

    public ExpenseHeap() {
        expenseQueue = new PriorityQueue<>();
    }

    public void addExpense(Expense expense) {
        expenseQueue.add(expense);
    }

    public Expense getHighestExpense() {
        return expenseQueue.peek();
    }

    public Expense removeHighestExpense() {
        return expenseQueue.poll();
    }

    public boolean isEmpty() {
        return expenseQueue.isEmpty();
    }

    public int size() {
        return expenseQueue.size();
    }
}