package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccount extends JFrame {
    public CreateAccount() {
        setTitle("Create Account");
        setSize(350, 200);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);

        JLabel balanceLabel = new JLabel("Initial Balance:");
        JTextField balanceField = new JTextField(15);

        JButton createBtn = new JButton("Create");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(new JLabel());
        panel.add(createBtn);

        add(panel);

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String balanceText = balanceField.getText();

                if (name.isEmpty() || balanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Please fill all fields!");
                    return;
                }

                try {
                    double balance = Double.parseDouble(balanceText);
                    Connection conn = DBConnection.getConnection();
                    if (conn != null) {
                        String sql = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, name);
                        stmt.setDouble(2, balance);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(CreateAccount.this, "Account created successfully!");
                        stmt.close();
                        conn.close();
                        dispose(); // close the window
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Invalid balance amount!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Database error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
