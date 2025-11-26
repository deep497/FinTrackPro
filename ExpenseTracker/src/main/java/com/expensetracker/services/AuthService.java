package com.expensetracker.services;

import com.expensetracker.models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users;
    private User currentUser;

    public AuthService() {
        users = new HashMap<>();
        users.put("admin", new User(1, "admin", "admin123"));
        users.put("user", new User(2, "user", "user123"));
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}