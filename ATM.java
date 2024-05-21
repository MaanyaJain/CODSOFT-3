import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM extends JFrame {
    private BankAccount account;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JTextArea messageArea;

    public ATM(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        balanceLabel = new JLabel("Balance: " + account.getBalance());
        add(balanceLabel);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositButtonListener());
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawButtonListener());
        add(withdrawButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new CheckBalanceButtonListener());
        add(checkBalanceButton);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(messageArea);

        setVisible(true);
    }

    private class DepositButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deposit(amount);
                balanceLabel.setText("Balance: " + account.getBalance());
                messageArea.setText("Deposited: $" + amount);
            } catch (NumberFormatException ex) {
                messageArea.setText("Please enter a valid amount.");
            }
        }
    }

    private class WithdrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (account.withdraw(amount)) {
                    balanceLabel.setText("Balance: " + account.getBalance());
                    messageArea.setText("Withdrew: $" + amount);
                } else {
                    messageArea.setText("Insufficient balance or invalid amount.");
                }
            } catch (NumberFormatException ex) {
                messageArea.setText("Please enter a valid amount.");
            }
        }
    }

    private class CheckBalanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            balanceLabel.setText("Balance: " + account.getBalance());
            messageArea.setText("Your balance is $" + account.getBalance());
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(500.0); // Initial balance is $500
        new ATM(account);
    }
}