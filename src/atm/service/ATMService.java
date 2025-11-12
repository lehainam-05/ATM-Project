package atm.service;

import atm.entity.Account;
import atm.entity.Transaction;
import atm.exception.InsufficientFundsException;
import atm.exception.InvalidAmountException;
import atm.exception.InvalidCredentialsException;
import atm.repository.AccountRepository;

import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho ATM
 */
public class ATMService {
    
    private final AccountRepository accountRepository;
    
    public ATMService() {
        this.accountRepository = new AccountRepository();
    }
    
    /**
     * Đăng nhập vào ATM
     */
    public Account login(String accountNumber, String pin) throws InvalidCredentialsException {
        return accountRepository.login(accountNumber, pin);
    }
    
    /**
     * Kiểm tra số dư
     */
    public double checkBalance(String accountNumber) {
        return accountRepository.checkBalance(accountNumber);
    }
    
    /**
     * Rút tiền
     */
    public void withdraw(Account account, double amount) throws InsufficientFundsException, InvalidAmountException {
        account.withdraw(amount);
        accountRepository.saveData(); // Lưu ngay sau khi rút tiền
    }
    
    /**
     * Nạp tiền
     */
    public void deposit(Account account, double amount) throws InvalidAmountException {
        account.deposit(amount);
        accountRepository.saveData(); // Lưu ngay sau khi nạp tiền
    }
    
    /**
     * Xem lịch sử giao dịch
     */
    public List<Transaction> getTransactionHistory(String accountNumber, int limit) {
        return accountRepository.getRecentTransactions(accountNumber, limit);
    }

    /**
     * Kiểm tra tính hợp lệ của PIN (4-6 số)
     */
    private boolean isValidPin(String pin) {
        if (pin.length() < 4 || pin.length() > 6) {
            return false;
        }
        // Kiểm tra chỉ chứa số
        for (char c : pin.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Kiểm tra PIN mới có hợp lệ không (dùng cho validation trước khi xác nhận)
     */
    public boolean validateNewPin(String newPin, String oldPin) throws IllegalArgumentException {
        // Validate PIN mới
        if (!isValidPin(newPin)) {
            throw new IllegalArgumentException("Mã PIN phải có 4-6 chữ số và chỉ chứa số (0-9)");
        }

        // Kiểm tra PIN mới không trùng PIN cũ
        if (newPin.equals(oldPin)) {
            throw new IllegalArgumentException("Mã PIN mới phải khác mã PIN cũ!");
        }

        return true;
    }
    /**
     * Đổi mã PIN (sau khi đã validate và xác nhận)
     */
    public void changePin(Account account, String oldPin, String newPin) throws InvalidCredentialsException, IllegalArgumentException {

        // Xác thực PIN cũ
        if (!account.verifyPin(oldPin)) {
            throw new InvalidCredentialsException("Mã PIN hiện tại không đúng!");
        }
        // Validate lại PIN mới (double check an toàn)
        validateNewPin(newPin, oldPin);

        // Thực hiện đổi PIN
        account.setPin(newPin);
        accountRepository.saveData();
    }

    /**
     * Lấy thông tin tài khoản
     */
    public Account getAccount(String accountNumber) {
        return accountRepository.getAccount(accountNumber);
    }

    /**
     * Lưu dữ liệu
     */
    public void saveData() {
        accountRepository.saveData();
    }
}