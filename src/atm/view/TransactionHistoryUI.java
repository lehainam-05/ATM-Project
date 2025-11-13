package atm.view;

import atm.entity.Account;
import atm.entity.Transaction;
import atm.service.ATMService;
import java.util.List;

/**
 * UI component cho chức năng xem lịch sử giao dịch
 */
public class TransactionHistoryUI {

    private ATMService atmService;

    public TransactionHistoryUI(ATMService atmService) {
        this.atmService = atmService;
    }

    /**
     * Xem lịch sử giao dịch
     */
    public void viewTransactionHistory(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    LỊCH SỬ 5 GIAO DỊCH GẦN NHẤT                     │");
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");

        List<Transaction> transactions = atmService.getTransactionHistory(account.getAccountNumber(), 5);

        if (transactions.isEmpty()) {
            System.out.println("\n✗ Chưa có giao dịch nào!");
        } else {
            System.out.println("\n");
            System.out.println("┌───────────────────┬─────────────┬──────────────────┬─────────────────────┐");
            System.out.println("│     Thời gian     │   Loại GD   │      Số tiền     │    Số dư sau GD     │");
            System.out.println("├───────────────────┼─────────────┼──────────────────┼─────────────────────┤");

            for (Transaction t : transactions) {
                System.out.println("│ " + t.toTableRow() + " │");
            }

            System.out.println("└───────────────────┴─────────────┴──────────────────┴─────────────────────┘");
        }
    }
}