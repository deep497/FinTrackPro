package com.expensetracker.ui;

import com.expensetracker.services.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private AuthService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(JFrame parent, AuthService authService) {
        super(parent, "Login", true);
        this.authService = authService;
        initializeUI();
    }

    private void initializeUI() {
        setSize(300, 200);
        setLocationRelativeTo(getParent());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authService.login(username, password)) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    // Open dashboard
                    new Dashboard(authService).setVisible(true);
                    ((WelcomePage) getParent()).dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}