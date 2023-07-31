import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ATMTask1 extends JFrame {
    private String currentUserId;
    private List<String> transactionHistory;
    private double currentBalance;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea transactionArea;

    public ATMTask1() {
        setTitle("ATM Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentUserId = null;
        currentBalance = 0.0;
        transactionHistory = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        JPanel loginPanel = new JPanel(new GridLayout(8, 5, 10, 10));
        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField(10);
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String pin = new String(pinField.getPassword());

                if (authenticateUser(userId, pin)) {
                    currentUserId = userId;
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid User ID or PIN. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                userIdField.setText("");
                pinField.setText("");
            }
        });

        loginPanel.add(userIdLabel);
        loginPanel.add(userIdField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        getContentPane().add(loginPanel);
    }

    private boolean authenticateUser(String userId, String pin) {
        
        return userId.equals("varad") && pin.equals("varad123");
    }

    private void showMainMenu() {
        getContentPane().removeAll();

        JPanel mainMenuPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton addMoneyButton = new JButton("1. Add Money");
        addMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddMoneyDialog();
            }
        });

        JButton transactionsButton = new JButton("2. Transactions History");
        transactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionsHistory();
            }
        });

        JButton withdrawButton = new JButton("3. Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWithdrawDialog();
            }
        });

        JButton transferButton = new JButton("4. Transfer");
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransferDialog();
            }
        });

        JButton quitButton = new JButton("5. Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        mainMenuPanel.add(addMoneyButton);
        mainMenuPanel.add(transactionsButton);
        mainMenuPanel.add(withdrawButton);
        mainMenuPanel.add(transferButton);
        mainMenuPanel.add(quitButton);

        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }

    private void showAddMoneyDialog() {
        String input = JOptionPane.showInputDialog(null, "Enter the amount to add:", "Add Money", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    currentBalance += amount;
                    addTransactionToHistory("Added Money: $" + amount);
                    JOptionPane.showMessageDialog(null, "Money added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showWithdrawDialog() {
        String input = JOptionPane.showInputDialog(null, "Enter the amount to withdraw:", "Withdraw", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    if (currentBalance >= amount) {
                        currentBalance -= amount;
                        addTransactionToHistory("Withdraw: $" + amount);
                        JOptionPane.showMessageDialog(null, "Withdrawal successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showTransferDialog() {
        String input = JOptionPane.showInputDialog(null, "Enter the amount to transfer:", "Transfer", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    if (currentBalance >= amount) {
                        currentBalance -= amount;
                        addTransactionToHistory("Transfer: $" + amount);
                        JOptionPane.showMessageDialog(null, "Transfer successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showTransactionsHistory() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());

        JTextArea transactionsArea = new JTextArea(10, 20);
        transactionsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(transactionsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        transactionsArea.append("Transactions History for User: " + currentUserId + "\n");
        for (String transaction : transactionHistory) {
            transactionsArea.append(transaction + "\n");
        }

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        getContentPane().add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void logout() {
        getContentPane().removeAll();
        currentUserId = null;
        currentBalance = 0.0;
        transactionHistory.clear();
        initComponents();
    }

    private void addTransactionToHistory(String transaction) {
        transactionHistory.add(transaction);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATMTask1().setVisible(true);
            }
        });
    }
}
