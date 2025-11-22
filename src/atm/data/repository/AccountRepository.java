package atm.data.repository;

import atm.data.entity.Account;
import atm.data.entity.Transaction;
import atm.data.exception.InvalidCredentialsException;
import atm.data.storage.FileManager;
import java.util.List;
import java.util.Map;

/**
 * Repository quản lý dữ liệu tài khoản
 * Sử dụng Map<String, Account> để lưu trữ
 */
public class AccountRepository {

    private Map<String, Account> accounts;

    public AccountRepository() {
        // Đọc dữ liệu từ file khi khởi tạo
        this.accounts = FileManager.loadAccounts();

        // Nếu chưa có dữ liệu, tạo một số tài khoản mẫu
        if (accounts.isEmpty()) {
            initializeSampleAccounts();
        }
    }

    /**
     * Khởi tạo một số tài khoản mẫu để test
     */
    private void initializeSampleAccounts() {
        System.out.println("\n=== KHỞI TẠO DỮ LIỆU MẪU ===");

        Account acc1 = new Account("1", "Lê Hải Nam", "1111", 15000000);
        Account acc2 = new Account("2", "Nguyễn Thuỳ Linh", "2222", 15000000);
        Account acc3 = new Account("3", "Nguyễn Đắc Tuấn Nghĩa", "3333", 15000000);

        accounts.put(acc1.getAccountNumber(), acc1);
        accounts.put(acc2.getAccountNumber(), acc2);
        accounts.put(acc3.getAccountNumber(), acc3);

        // Lưu ngay vào file
        saveData();

        int totalAccounts = accounts.size();
        System.out.println("✓ Đã tạo " + totalAccounts + " tài khoản mẫu:");

        // Hiển thị danh sách tài khoản
        for (Account acc : accounts.values()) {
            System.out.printf("  - Tài khoản: %s | PIN: %-5s | Số dư: %,10.0f VND\n",
                    acc.getAccountNumber(),
                    acc.getPin(),
                    acc.getBalance());
        }

        System.out.println("==========================================================\n");
    }

    /**
     * Xác thực đăng nhập
     * @throws InvalidCredentialsException nếu thông tin không đúng
     */
    public Account login(String accountNumber, String pin) throws InvalidCredentialsException {
        Account account = accounts.get(accountNumber);

        if (account == null) {
            throw new InvalidCredentialsException("Số tài khoản không tồn tại!");
        }

        if (!account.isCorrectPin(pin)) {
            throw new InvalidCredentialsException("Mã PIN không chính xác!");
        }

        return account;
    }

    /**
     * Lấy tài khoản theo số tài khoản
     */
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Kiểm tra số dư
     */
    public double checkBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }


    /**
     * Lưu dữ liệu vào file
     */
    public void saveData() {
        FileManager.saveAccounts(accounts);
    }

}