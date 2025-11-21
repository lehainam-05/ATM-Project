package atm.service;

import atm.entity.Account;
import atm.entity.Transaction;
import atm.exception.InsufficientFundsException;
import atm.exception.InvalidAmountException;
import atm.exception.InvalidCredentialsException;
import atm.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho ATM
 */
public class ATMService {
    
    private final AccountRepository accountRepository;
    private static final double MAX_DEPOSIT_AMOUNT = 100_000_000;
    
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
     * Rút tiền từ tài khoản
     * @throws InsufficientFundsException nếu số dư không đủ
     * @throws InvalidAmountException nếu số tiền không phải bội số 50,000
     */
    public void withdraw(Account account, double amount) throws InsufficientFundsException, InvalidAmountException {
        // Kiểm tra số tiền có phải bội số của 50,000 không
        if (amount % 50000 != 0) {
            throw new InvalidAmountException("Số tiền rút phải là bội số của 50,000 VND", amount);
        }

        // Kiểm tra số dư có đủ không
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Số dư không đủ để thực hiện giao dịch", amount, account.getBalance());
        }

        // Thực hiện rút tiền
        account.setBalance(account.getBalance() - amount);

        // Lưu giao dịch
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                "RUT_TIEN",
                amount,
                account.getBalance()
        );
        account.getTransactions().add(transaction);

        // Lưu dữ liệu
        accountRepository.saveData();
    }

    /**
     * Nạp tiền vào tài khoản
     */
    public void deposit(Account account, double amount) throws InvalidAmountException {
        // Kiểm tra số tiền hợp lệ
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền nạp phải lớn hơn 0", amount);
        }
        // Kiểm tra giới hạn tối đa
        if (amount > MAX_DEPOSIT_AMOUNT) {
            throw new InvalidAmountException("Số tiền nạp vượt quá hạn mức cho phép (tối đa 100,000,000 VND/lần)", amount);
        }
        if (amount % 50000 != 0) {
            throw new InvalidAmountException("Số tiền nạp phải là bội số của 50,000 VND", amount);
        }

        // Thực hiện nạp tiền
        account.setBalance(account.getBalance() + amount);

        // Lưu giao dịch
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                "NAP_TIEN",
                amount,
                account.getBalance()
        );
        account.getTransactions().add(transaction);

        // Lưu dữ liệu
        accountRepository.saveData();
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
    public void validateNewPin(String newPin, String oldPin) throws IllegalArgumentException {
        // Validate PIN mới
        if (!isValidPin(newPin)) {
            throw new IllegalArgumentException("Mã PIN phải có 4-6 chữ số và chỉ chứa số (0-9)");
        }

        // Kiểm tra PIN mới không trùng PIN cũ
        if (newPin.equals(oldPin)) {
            throw new IllegalArgumentException("Mã PIN mới phải khác mã PIN cũ!");
        }

    }
    /**
     * Đổi mã PIN (sau khi đã validate và xác nhận)
     */
    public void changePin(Account account, String oldPin, String newPin) throws InvalidCredentialsException, IllegalArgumentException {

        // Xác thực PIN cũ
        if (!account.isCorrectPin(oldPin)) {
            throw new InvalidCredentialsException("Mã PIN hiện tại không đúng!");
        }
        // Validate lại PIN mới (double check an toàn)
        validateNewPin(newPin, oldPin);

        // Thực hiện đổi PIN
        account.setPin(newPin);
        accountRepository.saveData();
    }

}