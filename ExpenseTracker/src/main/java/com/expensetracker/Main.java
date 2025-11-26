package com.expensetracker;

import com.expensetracker.services.AuthService;
import com.expensetracker.ui.WelcomePage;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage(authService);
            welcomePage.setVisible(true);
        });
    }
}