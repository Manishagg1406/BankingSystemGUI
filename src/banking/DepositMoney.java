package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepositMoney extends JFrame {
    private JTextField accountIdField, amountField;

    public DepositMoney() {
        setTitle("Deposit Money");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        add(accountIdField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        JButton depositBtn = new JButton("Deposit");
        add(depositBtn);

        depositBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });

        setVisible(true);
    }

    private void depositMoney() {
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

            // Check if account exists
            String checkSql = "SELECT balance FROM accounts WHERE account_id=?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setInt(1, accountId);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_id=?";
                PreparedStatement pst = conn.prepareStatement(updateSql);
                pst.setDouble(1, amount);
                pst.setInt(2, accountId);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Deposit Successful!");
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
