package com.expensetracker.dsa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExpenseGraph {
    private Map<String, Set<String>> categoryGraph;

    public ExpenseGraph() {
        categoryGraph = new HashMap<>();
    }

    public void addCategory(String category) {
        categoryGraph.putIfAbsent(category, new HashSet<>());
    }

    public void addRelationship(String category1, String category2) {
        addCategory(category1);
        addCategory(category2);
        categoryGraph.get(category1).add(category2);
        categoryGraph.get(category2).add(category1);
    }

    public Set<String> getRelatedCategories(String category) {
        return categoryGraph.getOrDefault(category, new HashSet<>());
    }

    public boolean areCategoriesRelated(String category1, String category2) {
        return categoryGraph.getOrDefault(category1, new HashSet<>()).contains(category2);
    }
}