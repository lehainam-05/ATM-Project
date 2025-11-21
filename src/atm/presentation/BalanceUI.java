package atm.presentation;

import atm.data.entity.Account;
import atm.business.ATMService;

/**
 * UI component cho chức năng kiểm tra số dư
 */
public class BalanceUI {

    private ATMService atmService;

    public BalanceUI(ATMService atmService) {
        this.atmService = atmService;
    }

    /**
     * Kiểm tra số dư
     */
    public void checkBalance(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           KIỂM TRA SỐ DƯ            │");
        System.out.println("└─────────────────────────────────────┘");

        double balance = atmService.checkBalance(account.getAccountNumber());
        System.out.printf("Số dư hiện tại: %,15.0f VND\n", balance);
    }
}