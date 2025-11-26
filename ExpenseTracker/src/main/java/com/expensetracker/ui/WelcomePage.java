package com.expensetracker.ui;

import com.expensetracker.services.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {
    private AuthService authService;

    public WelcomePage(AuthService authService) {
        this.authService = authService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Expense Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel with login button
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginDialog(WelcomePage.this, authService).setVisible(true);
            }
        });
        headerPanel.add(loginButton);

        // Welcome content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeLabel = new JLabel("Welcome to Expense Tracker");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center; width: 300px;'>"
                + "Track and Manage your expenses efficiently.</div></html>");

        JButton demoButton = new JButton("Take a Quick Tour");
        demoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(WelcomePage.this,
                    "This application helps you track expenses:\n"
                            + "- Money Management made easy\n"
                            + "- Tracking most expensive utility",
                    "Quick Tour", JOptionPane.INFORMATION_MESSAGE);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        contentPanel.add(descriptionLabel, gbc);

        gbc.gridy = 2;
        contentPanel.add(demoButton, gbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}