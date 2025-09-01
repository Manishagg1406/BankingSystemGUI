package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithdrawMoney extends JFrame {
    private JTextField accountIdField, amountField;

    public WithdrawMoney() {
        setTitle("Withdraw Money");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        add(accountIdField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        JButton withdrawBtn = new JButton("Withdraw");
        add(withdrawBtn);

        withdrawBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });

        setVisible(true);
    }

    private void withdrawMoney() {
        String accountIdText = accountIdField.getText();
        String amountText = amountField.getText();

        if (accountIdText.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try {
            int accountId = Integer.parseInt(accountIdText);
            double amount = Double.parseDouble(amountText);

            Connection conn = DBConnection.getConnection();

            // Check balance
            String checkSql = "SELECT balance FROM accounts WHERE account_id=?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setInt(1, accountId);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_id=?";
                    PreparedStatement pst = conn.prepareStatement(updateSql);
                    pst.setDouble(1, amount);
                    pst.setInt(2, accountId);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account not found!");
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
