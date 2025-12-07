package atm.data.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class đại diện cho một tài khoản ngân hàng trong hệ thống ATM
 */
public class Account implements Serializable { //Marker Interface:báo cho JVM biết rằng class được phép tuần tự hóa

    @Serial
    private static final long serialVersionUID = 1L;

    private String accountNumber;      // Số tài khoản (6-10 chữ số)
    private String accountHolderName;  // Tên chủ tài khoản
    private String pin;                // Mã PIN (4-6 chữ số)
    private double balance;            // Số dư
    private List<Transaction> transactions; // Lịch sử giao dịch

    public Account(String accountNumber, String accountHolderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    /**
     * Kiểm tra PIN có đúng không
     */
    public boolean isCorrectPin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}