package atm.presentation;

import atm.data.entity.Account;
import atm.data.exception.InvalidAmountException;
import atm.business.ATMService;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UI component cho chức năng nạp tiền
 */
public class DepositUI {

    private Scanner scanner;
    private ATMService atmService;

    public DepositUI(Scanner scanner, ATMService atmService) {
        this.scanner = scanner;
        this.atmService = atmService;
    }

    /**
     * Nạp tiền
     */
    public void depositMoney(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│             NẠP TIỀN                │");
        System.out.println("└─────────────────────────────────────┘");

        System.out.printf("Số dư hiện tại: %,15.0f VND\n", account.getBalance());
        System.out.println("\nLưu ý:");
        System.out.println("- Số tiền nạp phải là bội số của 50,000 VND");
        System.out.println("- Hạn mức tối đa: 100,000,000 VND/lần");
        System.out.print("Nhập số tiền cần nạp: ");

        try {
            double amount = scanner.nextDouble();

            atmService.deposit(account, amount);

            System.out.println("\n✓ Nạp tiền thành công!");
            System.out.printf("✓ Số tiền đã nạp: %,15.0f VND\n", amount);
            System.out.printf("✓ Số dư mới: %,15.0f VND\n", account.getBalance());

        } catch (InvalidAmountException e) {
            System.out.println("\n✗ " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("\n✗ Lỗi: Vui lòng nhập số hợp lệ!");
            scanner.nextLine(); // clear buffer
        }
    }
}