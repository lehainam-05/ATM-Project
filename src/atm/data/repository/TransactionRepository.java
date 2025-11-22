package atm.data.repository;

import atm.data.entity.Account;
import atm.data.entity.Transaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository quản lý toàn bộ logic liên quan đến Transaction
 * Tập trung hóa mọi thao tác với giao dịch
 */
public class TransactionRepository {

    /**
     * Thêm giao dịch mới vào tài khoản
     * @param account Tài khoản cần thêm giao dịch
     * @param type Loại giao dịch: "RUT_TIEN" or "NAP_TIEN"
     * @param amount Số tiền giao dịch
     */
    public void addTransaction(Account account, String type, double amount) {
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                type,
                amount,
                account.getBalance()
        );
        account.getTransactions().add(transaction);
    }

    /**
     * Lấy N giao dịch gần nhất của tài khoản
     * @param account Tài khoản cần xem lịch sử
     * @param limit Số lượng giao dịch muốn lấy
     * @return Danh sách giao dịch gần nhất
     */
    public List<Transaction> getRecentTransactions(Account account, int limit) {
        List<Transaction> transactions = account.getTransactions();
        int size = transactions.size();

        if (size == 0) {
            return new ArrayList<>();
        }

        int fromIndex = Math.max(0, size - limit);
        return new ArrayList<>(transactions.subList(fromIndex, size));
    }
}