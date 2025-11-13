package atm.view;

import atm.entity.Account;
import atm.service.ATMService;
import java.util.Scanner;

/**
 * UI component hiển thị menu chính
 */
public class MainMenuUI {

    private Scanner scanner;
    private ATMService atmService;
    private BalanceUI balanceUI;
    private WithdrawUI withdrawUI;
    private DepositUI depositUI;
    private TransactionHistoryUI transactionHistoryUI;
    private ChangePinUI changePinUI;

    public MainMenuUI(Scanner scanner, ATMService atmService) {
        this.scanner = scanner;
        this.atmService = atmService;
        this.balanceUI = new BalanceUI(atmService);
        this.withdrawUI = new WithdrawUI(scanner, atmService);
        this.depositUI = new DepositUI(scanner, atmService);
        this.transactionHistoryUI = new TransactionHistoryUI(atmService);
        this.changePinUI = new ChangePinUI(scanner, atmService);
    }

    /**
     * Hiển thị menu ATM
     */
    public void showATMMenu(Account account) {
        boolean continueSession = true;

        while (continueSession) {
            System.out.println("\n");
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║              MENU CHÍNH ATM               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║  1. Kiểm tra số dư                        ║");
            System.out.println("║  2. Rút tiền                              ║");
            System.out.println("║  3. Nạp tiền                              ║");
            System.out.println("║  4. Xem lịch sử 5 giao dịch gần nhất      ║");
            System.out.println("║  5. Đổi mã PIN                            ║");
            System.out.println("║  6. Đăng xuất                             ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.print("Chọn chức năng (1-6): ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        balanceUI.checkBalance(account);
                        break;
                    case 2:
                        withdrawUI.withdrawMoney(account);
                        break;
                    case 3:
                        depositUI.depositMoney(account);
                        break;
                    case 4:
                        transactionHistoryUI.viewTransactionHistory(account);
                        break;
                    case 5:
                        changePinUI.changePin(account);
                        break;
                    case 6:
                        continueSession = false;
                        System.out.println("\n✓ Đăng xuất thành công!");
                        System.out.println("✓ Đừng quên lấy thẻ của bạn!");
                        break;
                    default:
                        System.out.println("\n✗ Lựa chọn không hợp lệ! Vui lòng chọn từ 1-6.");
                }

            } catch (Exception e) {
                System.out.println("\n✗ Lỗi: Vui lòng nhập số từ 1-6!");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
}