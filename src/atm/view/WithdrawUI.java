package atm.view;

import atm.entity.Account;
import atm.exception.InsufficientFundsException;
import atm.exception.InvalidAmountException;
import atm.service.ATMService;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UI component cho chức năng rút tiền
 */
public class WithdrawUI {

    private Scanner scanner;
    private ATMService atmService;

    public WithdrawUI(Scanner scanner, ATMService atmService) {
        this.scanner = scanner;
        this.atmService = atmService;
    }

    /**
     * Rút tiền
     */
    public void withdrawMoney(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│               RÚT TIỀN              │");
        System.out.println("└─────────────────────────────────────┘");

        System.out.printf("Số dư hiện tại: %,15.0f VND\n\n", account.getBalance());

        // Menu chọn số tiền
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║           CHỌN SỐ TIỀN MUỐN RÚT           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║  1. 100,000 VND                           ║");
        System.out.println("║  2. 500,000 VND                           ║");
        System.out.println("║  3. 1,000,000 VND                         ║");
        System.out.println("║  4. 1,500,000 VND                         ║");
        System.out.println("║  5. 2,000,000 VND                         ║");
        System.out.println("║  6. Số khác                               ║");
        System.out.println("║  0. Hủy                                   ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.print("Chọn (0-6): ");

        int choice = scanner.nextInt();
        double amount;

        switch (choice) {
            case 1:
                amount = 100000;
                break;
            case 2:
                amount = 500000;
                break;
            case 3:
                amount = 1000000;
                break;
            case 4:
                amount = 1500000;
                break;
            case 5:
                amount = 2000000;
                break;
            case 6:
                // Nhập số tiền khác
                System.out.println("\nLưu ý: Số tiền rút phải là bội số của 50,000 VND");
                System.out.print("Nhập số tiền cần rút: ");
                amount = scanner.nextDouble();
                break;
            case 0:
                System.out.println("\n✓ Đã hủy giao dịch");
                return;
            default:
                System.out.println("\n✗ Lựa chọn không hợp lệ!");
                return;
        }

        // Kiểm tra số tiền > 0
        if (amount <= 0) {
            System.out.println("\n✗ Số tiền phải lớn hơn 0!");
            return;
        }

        // Thực hiện rút tiền
        try {
            atmService.withdraw(account, amount);
            System.out.println("\n✓ Rút tiền thành công!");
            System.out.printf("✓ Số tiền đã rút: %,15.0f VND\n", amount);
            System.out.printf("✓ Số dư còn lại: %,15.0f VND\n", account.getBalance());
            System.out.println("\n✓ Vui lòng nhận tiền của bạn!");
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("\n" + e.getMessage());
        } catch(InputMismatchException e) {
            System.out.println("\n✗ Vui lòng nhập số hợp lệ!");
        }
    }
}