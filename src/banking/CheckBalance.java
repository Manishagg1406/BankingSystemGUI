package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBalance extends JFrame {
    private JTextField accountIdField;

    public CheckBalance() {
        setTitle("Check Balance");
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));

        add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        add(accountIdField);

        JButton checkBtn = new JButton("Check");
        add(checkBtn);

        checkBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        setVisible(true);
    }

    private void checkBalance() {
        String accountIdText = accountIdField.getText();

        if (accountIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Account ID");
            return;
        }

        try {
            int accountId = Integer.parseInt(accountIdText);

            Connection conn = DBConnection.getConnection();
            String sql = "SELECT balance FROM accounts WHERE account_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, accountId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                JOptionPane.showMessageDialog(this, "Current Balance: ₹" + balance);
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
