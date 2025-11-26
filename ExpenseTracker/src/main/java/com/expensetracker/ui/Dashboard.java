package com.expensetracker.ui;

import com.expensetracker.models.Expense;
import com.expensetracker.models.User;
import com.expensetracker.services.AuthService;
import com.expensetracker.services.ExpenseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Dashboard extends JFrame {
    private AuthService authService;
    private ExpenseService expenseService;
    private DefaultListModel<Expense> expenseListModel;

    public Dashboard(AuthService authService) {
        this.authService = authService;
        this.expenseService = new ExpenseService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Expense Tracker - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + authService.getCurrentUser().getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            authService.logout();
            dispose();
            new WelcomePage(authService).setVisible(true);
        });

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Expense list
        expenseListModel = new DefaultListModel<>();
        JList<Expense> expenseList = new JList<>(expenseListModel);
        expenseList.setCellRenderer(new ExpenseListRenderer());
        JScrollPane listScrollPane = new JScrollPane(expenseList);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryCombo = new JComboBox<>(new String[] {
                "Food", "Transport", "Entertainment", "Utilities", "Shopping"
        });

        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField(LocalDate.now().toString());

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String description = descField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    String category = (String) categoryCombo.getSelectedItem();
                    LocalDate date = LocalDate.parse(dateField.getText());

                    expenseService.addExpense(authService.getCurrentUser(),
                            description, amount, category, date);

                    refreshExpenseList();
                    descField.setText("");
                    amountField.setText("");

                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Expense added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(descLabel);
        formPanel.add(descField);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryCombo);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(new JLabel()); // Empty cell
        formPanel.add(addButton);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));

        JButton highestExpenseBtn = new JButton("Show Highest Expense");
        highestExpenseBtn.addActionListener(e -> {
            Expense highest = expenseService.getHighestExpense();
            if (highest != null) {
                JOptionPane.showMessageDialog(Dashboard.this,
                        "Highest Expense: " + highest, "Highest Expense",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Dashboard.this,
                        "No expenses recorded yet", "Highest Expense",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton totalSpendingBtn = new JButton("Show Total Spending");
        totalSpendingBtn.addActionListener(e -> {
            double total = expenseService.getTotalSpending(authService.getCurrentUser());
            JOptionPane.showMessageDialog(Dashboard.this,
                    String.format("Total Spending: $%.2f", total), "Total Spending",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        statsPanel.add(highestExpenseBtn);
        statsPanel.add(totalSpendingBtn);

        // Add components to content panel
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(listScrollPane, BorderLayout.CENTER);
        contentPanel.add(statsPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void refreshExpenseList() {
        expenseListModel.clear();
        expenseService.getUserExpenses(authService.getCurrentUser())
                .forEach(expenseListModel::addElement);
    }

    // Custom renderer for expense list
    private static class ExpenseListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Expense) {
                Expense expense = (Expense) value;
                setText(String.format("%s - $%.2f (%s, %s)",
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()));
            }
            return this;
        }
    }
}