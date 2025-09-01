package banking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankingGUI extends JFrame {

    public BankingGUI() {
        setTitle("Banking System");
        setSize(400, 300);
        setLayout(new GridLayout(5, 1));

        JButton createAccountBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit Money");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton checkBalanceBtn = new JButton("Check Balance");
        JButton exitBtn = new JButton("Exit");

        add(createAccountBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(checkBalanceBtn);
        add(exitBtn);

        // ✅ Add ActionListeners to open respective windows
        createAccountBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateAccount();
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DepositMoney();
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WithdrawMoney();
            }
        });

        checkBalanceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CheckBalance();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BankingGUI();
    }
}
